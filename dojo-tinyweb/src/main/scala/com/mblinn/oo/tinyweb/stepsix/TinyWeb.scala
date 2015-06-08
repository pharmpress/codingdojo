package com.mblinn.oo.tinyweb.stepsix

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import com.mblinn.oo.tinyweb.{RenderingException, ControllerException}
import com.sun.net.httpserver.Filter.Chain
import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}

import scala.io.Source
import com.sun.net.httpserver.Filter
import scala.collection.JavaConversions._

class TinyWeb(controllers: Seq[(String, (ViewType, ControllerType))], filters: Seq[(String , (HttpExchange) => HttpExchange)]) {
  val server: HttpServer = HttpServer.create(new InetSocketAddress(8888), 0)
  val executor = Executors.newCachedThreadPool()
  server.setExecutor(executor)
  controllers.foreach{ case (path, (viewRenderer: ViewType, doRequest)) =>
    val context = server.createContext(path, new HttpHandler{
      override def handle(httpExchange: HttpExchange): Unit = {
        val response = controllerHandleRequest(viewRenderer, doRequest, httpExchange)
        val content = response.body
        httpExchange.sendResponseHeaders(response.responseCode, content.length())
        val os = httpExchange.getResponseBody
        try{
          if(content.nonEmpty){
            os.write(content.getBytes)
          }
        } finally {
          os.close()
        }
      }
    })

    filters.foreach{
      case (desc, filter) =>
        context.getFilters.add(new Filter(){
          override def description(): String = desc
          override def doFilter(httpExchange: HttpExchange, chain: Chain): Unit = chain.doFilter(filter(httpExchange))
        })
    }

  }

  def start: Unit = server.start()

  def stop: Unit = server.stop(1000)

  def controllerHandleRequest(viewRenderer: ViewType, doRequest: ControllerType, request: HttpExchange): HttpResponse =
    try {
      HttpResponse(viewRenderer(doRequest(request)), 200)
    } catch {
      case e: ControllerException =>
        HttpResponse("", e.getStatusCode)
      case e: RenderingException =>
        HttpResponse("Exception while rendering.", 500)
      case e: Exception =>
        HttpResponse("", 500)
    }
}