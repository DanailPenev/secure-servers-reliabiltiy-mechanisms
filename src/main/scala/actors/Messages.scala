package actors

import akka.http.scaladsl.server.RequestContext
import akka.http.scaladsl.server.directives.Credentials

object Messages {
  case class Handle(credentials: Credentials)
  case object Terminate
  case class Authenticate(requestContext: RequestContext)
  case class CompleteFailed(requestContext: RequestContext)
}
