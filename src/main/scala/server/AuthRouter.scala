package server

import akka.http.scaladsl.server.Directives.{complete, formFields, path, toStrictEntity}
import akka.http.scaladsl.server.Route
import scala.concurrent.duration._

object AuthRouter {
  lazy val routes: Route = toStrictEntity(3.seconds) {
    path("authenticate") {
      formFields('username, 'password) { (username, password) =>
        println(username, password)
        complete(TryCatchAuthenticator.tryAuthenticate(username, password))
      }
    }
  }
}
