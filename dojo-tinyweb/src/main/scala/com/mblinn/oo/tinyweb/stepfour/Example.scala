package com.mblinn.oo.tinyweb.stepfour

import scala.util.Random

object Example {
    
  def greetingViewRenderer(model: Map[String, List[String]]) = 
    "<h1>Friendly Greetings:%s".format(
        model 
        getOrElse("greetings", List[String]()) 
        map(renderGreeting)
        mkString ", ")
        
  private def renderGreeting(greeting: String) = 
    "<h2>%s</h2>".format(greeting)
    
  def greetingView = new FunctionView(greetingViewRenderer)
  
  def handleGreetingRequest(request: HttpRequest) =
    Map("greetings" -> request.body.split(",").toList.map(makeGreeting))
    
  private def random = new Random()
  private def greetings = Vector("Hello", "Greetings", "Salutations", "Hola")
  private def makeGreeting(name: String) =
    "%s, %s".format(greetings(random.nextInt(greetings.size)), name)
  
  def greetingController = new FunctionController(greetingView, handleGreetingRequest)
    
  private def loggingFilter(request: HttpRequest) = {
    println("In Logging Filter - request for path: %s".format(request.path))
    request
  }
  
  def tinyweb = new TinyWeb(
      Map("/greeting" -> greetingController),
      List(loggingFilter))
  def testHttpRequest = HttpRequest(
      body="Mike,Joe,John,Steve", 
      path="/greeting")
  
}