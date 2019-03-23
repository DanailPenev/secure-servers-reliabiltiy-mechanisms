package controllers

import actors.Messages.{Authenticate, Authorize}
import actors.Supervisor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}
import akka.routing.RoundRobinPool

object ActorController {
  val NUMBER_OF_SUPERVISORS: Integer = 10

  private val system: ActorSystem = ActorSystem("Supervisor-Workers")
  private val supervisors: ActorRef = system.actorOf(Props[Supervisor].withRouter(RoundRobinPool(NUMBER_OF_SUPERVISORS)))

  def authenticate(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = {
    supervisors ! Authenticate(credentials, complete)
  }

  def authorizeAdmin(nameCookie: HttpCookiePair, complete: StatusCode => Unit): Unit = {
    supervisors ! Authorize(nameCookie, complete)
  }
}
