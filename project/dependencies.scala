import sbt._

object Versions {
  val akkaVersion = "2.3.14"
  val akkaPersistenceVersion = "2.3.14"
  val akkaTestkitVersion = "2.3.14"
  val scalaTestVersion = "3.0.0-M1"
  val akkaClusterVersion = "2.3.14"
  val akkContribVersion = "2.3.14"

  //Logging
  val scalaLoggingVersion = "2.1.2"
  val slf4jVersion = "1.7.12"
  val logbackClassicVersion = "1.1.3"

  //SdpCommonsVersion
  //val SdpCommonsVersion = "9.8.0"
}

/**
  *
  */
object Dependencies {
  import Versions._

  // scopes
  def compile (deps: ModuleID*): Seq[ModuleID] =  deps map (_ % "compile")

  //Akka
  lazy val akkaActor =  "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence-experimental" % akkaVersion
  lazy val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaTestkitVersion

  lazy val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % akkaClusterVersion
  lazy val akkaContrib = "com.typesafe.akka" %% "akka-contrib" % akkContribVersion
  lazy val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  lazy val akkaMultiNodeTestKit = "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion

  //Scala
  lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion


  //Logging
  lazy val scalaLogging = "com.typesafe.scala-logging" % "scala-logging-slf4j_2.10"  % scalaLoggingVersion
  lazy val slf4j = "org.slf4j" % "slf4j-api" % slf4jVersion
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackClassicVersion

  //SDP-Commons
  //lazy val sdpCommonsMonitoring = "com.deere.isg.sdp-commons" %% "sdp-commons-monitoring" % SdpCommonsVersion
}
