import sbt.Keys._
name := "BulkFileReader"

version := "1.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++=  Seq(ws,cache,
  "com.typesafe.play" % "play-json_2.11" % "2.5.12",
  "com.typesafe.akka" %% "akka-stream" % "2.5.0",
  "org.apache.commons" % "commons-csv" % "1.1",
  "com.h2database" % "h2" % "1.4.192") ++ testDependencies

lazy val testDependencies = Seq(
  "org.mockito" % "mockito-all" % "1.9.5" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test"
)