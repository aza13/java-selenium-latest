import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.dataProvider.TestDataProvider;
import utils.fileReader.ConfigDataReader;

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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "InsuredPageData")
    public void testCreateInsuredFieldsValidation(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies creation of new insured fields validation
         story - N2020-28293
         @author - Venkat Kottapalli
         **/
        logger.info("verifying creating new quote creation :: testCreateInsuredFieldsValidation");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("coverage"), jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.clickNewInsuredButton(DriverManager.getDriver());
        assert !insuredPageActions.isQuotePageDisabled(DriverManager.getDriver());
        String insuredName = insuredPageActions.getInsuredName(DriverManager.getDriver());
        assert insuredName.contentEquals(jsonObject.get("applicantName").toString());
        String insuredWebsite = insuredPageActions.getInsuredWebsite(DriverManager.getDriver());
        assert insuredWebsite.contentEquals(jsonObject.get("website").toString());
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());
        assert insuredPageActions.emailReqText(DriverManager.getDriver()).isDisplayed();
        assert insuredPageActions.validatePhysicalAddressFields(DriverManager.getDriver());
        assert insuredPageActions.validateMailingAddressFields(DriverManager.getDriver());
        insuredPageActions.cancelInsuredFormButton(DriverManager.getDriver());
        assert dashboardPageActions.clickQuotesTab(DriverManager.getDriver()).isDisplayed();
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "InsuredPageData")
    public void testCreateInsured(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies creation of new insured
         story - N2020-28293, N2020-30893-QAT-172
         @author - Azamat Uulu
         **/
        logger.info("verifying creating new quote creation :: testCreateInsured");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, newInsuredName,newInsuredWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        if (!insuredPageActions.isCreateNewInsuredTextDisplayed(DriverManager.getDriver())){
            insuredPageActions.clickNewInsuredButton(DriverManager.getDriver());
        }
        insuredPageActions.enterEmailAddress(DriverManager.getDriver());
        insuredPageActions.enterInsuredPhoneNumber(DriverManager.getDriver());
        assert insuredPageActions.verifyValidPhoneNumberFormat(DriverManager.getDriver());
        insuredPageActions.enterPhysicalAddress(DriverManager.getDriver());
        insuredPageActions.enterPhyCity(DriverManager.getDriver());
        insuredPageActions.enterPhyZipcode(DriverManager.getDriver());
        assert insuredPageActions.verifyValidZipCode(DriverManager.getDriver());
        insuredPageActions.selectPhyState(DriverManager.getDriver());
        insuredPageActions.clickSameAsPhyAddress(DriverManager.getDriver());
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "InsuredPageData")
    public void testSearchAgainFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies search again functionality
         story - N2020-29653
         @author - Venkat Kottapalli
         **/
        logger.info("verifying modify search of insured :: testSearchAgainFunctionality");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        assert insuredPageActions.validateSearchAgainButtonWithInsuredName(DriverManager.getDriver(), jsonObject.get("secondApplicant").toString());
        insuredPageActions.enterApplicantWebsite(DriverManager.getDriver(), jsonObject.get("secondWebsite").toString());
        insuredPageActions.clickSearchAgainButton(DriverManager.getDriver());
        assert insuredPageActions.verifyInsuredSearchResult(DriverManager.getDriver(), jsonObject.get("secondWebsite").toString());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "InsuredPageData")
    public void testCheckDuplicateSubmission(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies creation of new insured
         story - N2020-29053
         @author - Venkat Kottapalli
         **/
        logger.info("verifying duplicate submissions :: testCheckDuplicateSubmission");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.selectInsuredCard(DriverManager.getDriver(), jsonObject.get("applicantName").toString());
        if(insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())){
            insuredPageActions.enterClearanceText(DriverManager.getDriver(), "Test");
            insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
        }else{
            assert insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver());
            String actualText = insuredPageActions.duplicateSubmissionDialogDescription(DriverManager.getDriver());
            assert actualText.contains(jsonObject.get("dialogText").toString());
            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
            dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "InsuredPageData")
    public void testClickingLogoNavigatesToDashboardPage(JSONObject jsonObject) throws InterruptedException {
        /**
         * this test verifies search results for related Records
         story - N2020-32169
         @author - Sheetal
         **/
        logger.info("verifying Clicking QuoteIt Logo to Return to Dashboard :: testClickingLogoNavigatesToDashboardPage");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, jsonObject.get("applicantName").toString(), jsonObject.get("website").toString());
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        dashboardPageActions.clickQuoteIt(DriverManager.getDriver());
        assert dashboardPageActions.clickQuotesTab(DriverManager.getDriver()).isDisplayed();

    }
}
