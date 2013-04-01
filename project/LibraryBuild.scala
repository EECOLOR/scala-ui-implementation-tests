import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object LibraryBuild extends Build {

  val appName = "scala-ui-implementation-tests"
  val appVersion = "1.0"
  val scalaVersion = "2.10.1"
    
  val appDependencies = Seq(
     "org.specs2" %% "specs2" % "1.14" % "test",
     "junit" % "junit" % "4.11" % "test",
    "org.scala-lang" % "scala-compiler" % scalaVersion % "test")

  val defaultSettings = Seq(
    libraryDependencies ++= appDependencies,
    Keys.scalaVersion := scalaVersion,
    scalacOptions += "-feature",
    fork := true,
    resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases"))

  val eclipseSettings = Seq(
    EclipseKeys.withSource := true,
    unmanagedSourceDirectories in Compile <<= Seq(scalaSource in Compile).join,
    unmanagedSourceDirectories in Test <<= Seq(scalaSource in Test).join)

  lazy val root = Project(id = appName,
    base = file("."),
    settings = 
      Project.defaultSettings ++ 
      defaultSettings ++ 
      eclipseSettings) dependsOn (scalaUiProject % "test->test;compile->compile", scalaUiJavaFxProject)

  lazy val scalaUiJavaFxProject = RootProject(file("../scala-ui-javafx"))
  lazy val scalaUiProject = RootProject(file("../scala-ui"))
}

