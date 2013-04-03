package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._

object Test4_CallStopOnExit extends ApplicationLauncherTestBase {

  "call stop on the application if an exit is received" in {

    var stopCalled = false

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def stop() = stopCalled = true
      }
    }

    startAndExitApplication(launcher, startUpTime)

    stopCalled === true
  }
}