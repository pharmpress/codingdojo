package com.mblinn.oo.tinyweb

package object stepfive {

  case class HttpRequest(headers: Map[String, String] = Map(), body: String, path: String)
  case class HttpResponse(body: String, responseCode: Integer)

  type ViewType = (Map[String, List[String]]) => String
  type ControllerType = (HttpRequest) => Map[String, List[String]]
}
