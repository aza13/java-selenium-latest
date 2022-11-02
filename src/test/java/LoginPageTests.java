import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;
import utils.fileReader.ConfigDataReader;

import java.util.Map;

public class LoginPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(LoginPageTests.class);
    private LoginPageActions loginPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("LoginPageTests");
        logger.info("Creating object for LoginPageTest :: beforeClassSetUp");
        loginPageActions = PageObjectManager.getLoginPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "LoginPageData")
    public void testLoginFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies login functionality
         story - N2020-28282, 35082
         **/
        logger.info("verifying login functionality :: testQuotesDashboardUI");
        String description = loginPageActions.getLandingPageDescription(DriverManager.getDriver()).trim();
        assert !description.contains("TMHCC") && !description.contains(".");
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        if (map.get("scenario").equalsIgnoreCase("validData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("userId"), ConfigDataReader.getInstance().getProperty("password"));
            WaitHelper.pause(3000);
            assert DriverManager.getDriver().getCurrentUrl().contains("dashboard");
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
            assert !dashboardPageActions.isBrokerIdDisplayed(DriverManager.getDriver());
        } else if (map.get("scenario").equalsIgnoreCase("invalidData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            assert loginPageActions.invalidUserNamePasswordText(DriverManager.getDriver()).isDisplayed();

        } else if (map.get("scenario").equalsIgnoreCase("noData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), "", "");
            assert loginPageActions.pleaseProvideEmailPasswordText(DriverManager.getDriver()).isDisplayed();
        } else if (map.get("scenario").equalsIgnoreCase("logout")) {
            WaitHelper.pause(3000);
            loginPageActions.loginApp(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("userId"), ConfigDataReader.getInstance().getProperty("password"));
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
            dashboardPageActions.signOutLink(DriverManager.getDriver()).click();
            String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
            assert text.contentEquals("Welcome to QuoteIt");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "LoginPageData")
    public void testResetPassword(Map<String, String> map) {
        /***
         this test verifies login functionality
         story - N2020-28284
         Author  - Sheetal
         **/
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        dashboardPageActions.logoutApp(DriverManager.getDriver());
        loginPageActions.resetPassword(DriverManager.getDriver(), map.get("userId"));
    }


}
