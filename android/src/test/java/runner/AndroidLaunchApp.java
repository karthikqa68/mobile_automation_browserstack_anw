package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        // features = "@rerun/failed_scenarios.txt",
        features = "src/test/resources/features/MakeMyTripLaunchApp/AndroidAppLaunch.feature", glue = { "teststeps" }, plugin = {
        "html:target/cucumber-reports.html", "rerun:rerun/failed_scenarios.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "html:target/cucumber-html-report", "json:target/cucumber.json",
        "junit:target/cucumber.xml" }, tags = "@RegressionWorking")

public class AndroidLaunchApp extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}