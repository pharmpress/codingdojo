package controllers


import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import actors.MainSearchActor
import akka.actor.Props

import play.api.Play.current
import models.{StartSearch, SearchFeed}
import play.api.libs.EventSource
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext
//import ExecutionContext.Implicits.global

import scala.concurrent.duration._
import scala.language.postfixOps


object Application extends Controller {

  implicit val timeout = Timeout(5 seconds)

  val searchActor = Akka.system.actorOf(Props[MainSearchActor])

  def index = Action {
    Ok(views.html.index("Search logs"))
  }

  def search(searchString: String) = Action.async {
    (searchActor ? StartSearch(searchString = searchString)).map {
      case SearchFeed(out) => Ok.stream(out &> EventSource()).as("text/event-stream")
    }
  }
}