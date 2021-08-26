organization := "com.totenpass"

name := "tes-java"

version := "0.1"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:deprecation", "-encoding", "UTF8")

crossPaths := false

autoScalaLibrary := false

//parallelExecution in Test := false

libraryDependencies ++= Seq(
  "org.bouncycastle" % "bcprov-jdk15on" % "1.69",
  "com.novocode" % "junit-interface" % "0.11" % Test
)

