import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.fileReader.ConfigDataReader;

public class LoginPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(LoginPageTests.class);
    private LoginPageActions loginPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("LoginPageTests");
        logger.info("Creating object for LoginPageTest :: beforeClassSetUp");
        loginPageActions = PageObjectManager.getLoginPageActions();
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "LoginPageData")
    public void testLoginFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies login functionality
         story - N2020-28282, 35082
         **/
        logger.info("verifying login functionality :: testQuotesDashboardUI");
        String description = loginPageActions.getLandingPageDescription(DriverManager.getDriver()).trim();
        assert !description.contains("TMHCC") && !description.contains(".");
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        if (jsonObject.get("scenario").equals("validData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("userId"), ConfigDataReader.getInstance().getProperty("password"));
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
            assert DriverManager.getDriver().getCurrentUrl().contains("dashboard");
            assert !dashboardPageActions.isBrokerIdDisplayed(DriverManager.getDriver());
        } else if (jsonObject.get("scenario").equals("invalidData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), jsonObject.get("userId").toString(), jsonObject.get("userPassword").toString());
            assert loginPageActions.invalidUserNamePasswordText(DriverManager.getDriver()).isDisplayed();
        } else if (jsonObject.get("scenario").equals("noData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), "", "");
            assert loginPageActions.pleaseProvideEmailPasswordText(DriverManager.getDriver()).isDisplayed();
        } else if (jsonObject.get("scenario").equals("logout")) {
            WaitHelper.pause(3000);
            loginPageActions.loginApp(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("userId"), ConfigDataReader.getInstance().getProperty("password"));
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
            dashboardPageActions.signOutLink(DriverManager.getDriver()).click();
            String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
            assert text.contentEquals("Welcome to QuoteIt");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "LoginPageData")
    public void testResetPassword(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies login functionality
         story - N2020-28284
         Author  - Sheetal
         **/
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        dashboardPageActions.logoutApp(DriverManager.getDriver());
        loginPageActions.resetPassword(DriverManager.getDriver(), jsonObject.get("userId").toString());
    }


}
