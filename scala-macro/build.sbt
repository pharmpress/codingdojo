
scalaVersion := "2.11.8"

name := "scala-macro"

libraryDependencies ++= Seq(
  scalaVersion("org.scala-lang" % "scala-compiler" % _).value,
  "org.scalatest" %% "scalatest" % "2.2.1" % Test

)

