package ee.ui.application.ApplicationLauncherTests

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions
import utils.ApplicationLauncherUtils
import ee.ui.display.Window
import ee.ui.application.Application

trait ApplicationLauncherTestBase extends Specification
  with NoTimeConversions 
  with ApplicationLauncherUnderTest 
  with ApplicationLauncherUtils {
  
  abstract class TestApplication extends Application {
    def start(window: Window) = {}
  }

  trait CreateApplication { self: TestApplicationLauncher =>
    def createApplication() = new TestApplication with Engine
  }
}