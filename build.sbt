name := "Monocle"

version := "0.0.1"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core"  % "1.2.0",
  "com.github.julien-truffaut" %% "monocle-macro" % "1.2.0",
  "org.scalatest"              %% "scalatest"     % "2.2.6"  % "test")