package teststeps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;

public class AndroidAppLaunchSteps {

    private context.AndroidTestContext context;
    private pages.AndroidAppLaunchPage androidAppLaunchPage;

    public AndroidAppLaunchSteps(context.AndroidTestContext context) {
        this.context = context;
        this.androidAppLaunchPage = context.getAndroidPageObjectManager().getAndroidAppLaunch();
    }

    @Before
    public void before(Scenario scenario) throws InterruptedException {
        //AndroidAppiumServer.start();
        String[] tags = scenario.getSourceTagNames().toArray(new String[0]);
        context.createScenario(scenario.getName(), tags);
        context.log("Starting scenario " + scenario.getName());
    }

    @After
    public void after(Scenario scenario) {
        context.log("Ending scenario " + scenario.getName());
        if (scenario.getStatus() == Status.FAILED) {
            context.getAndroidPageObjectManager().getAndroidWebDriverManager().reportFailure("Scenario has not been finished correctly", false);
        }
        context.endScenario();
        context.getAndroidPageObjectManager().getAndroidWebDriverManager().quit();
        //AndroidAppiumServer.stop();
    }

    @When("I launch the Android app on device-{string}")
    public void launchApp(String deviceName) throws MalformedURLException {
        androidAppLaunchPage.launchingApp(deviceName);
    }

    @Then("I select english language")
    public void selectLanguage() {
        androidAppLaunchPage.selectingLanguage();
    }
}
