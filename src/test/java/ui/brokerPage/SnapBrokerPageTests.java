package ui.brokerPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import pageActions.SnapBrokerPageActions;
import pageActions.SnapDashboardPageActions;
import pageActions.SnapLoginPageActions;
import utils.dataProvider.TestDataProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class SnapBrokerPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapBrokerPageTests.class);

    private SnapLoginPageActions snapLoginPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("SnapLoginPageTests");
        logger.info("Creating object for SnapLoginPageActions :: beforeClassSetUp");
        snapLoginPageActions = PageObjectManager.getSnapLoginPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BrokerPageData")
    public void testCreateBroker(Map<String, String> map) throws InterruptedException {
        logger.info("Executing the test :: testCreateBroker");
        SnapDashboardPageActions snapDashboardPageActions;
        snapDashboardPageActions = snapLoginPageActions.enterLoginCredentials(DriverManager.getDriver(), map.get("userName"), map.get("password"), map.get("answer"));
        snapDashboardPageActions.clickOnBrokerMenu(DriverManager.getDriver());
        SnapBrokerPageActions snapBrokerPageActions;
        snapBrokerPageActions = snapDashboardPageActions.clickOnCreateBroker(DriverManager.getDriver());
        snapBrokerPageActions.fillBrokerContactInfo(DriverManager.getDriver());
    }
}
