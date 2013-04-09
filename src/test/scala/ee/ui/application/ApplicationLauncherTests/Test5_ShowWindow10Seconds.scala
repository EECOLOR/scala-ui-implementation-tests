package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._
import ee.ui.display.Window

object Test5_ShowWindow10Seconds extends ApplicationLauncherTestBase {

  "show a window for 10 seconds and then close it" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window: Window) = {

          window.title = "test"
          window.width = 200
          window.height = 100

          show(window)

          for (i <- 1 to 9)
            timer(i.seconds) {
              window.width.value += 20
              window.height.value += 10
            }

          timer(10.seconds) {
            hide(window)
          }
        }
      }
    }

    launch(launcher)

    ok
  }
}