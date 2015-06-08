package com.mblinn.oo.tinyweb

import com.sun.net.httpserver.HttpExchange

package object stepsix {

  case class HttpRequest(headers: Map[String, String] = Map(), body: String, path: String)
  case class HttpResponse(body: String, responseCode: Integer)

  type ViewType = (Map[String, List[String]]) => String
  type ControllerType = (HttpExchange) => Map[String, List[String]]
}
