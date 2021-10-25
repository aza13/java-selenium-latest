package ui.dashboardPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import pageActions.SnapDashboardPageActions;
import pageActions.SnapLoginPageActions;
import pageActions.SnapPolicyPageActions;
import utils.dataProvider.TestDataProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class SnapPolicyDashboardPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapPolicyDashboardPageTests.class);

    private SnapLoginPageActions snapLoginPageActions;
    private SnapDashboardPageActions snapDashboardPageActions;
    private SnapPolicyPageActions snapPolicyPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("SnapPolicyDashboardPageTests");
        logger.info("Creating object for SnapLoginPageActions :: beforeClassSetUp");
        snapLoginPageActions = PageObjectManager.getSnapLoginPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "PolicyPageData")
    public void testSearchPolicy(Map<String, String> map) throws InterruptedException {

        logger.info("Executing the test SearchPolicyTest");
        snapDashboardPageActions = snapLoginPageActions.enterLoginCredentials(DriverManager.getDriver(), map.get("userName"), map.get("password"), map.get("answer"));
        snapDashboardPageActions.clickPolicyMenu(DriverManager.getDriver());
        snapPolicyPageActions = snapDashboardPageActions.clickPolicyDashboard(DriverManager.getDriver());
        /* snapPolicyPageActions */
        snapPolicyPageActions.enterPolicyNumberInSearchBox(DriverManager.getDriver());
        snapPolicyPageActions.clickPolicySearchButton(DriverManager.getDriver());

    }
}
