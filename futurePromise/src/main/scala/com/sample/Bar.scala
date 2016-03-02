package com.sample

import akka.actor.{Actor, ActorLogging}

/**
  * Created by sp71427 on 2/24/2016.
  */

class Bar extends Actor with ActorLogging{

  override def receive: Receive = {
    case _ =>
      log.info(s"PONG")
      sender() ! "PONG"
  }
}

object Bar {

}
