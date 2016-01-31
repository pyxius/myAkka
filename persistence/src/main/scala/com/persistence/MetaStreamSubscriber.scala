package com.persistence

import akka.actor.{Props, ActorRef, ActorLogging, Actor}

case class PauseEvent(data:String)

/**
  * Created by sp71427 on 1/31/2016.
  */
class MetaStreamSubscriber(ssActorRef: ActorRef) extends Actor with ActorLogging{

  /**
    * handle Receive msg by MSS Actor
    *
    * @return
    */
  def receive: Receive = {
    case PauseEvent(data:String) => {
      data match {
        case "PAUSE" => {
          println(s"MSS - Sending PAUSE to SS")
          ssActorRef ! Cmd("PAUSE")
        }
      }
    }
    case _ =>
      println(s"MSS - Unhandled msg in MSS receive...")
  }

}

object MetaStreamSubscriber {
  /**
    * Constructor of MSS
    * @param ssActorRef
    * @return
    */
  def props(ssActorRef: ActorRef): Props = {
    val props = Props(classOf[MetaStreamSubscriber], ssActorRef)
    props
  }
}
