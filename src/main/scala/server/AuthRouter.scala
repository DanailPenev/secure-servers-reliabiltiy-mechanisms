package server

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import controllers.{ActorController, FutureController, TryCatchController}

object AuthRouter {
  lazy val routes: Route =
    pathPrefix(Config.ActorsPath) {
      path(Config.LoginPath) {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            ActorController.authenticate(credentials, completerFunction)
          }
        }
      } ~ path(Config.AdminPath) {
        cookie(Config.CookieName) { nameCookie =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            ActorController.authorizeAdmin(nameCookie, completerFunction)
          }
        }
      }
    } ~ pathPrefix(Config.ExceptionsPath) {
      path(Config.LoginPath) {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            TryCatchController.authenticate(credentials, completerFunction)
          }
        }
      } ~ path(Config.AdminPath) {
          cookie(Config.CookieName) { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              TryCatchController.authorizeAdmin(nameCookie, completerFunction)
            }
          }
        }
    } ~ pathPrefix(Config.FuturesPath) {
      path(Config.LoginPath) {
        extractCredentials { credentials =>
          completeWith(instanceOf[StatusCode]) { completerFunction =>
            FutureController.authenticate(credentials, completerFunction)
          }
        }
      } ~ path(Config.AdminPath) {
          cookie(Config.CookieName) { nameCookie =>
            completeWith(instanceOf[StatusCode]) { completerFunction =>
              FutureController.authorizeAdmin(nameCookie, completerFunction)
            }
          }
        }
    }
}
