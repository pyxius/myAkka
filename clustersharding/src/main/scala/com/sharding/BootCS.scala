package com.sharding

import akka.actor.{ActorLogging, Props, ActorSystem}

/**
  * Created by sp71427 on 2/29/2016.
  */
object BootCS {

  def main(args: Array[String]) {

    val system = ActorSystem("TestActorSystem")

    val workerRef = system.actorOf(Props[Worker], "worker")

    val jobProducerRef = system.actorOf(JobProducer.props(workerRef), "jobProducer")

    jobProducerRef ! Start("all Workers")

    //system.awaitTermination()

  }



}
