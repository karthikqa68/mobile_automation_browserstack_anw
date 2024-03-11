package context;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import managers.AndroidPageObjectManager;
import reports.ExtentManager;

public class AndroidTestContext {
	private final AndroidPageObjectManager androidPageObjectManager;
	private ExtentReports report;
	private ExtentTest test;

	public AndroidTestContext() {
		report = ExtentManager.getReports();// initialize the reports
		this.androidPageObjectManager = new AndroidPageObjectManager();
	}

	public AndroidPageObjectManager getAndroidPageObjectManager() {
		return androidPageObjectManager;
	}

	public void createScenario(String scenarioName,String[] tags) {
		test = report.createTest(scenarioName);	
		
        for (String tag : tags) {
            if (tag.startsWith("@Category_")) {
                String category = tag.replace("@Category_", "");
                test.assignCategory(category);
                test.log(Status.INFO, "Test case category: " + category);
            }
		this.androidPageObjectManager.getAndroidWebDriverManager().init(test);
	}}

	public void endScenario() {
		this.androidPageObjectManager.getAndroidWebDriverManager().quit();
		report.flush();
	}

	public void log(String msg) {
		this.androidPageObjectManager.getAndroidWebDriverManager().log(msg);
	}
	
}
