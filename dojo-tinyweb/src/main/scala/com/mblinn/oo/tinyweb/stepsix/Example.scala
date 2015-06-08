package com.mblinn.oo.tinyweb.stepsix

import java.io.{InputStreamReader, BufferedReader}
import java.net.URLDecoder

import com.sun.net.httpserver.HttpExchange

import scala.util.Random

object Example extends App {
    
  def greetingViewRenderer(model: Map[String, List[String]]) = {
    def renderGreeting(greeting: String) = "<h2>%s</h2>".format(greeting)
    "<h1>Friendly Greetings:%s".format(
        model 
        getOrElse("greetings", List[String]()) 
        map renderGreeting
        mkString ", ")
  }

  def handleGreetingRequest(request: HttpExchange) = {
    val random = new Random()
    def greetings = Vector("Hello", "Greetings", "Salutations", "Hola")
    def makeGreeting(name: String) = "%s, %s".format(greetings(random.nextInt(greetings.size)), name)
    val params = getParameters(request)
    Map("greetings" -> params("users").split(",").toList.map(makeGreeting))
  }

  def getParameters(request: HttpExchange): Map[String, String] = {
    Map(request.getRequestURI.getQuery.split("&").map{
      pair =>
        val idx = pair.indexOf("=")
        URLDecoder.decode(pair.substring(0, idx), "UTF-8") -> URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
    }.toSeq: _*)
  }

  def loggingFilter(request: HttpExchange): HttpExchange = {
    println("In Logging Filter - request for path: %s".format(request.getRequestURI.toString))
    request
  }
  
  val tinyweb = new TinyWeb(
      Seq("/greeting" -> (greetingViewRenderer _, handleGreetingRequest _)),
      Seq("logger" -> loggingFilter)
  )

  tinyweb.start
//  def testHttpRequest = HttpRequest(
//      body="Mike,Joe,John,Steve",
//      path="/greeting"
//  )

  val in = new BufferedReader(new InputStreamReader(System.in))
  var c: Int = 0
  while('q'.toInt != { c = in.read(); c }){
    println(c.toChar)
  }
  println("-STOP-")
  tinyweb.executor.shutdownNow()
  tinyweb.stop

}