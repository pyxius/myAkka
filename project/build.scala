import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.license.Apache2_0
import sbt.Keys._
import sbt._



object HelloWorldBuild extends AutoPlugin {

  override def requires = plugins.JvmPlugin && HeaderPlugin

  override def trigger = allRequirements

  import BuildSettings._
  import Dependencies._

  resolvers ++= Seq("Maven Repository" at "https://repo1.maven.org/maven2/")


  override def projectSettings =
  // Core settings
    List(
      organization := "de.heikoseeberger",
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),

      scalacOptions ++= List(
        "-unchecked",
        "-deprecation",
        "-language:_",
        "-target:jvm-1.7",
        "-encoding", "UTF-8"
      ),
      unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value),
      unmanagedSourceDirectories.in(Test) := List(scalaSource.in(Test).value),
      publishMavenStyle := false
    ) ++
      //      // Scalariform settings
      //      List(
      //        SbtScalariform.autoImport.preferences := SbtScalariform.autoImport.preferences.value
      //          .setPreference(AlignSingleLineCaseStatements, true)
      //          .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
      //          .setPreference(DoubleIndentClassDeclaration, true)
      //      ) ++
      //      // Git settings
      //      List(
      //        GitPlugin.autoImport.git.useGitDescribe := true
      //      ) ++
      // Header settings
      List(
        HeaderPlugin.autoImport.headers := Map(
          "scala" -> Apache2_0("2015", "Heiko Seeberger")
        )
      )


  // root project
  lazy val root = Project("root", file("."))
    .aggregate(allModules: _*)
    .settings(basicSettings: _*)
    .settings(Defaults.defaultSettings:_*)
    .settings(clusterShardingSettings:_*)




  lazy val allModules: Seq[ProjectReference] = Seq(helloWorld)

  lazy val helloWorld = Project("helloWorld", file("helloWorld"))
    .settings(libraryDependencies ++= compile(akkaActor, akkaTestkit, scalaTest))

//  lazy val persistence = Project("persistence", file("persistence"))
//    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence))
//
//  lazy val persistenceMongo = Project("persistenceMongo", file("persistenceMongo"))
//    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence))
//
//  lazy val clustersharding = Project("clustersharding", file("clustersharding"))
//    .settings(libraryDependencies ++= compile(akkaActor, akkaPersistence, akkaCluster, akkaContrib, akkaTestkit))
//    .settings(clusterShardingSettings: _*)
//
//  lazy val futurePromise = Project("futurePromise", file("futurePromise"))
//    .settings(libraryDependencies ++= compile(akkaActor, scalaLogging, slf4j, logbackClassic))





}
