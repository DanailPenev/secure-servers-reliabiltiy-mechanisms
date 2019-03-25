package server

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

object StatusRouter {
  val output: String = """{"status": "ok"}"""

  val routes: Route =
    path("status") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, output))
      }
    }
}
