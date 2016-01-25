package com.hello

import akka.actor.{Actor, ActorLogging}

object Greeter {
  case object Greet
  case object Done
}

class Greeter extends Actor with ActorLogging{

  def receive = {
    case Greeter.Greet =>
      log.info(s"Hello World")
      sender() ! Greeter.Done
  }
}