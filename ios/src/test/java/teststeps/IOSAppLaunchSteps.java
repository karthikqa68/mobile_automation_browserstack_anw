//package teststeps;
//
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import io.cucumber.java.Status;
//import io.cucumber.java.en.When;
//import io.cucumber.java.en.Then;
//
//public class IOSAppLaunchSteps {
//
//    private context.AndroidTestContext context;
//    private pages.AndroidAppLaunchPage androidAppLaunchPage;
//
//    public IOSAppLaunchSteps(context.AndroidTestContext context) {
//        this.context = context;
//        this.androidAppLaunchPage = context.getPageObjectManager().getAppLaunchPage();
//    }
//
//    @Before
//    public void before(Scenario scenario) {
//        managers.IOSAppiumServer.start();
//        String[] tags = scenario.getSourceTagNames().toArray(new String[0]);
//        context.createScenario(scenario.getName(), tags);
//        context.log("Starting scenario " + scenario.getName());
//    }
//
//    @After
//    public void after(Scenario scenario) {
//        context.log("Ending scenario " + scenario.getName());
//        if (scenario.getStatus() == Status.FAILED) {
//            context.getPageObjectManager().getWebDriverManager().reportFailure("Scenario has not been finished correctly", false);
//        }
//        context.endScenario();
//        context.getPageObjectManager().getWebDriverManager().quit();
//        managers.IOSAppiumServer.stop();
//    }
//
//    @When("I launch the app")
//    public void launchApp() {
//        androidAppLaunchPage.launchingApp();
//    }
//
//    @Then("I select english language")
//    public void selectLanguage() {
//        androidAppLaunchPage.selectingLanguage();
//    }
//}
