package managers;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import reports.ExtentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;


public class AndroidWebDriverManager {

    private WebElement resultWebelemt;
    private WebDriver driver;
    private ExtentTest test;
    private Properties prop;
    private Properties envprop;
    private SoftAssert softAssert;
    private Actions actions;
    private Proxy proxy;
    public String downloadDirectoryPath;
    private AndroidDriver aDriver;


    // Constructor to initialize the AndroidDriver instance
    public AndroidWebDriverManager(AndroidDriver aDriver, ExtentTest test) {
        this.aDriver = aDriver; // Assign the passed AndroidDriver to the class variable 'aDriver'
        this.test = test; // Ensure test is passed and initialized

        prop = new Properties(); // Initialize prop here
        envprop = new Properties(); // Initialize envprop here
        loadProperties();
    }

    // Method to get the AndroidDriver instance
    public AndroidDriver getDriver() {
        return aDriver; // Return the class variable 'aDriver'
    }

    private void loadProperties() {
        try {
            // Creating a File object for directory
            File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/androidproperties");
            // List of all files and directories
            File[] filesList = directoryPath.listFiles();
            for (File file : filesList) {
                FileInputStream propertyfiles = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + file.getName());
                prop.load(propertyfiles);
            }
            String env = prop.getProperty("env") + ".properties";
            FileInputStream envpropertyfile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + env);
            envprop.load(envpropertyfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public AndroidWebDriverManager() {
        // init the properties file
        if (prop == null) {
            prop = new Properties();
            envprop = new Properties();
            try {
                // Creating a File object for directory
                File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/androidproperties");
                // List of all files and directories
                File filesList[] = directoryPath.listFiles();
                for (File file : filesList) {
                    FileInputStream propertyfiles = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + file.getName());
                    prop.load(propertyfiles);
                }
                String env = prop.getProperty("env") + ".properties";
                FileInputStream envpropertyfile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + env);
                envprop.load(envpropertyfile);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            softAssert = new SoftAssert();
        }
    }


    /**
     * This Method is called for clicking on a webelement
     *
     * @param locatorkey Is the webelement which needs to be clicked on(fetched from
     *                   propertyfile)
     */
    public void click(String locatorkey, boolean assertype) {
        isEnabled(locatorkey, assertype);
        try {
            getElement(locatorkey, assertype).click();
        } catch (Exception ex) {
            reportFailure(ex.getMessage(), assertype);
            ex.printStackTrace();
        }
    }

    public void clickAndEnterText(String locatorKey, String text) {
        try {
            WebElement element = new WebDriverWait(aDriver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(getElement(locatorKey, true)));
            element.click();
            element.sendKeys(text);
        } catch (Exception ex) {
            // Handle exception, maybe log it or report a failure
            ex.printStackTrace();
        }
    }


    /**
     * This Method is called to check whether a webelement is in enable mode to
     * click or type
     *
     * @param locatorkey Is the webelement(fetched from propertyfile)
     * @param assertype  Is a boolean which tells what type of assertion should be
     *                   done in case of pass and failure whether hardassert or
     *                   sortassert should be done
     */
    public void isEnabled(String locatorkey, boolean assertype) {
        isEnabled(locatorkey, assertype, 100);
    }

    /**
     * This Method is called to check whether a webelement is in enable mode to
     * click or type
     *
     * @param locatorkey Is the webelement(fetched from propertyfile)
     * @param assertype  Is a boolean which tells what type of assertion should be
     *                   done in case of pass and failure whether hardassert or
     *                   sortassert should be done
     */
    public void isEnabled(String locatorkey, boolean assertype, int timeout) {
        //new WebDriverWait(aDriver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(getElement(locatorkey, assertype)));
        //visibilityOf(getElement(locatorkey, assertype)));
        test.log(Status.INFO, "Verifying whether the mobileElement- " + locatorkey + " is enabled / clickable");
        String actualValue = Boolean.toString(getElement(locatorkey, assertype).isEnabled());
        String expectedValue = "true";
        if (actualValue.equals(expectedValue)) {
            reportPass("mobileElement- " + locatorkey + " is enabled");
        } else {
            reportFailure("mobileElement- " + locatorkey + " is not enabled", assertype);
        }
    }

    public void init(ExtentTest test) {
        this.test = test;
    }

    /**
     * This Method is called for getting the webelement
     *
     * @param locatorkey Is the webelement(fetched from propertyfile)
     * @return will return the webelement if found or else failure message will be
     * logged
     */
    public WebElement getElement(String locatorkey, boolean asserttype) {
        WebElement e = null;
        try {
            if (locatorkey.endsWith("_xpath")) e = aDriver.findElement(AppiumBy.xpath(prop.getProperty(locatorkey)));
            else if (locatorkey.endsWith("_id")) e = aDriver.findElement(AppiumBy.id(prop.getProperty(locatorkey)));
            else if (locatorkey.endsWith("_accessibilityid"))
                e = aDriver.findElement(AppiumBy.accessibilityId(prop.getProperty(locatorkey)));
            else if (locatorkey.endsWith("_name")) e = aDriver.findElement(AppiumBy.name(prop.getProperty(locatorkey)));
            else if (locatorkey.endsWith("_webtablexpath")) e = aDriver.findElement(AppiumBy.xpath(locatorkey));

            else {
                System.out.println("Locatorykey is not correct" + " " + locatorkey);
                reportFailure("Locatorykey is not correct" + " " + locatorkey, asserttype);
            }
        } catch (Exception ex) {
            reportFailure(ex.getMessage(), asserttype);
            ex.printStackTrace();
        }
        return e;
    }

    /**
     * This Method is called for the test execution to wait implicitily,till the
     * speceified time
     *
     * @param milliseconds Is the time for the application to wait
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This Method is called for printing messages in the report
     *
     * @param msg Pass the message which needs to printed
     */
    public void log(String msg) {
        test.log(Status.INFO, msg);
        System.out.println(msg);
    }

    /**
     * This Method is called to log in the report when a test cases is failed and
     * also we can pass type of failure using the stoponfailure param
     *
     * @param failuremsg    Pass the message which needs to printed
     * @param stoponfailure Pass boolean value to specify whether it is critical
     *                      failure or not
     */
    public void reportFailure(String failuremsg, boolean stoponfailure) {

        test.log(Status.FAIL, MarkupHelper.createLabel(failuremsg, ExtentColor.RED));// report failure in extent report
        takeScreenShot();// Put screen-shot in report

//		softAssert.fail(failuremsg);// report failure in testng
//		if (stoponfailure == true) {
//			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
//			AssertAll();
//		}
    }

    /**
     * This Method is called to stop the test execution
     */
    public void AssertAll() {
        softAssert.assertAll();
    }

    /**
     * This Method is called to log in the report when a test case is passed
     *
     * @param msg Pass the message which needs to printed
     */
    public void reportPass(String msg) {
        test.log(Status.PASS, MarkupHelper.createLabel(msg, ExtentColor.GREEN));
        takeScreenShot();
    }

    /**
     * This Method is called to take a screen-shot
     */
    public void takeScreenShot() {
        // fileName of the screenshot
        Date d = new Date();
        String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
        // Take Screen-shot
        File scrFile = ((TakesScreenshot) aDriver).getScreenshotAs(OutputType.FILE);
        try {
            // get the dynamic folder name
            FileUtils.copyFile(scrFile, new File(ExtentManager.screenshotFolderPath + "/" + screenshotFile));
            // adding the screen-shot in extent reports
            test.log(Status.INFO, "Screenshot-> " + test.addScreenCaptureFromPath("screenshots" + "/" + screenshotFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void quit() {
        wait(2000);
        if (aDriver != null) {
            aDriver.quit();
        }
    }

    public void setDefaultImplicitlyWait() {
        setImplicitlyWait(40);
    }

    public void setImplicitlyWait(int waitInSeconds) {
        aDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitInSeconds));
    }
}
