package com.sharding

import akka.actor.{Props, ActorRef, Actor, ActorLogging}

case class Start(str:String)



class JobProducer(workerRef:ActorRef)extends Actor with ActorLogging{

  def receive: Receive = {
    case msg@Start(str:String) =>
      log.info(s"JobProducer received START message.")
      for(count <- 1 to 10){
        log.info(s"Sending Cmd to Worker Job-$count")
        workerRef ! Cmd(s"Job-$count")
      }
    case _ => log.info(s"Unhandled Message")
  }

}



/**
  * Created by sp71427 on 2/29/2016.
  */
object JobProducer {

  def props(workerRef:ActorRef): Props ={
      val props = Props(classOf[JobProducer], workerRef)
      props
  }


}
