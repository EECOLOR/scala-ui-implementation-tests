package ee.ui.application

import ee.ui.display.Window
import scala.language.reflectiveCalls
import scala.concurrent.Await
import scala.concurrent.duration._
import org.specs2.time.NoTimeConversions
import scala.concurrent.Future
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.mutable.Specification
import utils.ApplicationLauncherUtils

/**
 * Only put tests here that do not launch the application
 */
object ApplicationLauncherImplementationTest extends Specification with NoTimeConversions with ApplicationLauncherUtils {
  //xonly
  sequential
  
  type TestApplicationLauncher = JavaFxApplicationLauncher
  //type TestApplicationLauncher = ExampleApplicationLauncher

  abstract class TestApplication extends Application {
    def start(window: Window) = {}
  }

  trait CreateApplication { self: TestApplicationLauncher =>
    def createApplication() = new TestApplication with Engine
  }

  "ApplicationLauncherImplementation" should {
    "extend ApplicationLauncher and provide an implementation for launch" in {

      new TestApplicationLauncher {
        def createApplication(): Application with Engine = ???
      } must beAnInstanceOf[ApplicationLauncher]
    }
  }
}