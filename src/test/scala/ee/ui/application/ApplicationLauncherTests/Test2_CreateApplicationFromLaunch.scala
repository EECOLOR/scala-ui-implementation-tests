package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._

object Test2_CreateApplicationFromLaunch extends ApplicationLauncherTestBase {

  "create the application from launch" in {

    val launcher = new TestApplicationLauncher {
      lazy val createdApplication = new TestApplication with Engine
      def createApplication() = createdApplication
    }

    val createdApplication = start(launcher, startUpTime)

    createdApplication.exit()

    createdApplication === launcher.createApplication
  }
}