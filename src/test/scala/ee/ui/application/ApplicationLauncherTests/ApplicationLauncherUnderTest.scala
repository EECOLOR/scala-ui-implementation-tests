package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._

import ee.ui.application.JavaFxApplicationLauncher

trait ApplicationLauncherUnderTest {
  
  val startUpTime = 5.seconds 
  
  type TestApplicationLauncher = JavaFxApplicationLauncher
  //type TestApplicationLauncher = ExampleApplicationLauncher
}