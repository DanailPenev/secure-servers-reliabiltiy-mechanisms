package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait Routes {
  val route: Route = AuthRouter.routes ~ StatusRouter.routes
}
