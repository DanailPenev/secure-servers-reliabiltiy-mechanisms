package actors

import actors.Messages._
import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, OneForOneStrategy, Props}
import akka.http.scaladsl.model.{StatusCode, StatusCodes}

class Supervisor(complete: StatusCode => Unit) extends Actor {
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy() {
      case _: Exception =>
        self ! CompleteFailed
        Stop
    }

  override def receive: Receive = {
    case Handle(creds) =>
      val worker = context.actorOf(Props[Worker])
      worker ! Authenticate(creds)
    case CompleteSuccessful =>
      complete(StatusCodes.OK)
      context stop sender()
      context stop self
    case CompleteFailed =>
      complete(StatusCodes.Unauthorized)
      context stop self
  }
}
