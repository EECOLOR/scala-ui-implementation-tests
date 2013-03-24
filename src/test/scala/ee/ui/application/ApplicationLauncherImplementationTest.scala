package ee.ui.application

import org.specs2.mutable.Specification
import ee.ui.display.Window
import scala.language.reflectiveCalls

class ApplicationLauncherImplementationTest extends Specification {
  xonly
  isolated

  type TestApplicationLauncher = ExampleApplicationLauncher
  
  "ExampleApplicationLauncher" should {
    
    "extend ApplicationLauncher and provide an implementation for launch" in {
    
      new TestApplicationLauncher {
        def createApplication(): Application with Engine = ???
      } must beAnInstanceOf[ApplicationLauncher]
    }
    
    "should create the application from launch" in {
      
      abstract class TestApplication extends Application {
        def start(window:Window) = {
          println("application started")
        }
      }
      
      val launcher = new TestApplicationLauncher {
        lazy val application = new TestApplication with Engine
        def createApplication() = application
      }
      
      var createdApplication:Any = null
      
      launcher.applicationCreated { 
        createdApplication = _
      }
      launcher.main(Array.empty[String])
      
      createdApplication === launcher.application
    }
  }
}