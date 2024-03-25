package pages;


import managers.AndroidWebDriverManager;

import java.net.MalformedURLException;

public class AndroidAppLaunchPage {
    private AndroidWebDriverManager app;

    public AndroidAppLaunchPage(AndroidWebDriverManager app) {
        this.app = app;
    }

    public void launchingApp(String deviceName,String automationType) throws MalformedURLException {
        app.log("Opening the app");
        app.openApp(deviceName,automationType);
        app.log("Opened app successfully");
    }

    public void selectingLanguage() {
        app.log("Clicking on Language");
        app.log("Clicking on Continue button");
        //app.click("selectLanguageClick_xpath",true);
        app.wait(12000);
        app.click("continueButtonselectLanguages_xpath",true);
        app.wait(11000);
        app.takeScreenShot();
    }
}
