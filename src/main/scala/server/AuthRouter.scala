package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.duration._

object AuthRouter {
  lazy val routes: Route = toStrictEntity(3.seconds) {
    path("authenticate") {
      authenticateBasic(realm = "secure site", TryCatchAuthenticator.authenticator) { statusCode =>
        complete(statusCode)
      }
    }
  }
}
