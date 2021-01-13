package AppiumSupport;

import io.appium.java_client.AppiumDriver;

public abstract class AppiumBaseClass {

    protected AppiumDriver driver() {
        return AppiumController.instance.driver;
    }
}
