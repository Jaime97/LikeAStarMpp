package AppiumSupport;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppiumController {

    public enum OS {
        ANDROID,
        IOS
    }

    public static OS executionOS = OS.IOS;


    public static AppiumController instance = new AppiumController();
    public AppiumDriver driver;

    public void start() throws MalformedURLException {
        if (driver != null) {
            return;
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch(executionOS){
            case ANDROID:
                File projectDir = new File(System.getProperty("user.dir"));
                File app = new File(projectDir.getParent(), "/android-app/build/outputs/apk/debug/android-app-debug.apk");

                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("deviceName", "NotUsed");
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability("appPackage","com.jaa.likeastarappmpp.debug");
                capabilities.setCapability("appActivity","com.jaa.likeastarappmpp.view.FilmListActivity");
                capabilities.setCapability("automationName","UiAutomator2");
                capabilities.setCapability("allowTestPackages","true");

                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                break;
            case IOS:
                projectDir = new File(System.getProperty("user.dir"));
                app = new File(projectDir.getParent(), "/ios-app/LikeAStarApp.app");

                capabilities.setCapability("platformName", "ios");
                capabilities.setCapability("deviceName", "iPhone 8");
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability("automationName", "XCUITest");
                capabilities.setCapability("wdaStartupRetries", "4");
                capabilities.setCapability("iosInstallPause", "8000");
                capabilities.setCapability("wdaStartupRetryInterval", "20000");
                capabilities.setCapability("showXcodeLog", "true");


                driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                break;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
