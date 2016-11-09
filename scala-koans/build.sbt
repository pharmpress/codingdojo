addCommandAlias("koans", "~test-only org.functionalkoans.forscala.Koans")

name := "scala-koans"

version := "1.0"

scalaVersion := "2.11.8"

traceLevel := -1

logLevel := Level.Info

// disable printing timing information, but still print [success]
showTiming := false

// disable printing a message indicating the success or failure of running a task
showSuccess := false

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

// disable updating dynamic revisions (including -SNAPSHOT versions)
offline := true

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.2.6" % "test" withSources() withJavadoc()
)

javacOptions ++= Seq("-encoding", "UTF-8")

javaOptions in (Test,run) += "-Xmx512M -Xss1M -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256m"