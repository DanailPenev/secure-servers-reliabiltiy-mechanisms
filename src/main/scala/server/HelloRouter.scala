package server

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

object HelloRouter {
  val input: String = """{"status": "ok"}"""

  val routes: Route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, input))
      }
    }
}
