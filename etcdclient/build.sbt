name := "etcdclient"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  Resolver.file("Local Ivy Repository", file(Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  "Pharmpress everything" at "http://rpsci.rps.local:8081/nexus/content/groups/everything/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.8",
  "org.apache.httpcomponents" % "httpclient" % "4.3.2",
  "org.eclipse.jetty" % "jetty-server" % "9.2.11.v20150529" % "test",
  "org.eclipse.jetty" % "jetty-http-spi" % "9.2.11.v20150529" % "test",
  "org.eclipse.jetty" % "jetty-util" % "9.2.11.v20150529" % "test",
  "info.cukes" %% "cucumber-scala" % "1.2.2" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.2" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
)