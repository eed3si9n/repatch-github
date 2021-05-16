def baseVersion = "0.1.0-SNAPSHOT"
def specsVersion = "3.10.0"

ThisBuild / crossScalaVersions := Seq("2.10.7", "2.12.13")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last

lazy val dispatchVersion = settingKey[String]("")

lazy val commonSettings: Seq[Def.Setting[_]] = Seq(
  dispatchVersion := "1.2.0",
  version := s"dispatch${dispatchVersion.value}_${baseVersion}",
  organization := "com.eed3si9n",
  fork in run := true
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "repatch-github"
  ).aggregate(core)

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(
    name := "repatch-github-core",
    libraryDependencies ++= Seq(
      "org.dispatchhttp" %% "dispatch-core" % dispatchVersion.value,
      "org.dispatchhttp" %% "dispatch-json4s-native" % dispatchVersion.value,
      "org.specs2" %% "specs2-core" % specsVersion % Test
    ),
    initialCommands in console := """import dispatch._, Defaults._
                                    |import repatch.github.{request => gh}
                                    |val client = gh.LocalConfigClient()
                                    |val http = new Http""".stripMargin
  )
