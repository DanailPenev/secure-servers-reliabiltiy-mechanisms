package controllers

import actors.Messages.{Authenticate, Authorize}
import actors.Supervisor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}
import akka.routing.RoundRobinPool

object ActorController {
  val NumberOfSupervisors: Integer = 10

  private val system: ActorSystem = ActorSystem("Supervisor-Workers")
  private val supervisors: ActorRef = system.actorOf(Props[Supervisor].withRouter(RoundRobinPool(NumberOfSupervisors)))

  def authenticate(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = {
    supervisors ! Authenticate(credentials, complete)
  }

  def authorizeAdmin(nameCookie: HttpCookiePair, complete: StatusCode => Unit): Unit = {
    supervisors ! Authorize(nameCookie, complete)
  }
}
