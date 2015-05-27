name := "dojo-timer"

version := "1.0"

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
  "info.cukes" %% "cucumber-scala" % "1.2.2" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.2" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "joda-time" % "joda-time" % "2.7"
)