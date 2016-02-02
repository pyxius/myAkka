package com.persistence

import akka.actor.{ActorSystem, Props}

/**
  * Created by sp71427 on 1/31/2016.
  */
object BootJS extends App {
  val system = ActorSystem("mySystem")
  val ssRef = system.actorOf(Props[StreamSubscriber], "streamSubscriber")
  val mssRef  = system.actorOf(MetaStreamSubscriber.props(ssRef), "metaStreamSubscriber")

  println("BootJS - Sending PAUSE to MSS...")
  mssRef ! PauseEvent("PAUSE")

  Thread.sleep(15000)
  println(s"BootJS - System shutting down")
  system.shutdown()
}
