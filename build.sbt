lazy val baseVersion = "0.1.0-SNAPSHOT"
lazy val specsVersion = "4.11.0"
lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.5"
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
      "org.specs2" %% "specs2-core" % specsVersion % Test
    ),
    console / initialCommands := """import dispatch._, Defaults._
                                   |import repatch.github.{request => gh}
                                   |val client = gh.LocalConfigClient()
                                   |val http = new Http""".stripMargin
  )
