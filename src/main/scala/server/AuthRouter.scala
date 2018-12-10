package server

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import controllers.{ActorController, FutureController, TryCatchController}

object AuthRouter {
  lazy val routes: Route =
    pathPrefix("exceptions") {
      path("login") {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            TryCatchController.authenticator(credentials, completerFunction)
          }
        }
      } ~ path("admin") {
          cookie("userName") { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              TryCatchController.authorizeAdmin(nameCookie, completerFunction)
            }
          }
        }
    } ~ pathPrefix("actors") {
      path("login") {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            ActorController.authenticate(credentials, completerFunction)
          }
        }
      } ~ path("admin") {
          cookie("userName") { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              ActorController.authorizeAdmin(nameCookie, completerFunction)
            }
          }
        }
    } ~ pathPrefix("futures") {
      path("login") {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            FutureController.authenticator(credentials, completerFunction)
          }
        }
      } ~ path("admin") {
          cookie("userName") { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              FutureController.authorizeAdmin(nameCookie, completerFunction)
            }
          }
        }
    }
}
