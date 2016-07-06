name := """play2.3-webapi"""

version := "1.0.0"

lazy val core = (project in file("modules/play2.3-core")).enablePlugins(PlayJava)

lazy val root = (project in file(".")).enablePlugins(PlayJava).dependsOn(core).aggregate(core)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)
