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
        path("admin") {
          cookie("userName") { nameCookie =>
            complete(TryCatchAuthenticator.isAdmin(nameCookie))
          }
        }
    } ~ pathPrefix("actors") {
      path("login") {
        authenticateBasicAsync(realm = "secure site", ActorAuthenticator.authenticator) { statusCode =>
          complete(statusCode)
        }

      } ~
        path("admin") {
          cookie("userName") { nameCookie =>
            complete(ActorAuthenticator.checkAdmin(nameCookie.value))
          }
        }
    } ~ pathPrefix("futures") {
      path("login") {
        authenticateBasicAsync(realm = "secure site", FutureAuthenticator.authenticator) { statusCode =>
          complete(statusCode)
        }

      } ~
        path("admin") {
          cookie("userName") { nameCookie =>
            complete(FutureAuthenticator.checkAdmin(nameCookie.value))
          }
        }
    }
}
