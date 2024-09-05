package managers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.Before;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URL;


public class AndroidAppiumServer{
    static AppiumDriverLocalService server;


    public AndroidDriver driver;

//    @BeforeClass
//    public void setUp() throws Exception {
//        MutableCapabilities capabilities = new UiAutomator2Options();
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
//    }

//    @AfterMethod(alwaysRun=true)
//    public void tearDown() throws Exception {
//        driver.quit();
//    }

}