package com.mblinn.oo.tinyweb.stepfive

import com.mblinn.oo.tinyweb.{RenderingException, ControllerException}

class TinyWeb(controllers: Map[String, (ViewType, ControllerType)], filters: List[(HttpRequest) => HttpRequest]) {
  
  def handleRequest(httpRequest: HttpRequest): Option[HttpResponse] = {
    val composedFilter = filters.reverse.reduceLeft((composed, next) => composed compose next)
    val filteredRequest = composedFilter(httpRequest)
    val controllerOption = controllers.get(filteredRequest.path)
    controllerOption map {
      case (viewRenderer, doRequest) =>
        controllerHandleRequest(viewRenderer, doRequest, filteredRequest)
    }
  }

  def controllerHandleRequest(viewRenderer: ViewType, doRequest: ControllerType, request: HttpRequest): HttpResponse =
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