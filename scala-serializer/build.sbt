
scalaVersion := "2.12.1"

name := "scala-serializer"

version := "1.0"

libraryDependencies ++= Seq(
	"org.typelevel" %% "cats" % "0.9.0",
	"com.chuusai" %% "shapeless" % "2.3.2",
	"org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
