package teststeps;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.AndroidAppiumServer;
import managers.AndroidWebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AndroidAppLaunchPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class AndroidAppLaunchSteps {

    //private context.AndroidTestContext context;

    // private pages.AndroidAppLaunchPage androidAppLaunchPage;
    private AndroidDriver driver;

    private AndroidWebDriverManager app; // This will hold the AndroidWebDriverManager instance
    private AndroidAppLaunchPage androidAppLaunchPage;
    private ExtentReports extent;
    private ExtentTest test;

    @Before
    public void setUp() throws Exception {
        // Initialize the ExtentReports and ExtentTest instances
        extent = new ExtentReports();
        test = extent.createTest("TestName");

        AndroidDriver aDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), new UiAutomator2Options());
        app = new AndroidWebDriverManager(aDriver, test); // Initialize WebDriverManager
        androidAppLaunchPage = new AndroidAppLaunchPage(app); // Initialize AndroidAppLaunchPage
    }

    @When("I click on search wikipedia")
    public void selectLanguage() throws InterruptedException {
        androidAppLaunchPage.selectingLanguage();
    }

    @Then("I enter text in search field- {string}")
    public void enterText(String text)
    {
        androidAppLaunchPage.enteringText(text);

    }

}

