package com.mblinn.oo.tinyweb.stepfour

case class HttpRequest(headers: Map[String, String] = Map(), body: String, path: String)
case class HttpResponse(body: String, responseCode: Integer)
  
