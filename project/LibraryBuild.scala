import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import Tests._

object LibraryBuild extends Build {

  val appName = "scala-ui-implementation-tests"
  val appVersion = "1.0"
  val scalaVersion = "2.10.1"

  val appDependencies = Seq(
    "org.specs2" %% "specs2" % "1.14" % "test",
    "junit" % "junit" % "4.11" % "test",
    "org.scala-lang" % "scala-compiler" % scalaVersion % "test")

  def singleTests(tests: Seq[TestDefinition]) =
    tests filter { test =>
      //println(test.name)
      //test.name == "ee.ui.application.ApplicationLauncherTests.Test5_ResizingWindow"
      test.name == "ee.ui.application.ApplicationLauncherTests.Test6_DancingRectangle"
    } map { test =>
      new Group(
        name = test.name,
        tests = Seq(test),
        runPolicy = SubProcess(javaOptions = Seq.empty[String])) //Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")))
    }

  println("Note to self: you disabled some tests in LibraryBuild.scala")

  val defaultSettings = Seq(
    libraryDependencies ++= appDependencies,
    Keys.scalaVersion := scalaVersion,
    scalacOptions += "-feature",
    fork := true,
    testGrouping <<= definedTests in Test map singleTests,
    //javaOptions in (Test) += "-Xdebug",
    //javaOptions in (Test) += "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005",
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

