package com.persistence

/**
  * Created by sp71427 on 1/30/2016.
  */
object Boot extends Bootable {

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[ExamplePersistentActor].getName()))
  }

}
