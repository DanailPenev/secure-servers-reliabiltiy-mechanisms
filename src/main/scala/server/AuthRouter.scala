package server

import actors.Messages.Handle
import actors.Supervisor
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.Credentials

object AuthRouter {
  val system: ActorSystem = ActorSystem("yolo")

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
            val props = Props(new Supervisor(completerFunction))
            val disposableActor = system.actorOf(props)
            disposableActor ! Handle(creds)
          }
        }
      }
//        path("admin") {
//          cookie("userName") { nameCookie =>
//            complete(ActorAuthenticator.checkAdmin(nameCookie.value))
//          }
//        }
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
