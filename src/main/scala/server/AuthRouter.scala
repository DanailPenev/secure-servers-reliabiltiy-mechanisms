package server

import actors.Messages.{Authenticate, Authorize}
import actors.Supervisor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object AuthRouter {
  val system: ActorSystem = ActorSystem("yolo")

  val supervisor: ActorRef = system.actorOf(Props[Supervisor])

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
        extractCredentials { creds =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            supervisor ! Authenticate(creds, completerFunction)
          }
        }
      } ~
        path("admin") {
          cookie("userName") { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              supervisor ! Authorize(nameCookie, completerFunction)
            }
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
