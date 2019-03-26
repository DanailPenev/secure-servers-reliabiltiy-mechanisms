import Dependencies._
import sbt.Keys.libraryDependencies

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    inThisBuild(List(
      organization := "com.danailpenev",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "secure-servers-reliability-mechanisms",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.5" ,
    libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.12"
  )


