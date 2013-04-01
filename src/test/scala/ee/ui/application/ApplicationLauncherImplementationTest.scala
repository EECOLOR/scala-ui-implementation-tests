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

class ApplicationLauncherImplementationTest extends Specification with NoTimeConversions with ApplicationLauncherUtils {
  //xonly
  isolated

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

    "block the thread until exit is called" in {
      val launcher = new TestApplicationLauncher with CreateApplication

      var blocked = true
      inThread {
        launcher.main(Array.empty)
        blocked = false
      }

      Thread.sleep(500)
      
      blocked === true

      val launchedApplication = waitFor(launcher.application)

      launchedApplication.exit()

      blocked === false
    }

    "create the application from launch" in {

      val launcher = new TestApplicationLauncher {
        lazy val createdApplication = new TestApplication with Engine
        def createApplication() = createdApplication
      }

      val createdApplication = start(launcher, 1.second)

      createdApplication.exit()

      createdApplication === launcher.createApplication
    }

    "call start before exit is called" in {
      var startCalled = false
      
      val launcher = new TestApplicationLauncher {
        override def createApplication() = new TestApplication with Engine {
          override def start() = startCalled = true
        }
      }

      val launchedApplication = start(launcher, 1.second)
      
      startCalled === true

      launchedApplication.exit()

      ok
    }
    
    "call stop on the application if an exit is received" in {

      var stopCalled = false

      val launcher = new TestApplicationLauncher {
        def createApplication() = new TestApplication with Engine {
          override def stop() = stopCalled = true
        }
      }

      startAndExitApplication(launcher)

      stopCalled === true

    }
  }
}