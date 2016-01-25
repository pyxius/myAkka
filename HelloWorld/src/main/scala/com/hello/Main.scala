package com.hello

/**
  * Created by sp71427 on 1/24/2016.
  */
object Main {

    def main(args: Array[String]): Unit = {
      akka.Main.main(Array(classOf[HelloWorld].getName()))
    }

}
