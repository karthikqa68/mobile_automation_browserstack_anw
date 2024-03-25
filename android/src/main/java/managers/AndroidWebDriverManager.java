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
import java.util.List;
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
	public  String downloadDirectoryPath;
	AndroidDriver aDriver;

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
					FileInputStream propertyfiles = new FileInputStream(
							System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + file.getName());
					prop.load(propertyfiles);
				}
				String env = prop.getProperty("env") + ".properties";
				FileInputStream envpropertyfile = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/androidproperties/" + env);
				envprop.load(envpropertyfile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			softAssert = new SoftAssert();
		}
	}



	/**
	 * This Method is called for opening the browser
	 *
     */
	public void openApp(String deviceName,String automationType) throws MalformedURLException {
         UiAutomator2Options options = new UiAutomator2Options();
		 if (automationType.equals("app")) {
			 //File appPath = new File(prop.getProperty("apkPath"));
			 System.out.println(System.getProperty("user.dir"));
			 File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/MakeMyTrip.apk");
			 if (deviceName.equals("android11")) {
				 options.setDeviceName(prop.getProperty("android11DeviceName"));
				 options.setPlatformName(prop.getProperty("android11PlatformName"));
				 options.setPlatformVersion(prop.getProperty("android11PlatformVersion"));
				 options.setApp(directoryPath.getAbsolutePath());
				 //options.setSystemPort(4723);
				 //options.setAutomationName("UiAutomator2");
				 //options.setCapability("appWaitForLaunch",false);
				 //options.setCapability("–session-override",true);
				 options.setCapability("uiautomator2ServerLaunchTimeout", 90000);
				 options.setCapability("androidInstallTimeout", 600000);
				 options.setCapability("ignoreHiddenApiPolicyError", true);
				 options.setNoSign(true);
				 options.setFullReset(false);
				 //aDriver =  new AndroidDriver(new URL(prop.getProperty("hubURL")), options);
				 //System.out.println(appPath.getAbsolutePath());
			 } else if (deviceName.equals("android09")) {
				 options.setDeviceName(prop.getProperty("android09DeviceName"));
				 options.setPlatformName(prop.getProperty("android09PlatformName"));
				 options.setPlatformVersion(prop.getProperty("android09PlatformVersion"));
				 options.setApp(directoryPath.getAbsolutePath());
				 options.setCapability("uiautomator2ServerLaunchTimeout", 90000);
				 //options.setSystemPort(4730);
				 //options.setAutomationName("UiAutomator2");
				 //options.setCapability("appWaitForLaunch",false);
				 //options.setCapability("–session-override",true);
				 options.setCapability("androidInstallTimeout", 600000);
				 options.setCapability("ignoreHiddenApiPolicyError", true);
				 options.setNoSign(true);
				 options.setFullReset(false);
				 //aDriver =  new AndroidDriver(new URL(prop.getProperty("hubURL1")), options);
			 }

			 wait(15000);

			 try {
				 //String appiumServerUrl = "http://127:" + options.getCapability("systemPort") + "/wd/hub";
				 //aDriver =  new AndroidDriver(new URL("http://127.0.0.1"), options);
				 //aDriver = new AndroidDriver(options);
				 aDriver = new AndroidDriver(new URL(prop.getProperty("hubURL")), options);
				 //aDriver = (AndroidDriver<AndroidElement>)driver;
				 //iDriver =  (IOSDriver)driver;
			 } catch (Exception e) {
				 e.printStackTrace();
				 Assert.fail("Driver failed to start - " + e.getMessage());
			 }}else {
			 options.setDeviceName(prop.getProperty("android09DeviceName"));
			 options.setPlatformName(prop.getProperty("android09PlatformName"));
			 options.setPlatformVersion(prop.getProperty("android09PlatformVersion"));
			 //options.
			 options.setCapability("browserName", "chrome");

			 wait(15000);

			 try {
				 //String appiumServerUrl = "http://127:" + options.getCapability("systemPort") + "/wd/hub";
				 //aDriver =  new AndroidDriver(new URL("http://127.0.0.1"), options);
				 //aDriver = new AndroidDriver(options);
				 aDriver = new AndroidDriver(new URL(prop.getProperty("hubURL")), options);
				 //aDriver.get(prop.getProperty("url"));
				 //aDriver = (AndroidDriver<AndroidElement>)driver;
				 //iDriver =  (IOSDriver)driver;
			 } catch (Exception e) {
				 e.printStackTrace();
				 Assert.fail("Driver failed to start - " + e.getMessage());
			 }
		 }

		//aDriver.manage().timeouts().implicitlyWait(30);
		setDefaultImplicitlyWait();
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

	/**
	 * This Method is called to check whether a webelement is in enable mode to
	 * click or type
	 *
	 * @param locatorkey Is the webelement(fetched from propertyfile)
	 * @param assertype Is a boolean which tells what type of assertion should be
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
	 * @param assertype Is a boolean which tells what type of assertion should be
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

	public void closeapp(){
		log("Closing app");
		if(aDriver!=null) {
			aDriver.quit();
			aDriver = null;
		}
	}

	public Properties getEnvprop() {
		return envprop;
	}

	public Properties getProp() {
		return prop;
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
			if (locatorkey.endsWith("_xpath"))
				e = aDriver.findElement(AppiumBy.xpath(prop.getProperty(locatorkey)));
			else if (locatorkey.endsWith("_id"))
				e = aDriver.findElement(AppiumBy.id(prop.getProperty(locatorkey)));
			else if (locatorkey.endsWith("_name"))
				e = aDriver.findElement(AppiumBy.name(prop.getProperty(locatorkey)));
			else if (locatorkey.endsWith("_webtablexpath"))
				e = aDriver.findElement(AppiumBy.xpath(locatorkey));

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
	 * This Method is called for clearing data in a webelementt
	 *
	 * @param locatorKey Is the webelement from which data needs to be
	 *                   cleared(fetched from propertyfile)
	 */
	public void clearText(String locatorKey, boolean assertype) {
		wait(1000);
		log("Clearing text field " + locatorKey);
		getElement(locatorKey, assertype).clear();
	}

	/**
	 * This Method is called for clicking on enter button
	 *
	 * @param locatorKey Is the webelement on which enter buton needs to be
	 *                   clicked(fetched from propertyfile)
	 */
	public void clickEnterButton(String locatorKey, boolean assertype) {
		log("Clicking Enter button");
		getElement(locatorKey, assertype).sendKeys(Keys.ENTER);
	}

	/**
	 * This Method is called to check whether webelement is present in the opened
	 * page or not
	 *
	 * @param locatorkey Is the webelement(fetched from propertyfile)
	 * @param assertype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hardassert or
	 *                   sortassert should be done.
	 * @return Boolen ,will return true if webelemt is found or else will return
	 *         false
	 */
	public boolean isElementPresent(String locatorkey, boolean assertype) {
		try {
			List<WebElement> webelementlist = null;
			if (locatorkey.endsWith("_xpath"))
				webelementlist = aDriver.findElements(By.xpath(prop.getProperty(locatorkey)));
			else if (locatorkey.endsWith("_id"))
				webelementlist = aDriver.findElements(By.id(prop.getProperty(locatorkey)));
			else if (locatorkey.endsWith("_name"))
				webelementlist = aDriver.findElements(By.name(prop.getProperty(locatorkey)));
			else {
				takeScreenShot();
				reportFailure("Locator not found" + locatorkey, assertype);
			}
			if (webelementlist.size() == 0) {
				if(assertype == false){
					return false;
				}else {
					reportFailure("WebElement " + locatorkey + " is not Present", assertype);
					return false;
				}
			} else {
				if(assertype == false){
					return true;
				}else {
					reportPass("WebElement " + locatorkey + " is Present");
					return true;
				}
			}
		} catch (Exception ex) {
			reportFailure(ex.getMessage(), assertype);
			ex.printStackTrace();
			Assert.fail("Fail the test" + ex.getMessage());
			return false;
		}
	}
	
	/**
	 * This Method is called to check whether webelement is present in the opened
	 * page within the period of time passed as an argument
	 *
	 * @param locatorKey Is the webelement(fetched from propertyfile)
	 * @param timeoutInSeconds Is a int which indicates how long the method will wait for element before failing the test
	 */
	public void waitForElement(String locatorKey, int timeoutInSeconds) {
		  Date start = new Date();
		  while(!this.isElementPresent(locatorKey, false)
				  && new Date().getTime() - timeoutInSeconds * 1000 < start.getTime()){
			  this.wait(500);
	        }
		  if(new Date().getTime() - timeoutInSeconds * 1000 > start.getTime()) {
			  takeScreenShot();
				reportFailure("Locator not found in timeout" + timeoutInSeconds, true);
				Assert.fail("\"Locator not found in timeout" + timeoutInSeconds);
		  }
	}

	/**
	 * This Method is called to check whether webelement is not present in the
	 * opened page or not
	 *
	 * @param locatorkey Is the webelement(fetched from propertyfile)
	 * @param assertype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hardassert or
	 *                   sortassert should be done.
	 * @return Boolen ,will return true if webelemt is not found or else will return
	 *         false
	 */
	public boolean isElementnotPresent(String locatorkey, boolean assertype) {
		List<WebElement> webelementlist = null;

		if (locatorkey.endsWith("_xpath"))
			webelementlist = aDriver.findElements(By.xpath(prop.getProperty(locatorkey)));
		else if (locatorkey.endsWith("_id"))
			webelementlist = aDriver.findElements(By.id(prop.getProperty(locatorkey)));
		else if (locatorkey.endsWith("_name"))
			webelementlist = aDriver.findElements(By.name(prop.getProperty(locatorkey)));
		else {
			takeScreenShot();
			reportFailure("Locator not found" + locatorkey, assertype);
			Assert.fail("Locator not found" + locatorkey);
		}
		if (webelementlist.size() == 0) {
			reportPass("WebElement is not Present");
			return false;
		} else {
			reportFailure("WebElement is Present", assertype);
			return true;
		}
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

		softAssert.fail(failuremsg);// report failure in testng
		if (stoponfailure == true) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
			AssertAll();
		}
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

			// ExtentTest.log(Status.INFO,"Screenshot-> "+
			// ExtentTest.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+ "/" +
			// screenshotFile));
			test.log(Status.INFO, "Screenshot-> "
					+ test.addScreenCaptureFromPath("screenshots" + "/" + screenshotFile));
			// MediaEntityBuilder
			// test.log(Status.INFO,"Screenshot-> "+
			// MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotFolderPath+
			// "/" + screenshotFile).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void quit() {
		wait(2000);
		if(aDriver!=null) {
			aDriver.quit();
		}
	}


	/**
	 * This Method is called to check whether text is present in the opened web page
	 * or not
	 *
	 * @param locatorkey Is the web element(fetched from property file)
	 * @param text       Is the text which needs to be verified
	 * @param asserttype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hard assert or
	 *                   sort assert should be done.
	 * @return Boolen ,will return true if text is present or else will return false
	 */
	public boolean verifyText(String locatorkey, String text, boolean asserttype) {
		String ActualText = getElement(locatorkey, asserttype).getText().trim().replaceAll("[\\t\\n\\r]+", " ");
		System.out.println(ActualText);
		// String ExpectedText = prop.getProperty(text);
		if (ActualText.equals(text)) {
			reportPass(text + " is present in the webpage based on the locatorkey");
			return true;
		} else {
			reportFailure(text + " is not present in the webpage based on the locatorkey", asserttype);
			return false;
		}
	}


	/**
	 * This Method is called to check whether text is present in the opened webpage
	 * or not
	 *
	 * @param text       Is the text which needs to be verified
	 * @param asserttype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hardassert or
	 *                   sortassert should be done.
	 */
	public boolean verifyTextinPage(String text, boolean asserttype) {
		wait(2000);
		if (aDriver.getPageSource().contains(text)) {
			reportPass(text + " " + "is present in the webpage");
			return true;
		} else {
			reportFailure(text + " " + "is not present in the webpage", asserttype);
			return false;
		}
	}

	/**
	 * This Method is called to check whether text is not present in the opened
	 * webpage or not
	 *
	 * @param text       Is the text which needs to be verified
	 * @param asserttype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hardassert or
	 *                   sortassert should be done.
	 */
	public boolean verifyTextNotinPage(String text, boolean asserttype) {
		if (aDriver.getPageSource().contains(text)) {
			reportFailure(text + " " + "is present in the webpage", asserttype);
			return false;
		} else {
			reportPass(text + " " + "is not present in the webpage");
			return true;
		}
	}

	/**
	 * This Method is called to select the value from the Drop down
	 * 
	 * @param locatorkey Is the webelement(fetched from propertyfile)
	 * @param text       Is the text which needs to be verified
	 */
	public void selectValueDropdown(String locatorkey, String text) {
		List<WebElement> ele = aDriver.findElements(By.xpath(prop.getProperty(locatorkey)));
		System.out.println(ele.size());
		for (WebElement i : ele) {
			System.out.println(i.getText());
			if (i.getText().equals(text)) {
				i.click();
				break;
			}
		}
	}


	/**
	 * The ClearData method is used to clearing the text in the text box.
	 *
	 * @param locatorKey Is the webelement of the text box
	 * @param assertype Is a boolean which tells what type of assertion should be
	 *                   done in case of pass and failure whether hardassert or
	 *                   sortassert should be done.
	 */
	public void clearData(String locatorKey, boolean assertype) {
		log("Clearing text field " + locatorKey);
		getElement(locatorKey, assertype).sendKeys(Keys.CONTROL + "a");
		getElement(locatorKey, assertype).sendKeys(Keys.DELETE);
	}
	
	public JavascriptExecutor getJavascriptExecutor() {
		return (JavascriptExecutor) aDriver;
	}
		
	public void setDefaultImplicitlyWait() {
		setImplicitlyWait(40);
	}
	
	public void setImplicitlyWait(int waitInSeconds) {
		aDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitInSeconds));
	}


}
