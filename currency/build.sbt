val macros = project in file("macros")

val root = (project in file(".")).dependsOn(macros % "test->test;compile->compile").aggregate(macros)


name := "currency"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.file("Local Ivy Repository", file(Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  scalaVersion("org.scala-lang" % "scala-reflect" % _).value,
  scalaVersion("org.scala-lang" % "scala-compiler" % _).value
)





addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)