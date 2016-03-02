import sbt.Keys._
import sbt._

object HelloWorldBuild extends Build {

  import Dependencies._

  resolvers ++= Seq("Maven Repository" at "https://repo1.maven.org/maven2/")

  lazy val helloWorld = Project("helloWorld", file("helloWorld"))
    .settings(libraryDependencies ++= compile(akkaActor, akkaTestkit, scalaTest))

  lazy val persistence = Project("persistence", file("persistence"))
    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence))

  lazy val persistenceMongo = Project("persistenceMongo", file("persistenceMongo"))
    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence))

  lazy val clustersharding = Project("clustersharding", file("clustersharding"))
    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence, akkaCluster, akkaContrib, akkaTestkit))

  lazy val futurePromise = Project("futurePromise", file("futurePromise"))
    .settings(libraryDependencies ++= compile(akkaActor, scalaLogging, slf4j, logbackClassic))
}
