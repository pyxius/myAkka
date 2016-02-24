package com.sharding

import akka.actor.{ActorSystem, Props}

/**
  * Created by sp71427 on 1/31/2016.
  */
object BootJS extends App {
  val system = ActorSystem("ClusterSystem")
  val ssRef = system.actorOf(Props[Counter], "counter")

  Thread.sleep(5000)

  println(ssRef.path)
  println("Ready to send message...")
  ssRef ! Get(2)

//  Thread.sleep(15000)
//  println(s"BootJS - System shutting down")
//  system.shutdown()
}
