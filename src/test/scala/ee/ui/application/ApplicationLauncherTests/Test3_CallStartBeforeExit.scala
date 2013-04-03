package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._

object Test3_CallStartBeforeExit extends ApplicationLauncherTestBase {

  "call start before exit is called" in {

    var startCalled = false

    val launcher = new TestApplicationLauncher {
      override def createApplication() = new TestApplication with Engine {
        override def start() = startCalled = true
      }
    }

    val launchedApplication = start(launcher, startUpTime)

    startCalled === true

    launchedApplication.exit()

    ok
  }
}