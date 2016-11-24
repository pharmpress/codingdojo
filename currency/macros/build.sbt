name := "currency-macros"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  Resolver.file("Local Ivy Repository", file(Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  scalaVersion("org.scala-lang" % "scala-reflect" % _).value,
  scalaVersion("org.scala-lang" % "scala-compiler" % _).value
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)