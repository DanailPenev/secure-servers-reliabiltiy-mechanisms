package controllers

import actors.Messages.{Authenticate, Authorize}
import actors.Supervisor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}

object ActorController {
  private val system: ActorSystem = ActorSystem("Supervisor-Workers")

  private val supervisor: ActorRef = system.actorOf(Props[Supervisor])

  def authenticate(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = {
    supervisor ! Authenticate(credentials, complete)
  }

  def authorizeAdmin(nameCookie: HttpCookiePair, complete: StatusCode => Unit): Unit = {
    supervisor ! Authorize(nameCookie, complete)
  }
}
