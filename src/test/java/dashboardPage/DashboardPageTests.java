package dashboardPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import utils.dataProvider.TestDataProvider;

import java.util.Map;

public class DashboardPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageTests.class);
    private DashboardPageActions dashboardPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("DashboardPageTests");
        logger.info("Creating object for DashboardPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testDashboardUI(Map<String, String> map) throws InterruptedException {
        /**
         * this test verifies UI of dashboard
            story - N2020-28285, N2020-28286
         **/
        logger.info("verifying the broker portal dashboard page :: testDashboardUI");
        assert dashboardPageActions.tmhccLogo(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.profileSettingsIcon(DriverManager.getDriver()).isDisplayed();
        String title = dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver());
        assert title.contentEquals("MY QUOTES");
        String titlePolicies = dashboardPageActions.getMyPoliciesTabTitle(DriverManager.getDriver());
        assert titlePolicies.contentEquals("MY POLICIES");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        assert dashboardPageActions.profileLink(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.signOutLink(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), "6854");
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), "3179");
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.verifySubmissionListLabels(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testNewQuoteFieldsValidation(Map<String, String> map) throws InterruptedException {
        /**
         story - N2020-28289, test cases -
         **/
        logger.info("validating the fields on New Quote modal dialog :: testNewQuoteFieldsValidation");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        logger.info("validating whether mandatory field text displayed or not");
        assert dashboardPageActions.productRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.nameRequiredElement(DriverManager.getDriver()).isDisplayed();
        assert dashboardPageActions.websiteRequiredElement(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), "Product one", "applicant 1", "https://www.google.com");
        dashboardPageActions.clickCancelButton(DriverManager.getDriver());
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        logger.info("validating whether the data entered is erased or not");
        String product = dashboardPageActions.productDropdown(DriverManager.getDriver()).getText();
        assert product.equals("Select Product");
        String name = dashboardPageActions.getApplicantName(DriverManager.getDriver());
        assert name.equals("");
        String website = dashboardPageActions.getWebsite(DriverManager.getDriver());
        assert website.equals("");
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "DashboardPageData")
    public void testCreateNewQuote(Map<String, String> map) throws InterruptedException {
        /**
         story - N2020-28289, test cases - TC2
         **/
        logger.info("verifying the broker portal dashboard page :: testCreateNewQuote");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), "Product one", "applicant 1", "https://www.google.com");
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());

    }


}
