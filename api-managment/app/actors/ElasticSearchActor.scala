package actors

import akka.actor.{ActorRef, Actor}
import models.{SearchMatch, StopSearch, LogEntry, StartSearch}
import play.api.libs.ws.WS
import play.api.libs.json.{JsArray, JsValue, Json}
import java.util.UUID
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import play.api.Play.current

/**
  */
class ElasticsearchActor extends Actor {

  def receive = {
    case LogEntry(data) => percolate(data, sender())
    case StartSearch(id, searchString) => registerQuery(id, searchString)
    case StopSearch(id) => unregisterQuery(id)
  }

  private def percolate(logJson: JsValue, requestor: ActorRef) {
    WS.url("http://localhost:9200/logentries/logentry/_percolate").post(Json.stringify(Json.obj("doc" -> logJson))).map {
      response =>
        val body = response.json
        val total = (body \ "total").as[Int]
        if (total  > 0) {
          val matchingIds = (body \ "matches" \\ "_id").foldLeft(List[UUID]())((acc, v) => UUID.fromString(v.as[String]) :: acc)
          if (matchingIds.nonEmpty) {
            requestor ! SearchMatch(LogEntry(logJson), matchingIds)
          }
        }
    }
  }

  private def unregisterQuery(id: UUID) {
    WS.url("http://localhost:9200/_percolator/logentries/" + id.toString).delete()
  }

  private def registerQuery(id: UUID, searchString: String) {
    val query = Json.obj(
      "query" -> Json.obj(
        "query_string" -> Json.obj(
          "query" -> searchString,
          "default_field" -> "path"
        )))

    WS.url("http://localhost:9200/logentries/.percolator/" + id.toString).put(Json.stringify(query))
  }
}
