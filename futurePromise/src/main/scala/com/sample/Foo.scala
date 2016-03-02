package com.sample

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
  * Created by sp71427 on 2/24/2016.
  */

case class SendPing(str:String)
class Foo(barRef: ActorRef) extends Actor with ActorLogging{
  override def receive: Receive = {
    case _ =>
      log.info(s"PING")
      Thread.sleep(2000)
      barRef ! "PING"
  }
}

object Foo {
  def props(barRef:ActorRef): Props ={
    val props = Props(classOf[Foo], barRef)
    props
  }

}
