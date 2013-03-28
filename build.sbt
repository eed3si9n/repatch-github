import DispatchGithubKeys._

dispatchVersion := "0.10.0-beta1"

name := "repatch-github"

organization := "com.eed3si9n"

scalaVersion := "2.10.1"

version <<= (dispatchVersion) { "dispatch" + _ + "_0.0.1-SNAPSHOT" }

libraryDependencies <++= (dispatchVersion) { (dv) => Seq(
  "net.databinder.dispatch" %% "dispatch-core" % dv,
  "net.databinder.dispatch" %% "dispatch-json4s-native" % dv
)}

libraryDependencies <+= (scalaVersion) {
  case "2.9.3" =>  "org.specs2" %% "specs2" % "1.12.4.1" % "test"
  case _ => "org.specs2" %% "specs2" % "1.14" % "test"
}

crossScalaVersions := Seq("2.10.1", "2.9.3")

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/repositories/public"
