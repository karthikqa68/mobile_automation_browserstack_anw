package src.main.java.managers;

//import pages.*;
import src.main.java.managers.IOSWebDriverManager;
import src.main.java.pages.IOSAppLaunchPage;


public class IOSPageObjectManager {
    private IOSAppLaunchPage appLaunchPage;
    private IOSWebDriverManager app;

    public IOSPageObjectManager() {
        this.app = new IOSWebDriverManager();
    }

    public IOSWebDriverManager getIOSWebDriverManager() {
        return app;
    }
    public IOSAppLaunchPage getIOSAppLaunchPage() {
        if (appLaunchPage == null)
            appLaunchPage = new IOSAppLaunchPage(app);
        return appLaunchPage;
    }
}