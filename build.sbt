lazy val baseVersion = "0.1.0-SNAPSHOT"
lazy val specsVersion = "4.11.0"
lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.6"
lazy val dispatchVersion = settingKey[String]("")

ThisBuild / dispatchVersion := "1.2.0"
ThisBuild / version := s"dispatch${(ThisBuild / dispatchVersion).value}_${baseVersion}"
ThisBuild / crossScalaVersions := Seq(scala212, scala213)
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last
ThisBuild / organization := "com.eed3si9n"

lazy val root = (project in file("."))
  .aggregate(core)
  .settings(
    name := "repatch-github-root"
  )

lazy val core = (project in file("core"))
  .settings(
    name := "repatch-github",
    run / fork := true,
    libraryDependencies ++= Seq(
      "org.dispatchhttp" %% "dispatch-core" % dispatchVersion.value,
      "org.dispatchhttp" %% "dispatch-json4s-native" % dispatchVersion.value,
      "org.specs2" %% "specs2-core" % specsVersion % Test,
      "javax.xml.bind" % "jaxb-api" % "2.3.0", // For java11
    ),
    console / initialCommands := """import dispatch._, Defaults._
                                   |import repatch.github.{request => gh}
                                   |val client = gh.LocalConfigClient()
                                   |val http = new Http""".stripMargin
  )

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/eed3si9n/repatch-github"),
    "scm:git@github.com:eed3si9n/repatch-github.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "eed3si9n",
    name  = "Eugene Yokota",
    email = "@eed3si9n",
    url   = url("https://eed3si9n.com")
  ),
  Developer(
    id    = "er1c",
    name  = "Eric Peters",
    email = "@ericpeters",
    url   = url("https://github.com/er1c")
  ),
)

ThisBuild / description := "Dispatch plugin for GitHub API v3"
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/eed3si9n/repatch-github"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
