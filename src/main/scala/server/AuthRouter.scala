package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object AuthRouter {
  lazy val routes: Route =
    authenticateBasic(realm = "secure site", TryCatchAuthenticator.authenticator) { statusCode =>
      path("login") {
        complete(statusCode)
      }
    } ~ cookie("userName") { nameCookie =>
      path("admin") {
        complete(TryCatchAuthenticator.isAdmin(nameCookie))
      }
    }
}
