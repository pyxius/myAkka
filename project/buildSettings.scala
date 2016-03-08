import sbt.Keys._
import sbt._
import xerial.sbt.Pack._

/**
  *
  */
object BuildSettings {

  val buildScalaVersion = "2.10.4"
  val buildOrganization = "com.sample.clustersharding"

  lazy val clusterShardingSettings =
    Seq(
      packJvmOpts := Map("clustersharding" -> Seq("-Xmx1024m","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/www/logs/")),
      packResourceDir := Map(baseDirectory.value /  "../configuration" -> "conf"),
//      packExtraClasspath := Map("transfer" -> Seq("${PROG_HOME}/config")),
      packMain := Map("clustersharding" -> "com.sample.clustersharding.BootCS"),
      packBatTemplate := "../scripts/templates/launch-bat.mustache",
      packGenerateWindowsBatFile := true
    )





  lazy val basicSettings = Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,

    scalacOptions := Seq(
      "-encoding", "UTF-8",
      //"-feature", // explicit activation of language features
      "-deprecation",
      "-unchecked",
      "-optimize",
      //"-Xlog-reflective-calls",
      "-Xlint"
    ),

    javacOptions := Seq(
      "-encoding", "UTF-8"
    ),

    // dedicated compile options to avoid passing invalid params to JavaDoc and other Java tools
    javacOptions in(Compile, compile) ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    )
  )




}