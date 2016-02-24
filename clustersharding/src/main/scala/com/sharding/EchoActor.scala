package com.sharding

import akka.actor._
/**
  * Created by sp71427 on 2/23/2016.
  */
class EchoActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case _ => println("World!!")
  }
}
