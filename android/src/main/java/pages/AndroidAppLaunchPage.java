package pages;


import managers.AndroidWebDriverManager;

public class AndroidAppLaunchPage {
    private AndroidWebDriverManager app;

    public AndroidAppLaunchPage(AndroidWebDriverManager app) {
        this.app = app;
    }

    public void launchingApp(String deviceName) {
        app.log("Opening the app");
        app.openapp(deviceName);
        app.log("Opened app successfully");
    }

    public void selectingLanguage() {
        app.log("Clicking on Language");
        app.log("Clicking on Continue button");
        //app.click("selectLanguageClick_xpath",true);
        app.wait(5000);
        app.click("continueButtonselectLanguage_id",true);
        app.wait(10000);
        app.takeScreenShot();
    }
}
