import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import com.aventstack.extentreports.Status;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.LoginPageActions;
import utils.dataProvider.TestDataProvider;

import java.io.IOException;
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
        if (map.get("scenario").equalsIgnoreCase("validData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            WaitHelper.pause(3000);
            assert DriverManager.getDriver().getCurrentUrl().contains("dashboard");

        } else if (map.get("scenario").equalsIgnoreCase("invalidData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            assert loginPageActions.invalidUserNamePasswordText(DriverManager.getDriver()).isDisplayed();

        } else if (map.get("scenario").equalsIgnoreCase("noData")) {
            loginPageActions.loginApp(DriverManager.getDriver(), "", "");
            assert loginPageActions.pleaseProvideEmailPasswordText(DriverManager.getDriver()).isDisplayed();
        } else if (map.get("scenario").equalsIgnoreCase("logout")) {
            DashboardPageActions dashboardPageActions = loginPageActions.loginApp(DriverManager.getDriver(), map.get("userId"), map.get("userPassword"));
            WaitHelper.pause(3000);
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
            dashboardPageActions.signOutLink(DriverManager.getDriver()).click();
            String text = loginPageActions.getWelcomeText(DriverManager.getDriver());
            assert text.contentEquals("Welcome to the Broker Portal");

        }
    }

    @AfterMethod(alwaysRun = true)
    public static synchronized void updateTestStatus(ITestResult result) {
        System.out.println("In After Method :: "+result.getName());
        System.out.println("In After Method :: "+result.getStatus());

        logger.info("updating result of test script " + result.getName() + " to report :: updateTestStatus");
        try {
            logTestStatusToReport(DriverManager.getDriver(), result);
        } catch (IOException e) {
            logger.error("Failed to update the status of the test case:: updateTestStatus" + e);
        }
        DriverManager.quitDriver();
        testLogger.log(Status.PASS, "Closed the browser successfully");
    }
}
