package managers;

//import pages.*;


public class AndroidPageObjectManager {
    private pages.AndroidAppLaunchPage androidAppLaunchPage;
    private AndroidWebDriverManager app;

    public AndroidPageObjectManager() {
        this.app = new AndroidWebDriverManager();
    }

    public AndroidWebDriverManager getAndroidWebDriverManager() {
        return app;
    }
    public pages.AndroidAppLaunchPage getAndroidAppLaunch() {
        if (androidAppLaunchPage == null)
            androidAppLaunchPage = new pages.AndroidAppLaunchPage(app);
        return androidAppLaunchPage;
    }
}