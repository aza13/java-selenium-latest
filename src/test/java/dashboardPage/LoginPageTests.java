package dashboardPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;

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
         story - N2020-28282
         **/
        logger.info("verifying login functionality :: testQuotesDashboardUI");
        if (map.get("scenario").equalsIgnoreCase("validData")){
            loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            WaitHelper.pause(3000);
            assert DriverManager.getDriver().getCurrentUrl().contains("dashboard");

        }else if (map.get("scenario").equalsIgnoreCase("invalidData")){
            loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            assert loginPageActions.invalidUserNamePasswordText(DriverManager.getDriver()).isDisplayed();

        }else if (map.get("scenario").equalsIgnoreCase("noData")){
            loginPageActions.loginApp(DriverManager.getDriver(), "", "");
            assert loginPageActions.pleaseProvideEmailPasswordText(DriverManager.getDriver()).isDisplayed();
        }
    }
}
