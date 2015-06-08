package com.mblinn.oo.tinyweb.stepfive

import scala.util.Random

object Example {
    
  def greetingViewRenderer(model: Map[String, List[String]]) = {
    def renderGreeting(greeting: String) = "<h2>%s</h2>".format(greeting)
    "<h1>Friendly Greetings:%s".format(
        model 
        getOrElse("greetings", List[String]()) 
        map renderGreeting
        mkString ", ")
  }

  def handleGreetingRequest(request: HttpRequest) = {
    val random = new Random()
    def greetings = Vector("Hello", "Greetings", "Salutations", "Hola")
    def makeGreeting(name: String) = "%s, %s".format(greetings(random.nextInt(greetings.size)), name)
    Map("greetings" -> request.body.split(",").toList.map(makeGreeting))
  }

  def loggingFilter(request: HttpRequest) = {
    println("In Logging Filter - request for path: %s".format(request.path))
    request
  }
  
  def tinyweb = new TinyWeb(
      Map("/greeting" -> (greetingViewRenderer _, handleGreetingRequest _)),
      List(loggingFilter)
  )

  def testHttpRequest = HttpRequest(
      body="Mike,Joe,John,Steve", 
      path="/greeting"
  )
  
}