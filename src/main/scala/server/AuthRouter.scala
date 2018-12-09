package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object AuthRouter {
  lazy val routes: Route =
    pathPrefix("exceptions") {
      path("login") {
        authenticateBasic(realm = "secure site", TryCatchAuthenticator.authenticator) { statusCode =>
          complete(statusCode)
        }
      } ~
        cookie("userName") { nameCookie =>
          path("admin") {
            complete(TryCatchAuthenticator.isAdmin(nameCookie))
          }
        }
    } ~ pathPrefix("futures") {
      path("login") {
        authenticateBasicAsync(realm = "secure site", FutureAuthenticator.authenticator) { statusCode =>
          complete(statusCode)
        }

      } ~
        cookie("userName") { nameCookie =>
          path("admin") {
            complete(FutureAuthenticator.checkAdmin(nameCookie.value))
          }
        }
    }
}
