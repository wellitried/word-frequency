name := """word-frequency"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "com.googlecode.juniversalchardet" % "juniversalchardet" % "1.0.3"
libraryDependencies += "org.apache.poi" % "poi" % "3.15"