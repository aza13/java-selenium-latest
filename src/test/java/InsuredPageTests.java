import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import utils.dataProvider.TestDataProvider;

import java.io.IOException;
import java.util.Map;

public class InsuredPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(InsuredPageTests.class);
    private DashboardPageActions dashboardPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("InsuredPageTests");
        logger.info("Creating object for LoginPageTest :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testCreateInsuredFieldsValidation(Map<String, String> map) throws InterruptedException {
        /***
          this test verifies creation of new insured fields validation
         story - N2020-28293
         **/
        logger.info("verifying creating new quote creation :: testCreateInsuredFieldsValidation");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.clickNewInsuredButton(DriverManager.getDriver());
        String insuredName = insuredPageActions.getInsuredName(DriverManager.getDriver());
        assert insuredName.contentEquals(map.get("applicantName"));
        String insuredWebsite = insuredPageActions.getInsuredWebsite(DriverManager.getDriver());
        assert insuredWebsite.contentEquals(map.get("website"));
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());
        assert insuredPageActions.emailReqText(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.validatePhysicalAddressFields(DriverManager.getDriver());
        assert insuredPageActions.validateMailingAddressFields(DriverManager.getDriver());
        insuredPageActions.cancelInsuredFormButton(DriverManager.getDriver());
        assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testCreateInsured(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies creation of new insured
         story - N2020-28293
         **/
        logger.info("verifying creating new quote creation :: testCreateInsured");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.clickNewInsuredButton(DriverManager.getDriver());
        insuredPageActions.enterEmailAddress(DriverManager.getDriver());
        insuredPageActions.enterPhysicalAddress(DriverManager.getDriver());
        insuredPageActions.enterPhyCity(DriverManager.getDriver());
        insuredPageActions.enterPhyZipcode(DriverManager.getDriver());
        insuredPageActions.selectPhyState(DriverManager.getDriver());
        insuredPageActions.clickSameAsPhyAddress(DriverManager.getDriver());
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testSearchAgainFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies search again functionality
         story - N2020-29653
         **/
        logger.info("verifying modify search of insured :: testModifyInsuredSearch");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.validateSearchAgainButtonWithInsuredName(DriverManager.getDriver(), map.get("secondApplicant"));
        assert insuredPageActions.validateSearchAgainButtonWithInsuredWebsite(DriverManager.getDriver(), map.get("secondWebsite"));
        insuredPageActions.clickSearchAgainButton(DriverManager.getDriver());
        assert insuredPageActions.verifyInsuredSearchResult(DriverManager.getDriver(), map.get("secondApplicant"), map.get("secondWebsite"));
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testCheckDuplicateSubmission(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies creation of new insured
         story - N2020-29053
         **/
        logger.info("verifying duplicate submissions :: testCheckDuplicateSubmission");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.selectInsuredCard(DriverManager.getDriver(), map.get("applicantName"));
        assert insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver());
        String actualText = insuredPageActions.duplicateSubmissionDialogDescription(DriverManager.getDriver());
        assert actualText.contains(map.get("dialogText"));
        insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
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
