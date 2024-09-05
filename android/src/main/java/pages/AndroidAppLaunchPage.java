package pages;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import managers.AndroidWebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;

public class AndroidAppLaunchPage {
    private AndroidWebDriverManager app;
    private AndroidDriver aDriver;


    public AndroidAppLaunchPage(AndroidWebDriverManager app) {
        this.app = app;
        this.aDriver = app.getDriver();
    }

    public void selectingLanguage() throws InterruptedException {
        app.click("searchWikipedia_accessibilityid", true);
        app.wait(4000);
    }

    public void enteringText(String text)
    {
        app.clickAndEnterText("search_id", text);
        System.out.println("Executed scuccesfuly in anw mobile automation");
    }
}
