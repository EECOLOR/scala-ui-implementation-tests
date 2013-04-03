package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._

object Test1_BlockThread extends ApplicationLauncherTestBase {

  "block the thread until exit is called" in {

    val launcher = new TestApplicationLauncher with CreateApplication

    var blocked = true
    inThread {
      launcher.main(Array.empty)
      blocked = false
    }

    blocked === true

    val launchedApplication = waitFor(launcher.application, startUpTime)

    blocked === true

    launchedApplication.exit()

    blocked === false
  }
}