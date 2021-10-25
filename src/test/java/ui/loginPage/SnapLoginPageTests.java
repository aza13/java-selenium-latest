package ui.loginPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import pageActions.SnapLoginPageActions;
import utils.dataProvider.TestDataProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class SnapLoginPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapLoginPageTests.class);

    private SnapLoginPageActions snapLoginPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("SnapLoginPageTests");
        logger.info("Creating object for SnapLoginPageActions :: beforeClassSetUp");
        snapLoginPageActions = PageObjectManager.getSnapLoginPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "LoginPageData")
    public void testValidateLoginFunctionality(Map<String, String> map) throws InterruptedException {

        logger.info("Executing the test ValidateLoginFunctionalityTest");
        snapLoginPageActions.enterLoginCredentials(DriverManager.getDriver(), map.get("userName"), map.get("password"), map.get("answer"));
    }
}
