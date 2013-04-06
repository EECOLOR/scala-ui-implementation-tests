package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._
import ee.ui.display.Window

object Test5_ShowWindow10Seconds extends ApplicationLauncherTestBase {

  "show a window for 10 seconds and then close it" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window:Window) = {
          
          window.title = "test"
          window.width = 100
          window.height = 100
            
          show(window)
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