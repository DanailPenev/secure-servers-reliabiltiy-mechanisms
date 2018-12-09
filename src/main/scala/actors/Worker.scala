package actors

import actors.Messages.Authenticate
import akka.actor.Actor
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.{HttpCredentials, `WWW-Authenticate`}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}
import akka.http.scaladsl.server.directives.Credentials
import server.TryCatchAuthenticator

class Worker extends Actor {
  override def receive: Receive = {
    case Authenticate(request) =>
      extractCredentials { creds =>
        creds.
      }
      extractRequestContext { ctx =>
        var head = ctx.request.headers[`WWW-Authenticate`]
      }
  }

  def authenticate(requestContext: RequestContext, credentials: Option[HttpCredentials]): Route = {
    TryCatchAuthenticator.authenticate(credentials)
    requestContext.complete(StatusCodes.OK)
  }

}
