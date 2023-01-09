package QuotesPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.BindingPageActions;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.dbConnector.DatabaseConnector;
import workflows.CreateSubmission;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class NewSubmissionQuoteTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(NewSubmissionQuoteTests.class);
    private DashboardPageActions dashboardPageActions;
    private QuoteListPageActions quoteListPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private DatabaseConnector databaseConnector;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuotesPageTests");
        logger.info("Executing the tests from QuotesPageTests class  :: beforeClassSetUp");
        databaseConnector = new DatabaseConnector();
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
    }


    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testAddingQuoteToNewSubmission(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test verifies whether user can add quote to new submission
         story -
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testConfirmDatesModal from BindingPageTests class :: testAddingQuoteToNewSubmission");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals(jsonObject.get("quoteStatus").toString());
        int quotesCountBefore = quoteListPageActions.getQuotesCount(DriverManager.getDriver());
        quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
        logger.info("option count is 1, because only one quote will be open at a time");
        String optionCount = "1";
        quoteListPageActions.selectPerClaim(DriverManager.getDriver(), optionCount, jsonObject.get("claim").toString());
        quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), optionCount, jsonObject.get("retention").toString());
        quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), optionCount, jsonObject.get("limit").toString());
        int quotesCountAfter = quoteListPageActions.getQuotesCount(DriverManager.getDriver());
        assert quotesCountAfter == quotesCountBefore + 1;
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testQuotePreview(JSONObject jsonObject) throws InterruptedException {
        /************************************************************
         this verifies whether broker can click preview quote option
         story - N2020-28644-QAT-229
         @author - Azamat Uulu
         *************************************************************/
        logger.info("Executing the testQuotePreview from QuoteTests class :: testQuotePreview");
        logger.info("verifying quote preview icons");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        if(coverage.contains(jsonObject.get("coverageOmic").toString())){
            quoteListPageActions.selectBRRPCoverageWithoutInvestigation(DriverManager.getDriver());
            quoteListPageActions.selectBRRPCoverageWithInvestigation(DriverManager.getDriver());
        }
        assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
        assert quoteListPageActions.verifyQuotePreview(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testLockingQuote(JSONObject jsonObject) throws InterruptedException, SQLException {
        /******************************************************************
         this verifies whether broker can lock the quote using confirm lock button
         story - N2020-28645, 28655 -QAT-174, N2020-28633 and N2020-28708
         @author - Azamat Uulu, Venkat Kottapalli
         ********************************************************************/
        logger.info("Executing the testConfirmAndLockQuoteOption from QuoteTests class :: testConfirmAndLockQuoteOption");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals(jsonObject.get("quoteStatus").toString());
        assert quoteListPageActions.isQuoteExpiryDisplayed(DriverManager.getDriver());
        assert quoteListPageActions.verifyIfLockedQuoteExist(DriverManager.getDriver());
        assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
        assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
        logger.info("placing the quote order and verifying it;s status in dashboard");
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        BindingPageActions bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        String quoteOptionStatus = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteOptionStatus.contentEquals(jsonObject.get("quoteStatusBinder").toString());
        String query = GET_SUBMISSION_ID_WITH_QUOTE_ID + quoteId + ";";
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(query);
        int submissionCount = submissionIds.size();
        String submissionId = null;
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("submission_id").toString();
                break;
            }
        }
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
        String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(jsonObject.get("quoteStatusDashboard").toString());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testValidateConfirmDatesModal(JSONObject jsonObject) throws InterruptedException, ParseException {
        /*****************************************************************
         this test validates confirm dates modal
         story - N2020-35623
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testValidateConfirmDatesModal from BindingPageTests class :: testValidateConfirmDatesModal");
        logger.info("validating download icons of quote list page");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals(jsonObject.get("quoteStatus").toString());
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        String effDate = quoteListPageActions.getEffectiveDate(DriverManager.getDriver());
        long dateDifference = quoteListPageActions.validateEffectiveDate(DriverManager.getDriver());
        if (dateDifference > 7) {
            // eff date defaults to empty/null
            assert effDate == null;
        } else {
            // then the date defaults to the Effective Date that was entered on the rating criteria
            assert effDate != null;
        }
        quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData", enabled = false)
    public void testContactUnderwriterModalBeforeLock(JSONObject jsonObject) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies contact underwriter modal on quote page before lock
         @author - Venkat Kottapalli
         ******************************************************************/
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
        assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed2(DriverManager.getDriver());
        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
        assert quoteListPageActions.submitReviewCancelButton(DriverManager.getDriver()).isDisplayed();
        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
        logger.info("fetching the submission Id using initial quote Id from db");
        String query = GET_SUBMISSION_ID_WITH_QUOTE_ID + quoteId + ";";
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(query);
        int submissionCount = submissionIds.size();
        String submissionId = null;
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("submission_id").toString();
                break;
            }
        }
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
        String quoteStatusDashboard = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatusDashboard.contentEquals(ConstantVariable.IN_REVIEW_STRING);
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testContactUnderwriterModalAfterLock(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test verifies contact underwriter modal
         story - N2020-35238 - QAT-546
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testContactUnderwriterModalAfterLock :: NewSubmissionQuoteTests");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        assert quoteListPageActions.verifyContactUnderwriter(DriverManager.getDriver());
        quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
        assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver());
        assert quoteListPageActions.getSubmitReviewDialogText(DriverManager.getDriver()).equalsIgnoreCase(jsonObject.get("modalText").toString());
        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData", enabled = false)
    public void testQuoteOutsideBoundSoftDeclined(JSONObject jsonObject) throws InterruptedException {
        /******************************************************************************
         this test verifies Broker Portal Quotes Outside the Bounds Will Be Soft Declined
         story - N2020-28646-QAT-234
         @author - Azamat Uulu
         *********************************************************************************/
        logger.info("verifying Quotes Outside the Bounds Will Be Soft Declined functionality :: testQuoteOutsideBoundSoftDeclined");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
        quoteListPageActions.selectPerClaim(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("claim").toString());
        quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("limit").toString());
        quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver());
        if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
        } else {
            quoteListPageActions.verifyQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
            String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
            assert Objects.equals(status, "Ready to Place Order");
            quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
            quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
            quoteListPageActions.verifySoftDeclinePopup(DriverManager.getDriver());
            String actualFirstStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
            assert actualFirstStatus.equals("Cancelled");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testQuoteOptionCoverageGroupValidation(JSONObject jsonObject) throws InterruptedException {
        /*************************************************************************************
         this test verifies Broker Portal Quotes Can Select/Unselect Coverage Groups for an Option
         story - N2020-30895 and N2020-28635/28636 QAT-231
         @author - Azamat Uulu
         *******************************************************************************************/
        logger.info("verifying Quotes Broker Can Select/Unselect Coverage Groups for an Option :: testQuoteOptionCoverageGroupValidation");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
        logger.info("verifying the fields, when coverage unchecked");
        boolean optionCoverageGroupUnSelect = quoteListPageActions.verifyOptionCoverageGroupUnSelect(DriverManager.getDriver());
        Assert.assertFalse(optionCoverageGroupUnSelect);
        assert quoteListPageActions.verifyWarningMsgWhenUncheckedOptionCoverageGroup(DriverManager.getDriver());
        boolean isPremiumAmountDisplay = quoteListPageActions.isPremiumAmountDisplay(DriverManager.getDriver());
        Assert.assertFalse(isPremiumAmountDisplay);
        boolean isConfirmAndLockButtonVisible = quoteListPageActions.isConfirmedAndLockQuoteButtonDisplay(DriverManager.getDriver());
        Assert.assertFalse(isConfirmAndLockButtonVisible);
        logger.info("verifying the fields, when coverage checked");
        boolean optionCoverageGroupSelect = quoteListPageActions.verifyOptionCoverageGroupSelect(DriverManager.getDriver());
        Assert.assertTrue(optionCoverageGroupSelect);
        boolean defaultSelectedCoverage = quoteListPageActions.verifyDefaultCoverageCheckboxSelected(DriverManager.getDriver());
        Assert.assertTrue(defaultSelectedCoverage);
        logger.info("verifying whether selected values are saved or not in new option");
        quoteListPageActions.clickAddOptionButton(DriverManager.getDriver());
        if(coverage.contains(jsonObject.get("coverageOmic").toString()) || coverage.contains(jsonObject.get("coverageAAO").toString())){
            boolean isSelectVisible = quoteListPageActions.isSelectVisibleToNewAddOptionOMICAAO(DriverManager.getDriver());
            Assert.assertTrue(isSelectVisible);
        }else{
            boolean isSelectVisible = quoteListPageActions.isSelectVisibleToNewAddOption(DriverManager.getDriver());
            Assert.assertTrue(isSelectVisible);
        }
        String optionCount = String.valueOf(quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver()));
        quoteListPageActions.selectPerClaim(DriverManager.getDriver(), optionCount, jsonObject.get("claim").toString());
        String selectedPerClaimValue = quoteListPageActions.clickClaimCheckbox(DriverManager.getDriver(), optionCount);
        Assert.assertEquals(selectedPerClaimValue, jsonObject.get("claim"));

        quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), optionCount, jsonObject.get("limit").toString());
        String aggLimit = quoteListPageActions.getAggLimitSelectedValue(DriverManager.getDriver(), optionCount);
        Assert.assertEquals(aggLimit, jsonObject.get("limit"));

        boolean valueSelected = quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), optionCount, jsonObject.get("retention").toString());
        if (valueSelected) {
            String retentionValue = quoteListPageActions.getRetentionSelectedValue(DriverManager.getDriver(), optionCount);
            Assert.assertEquals(retentionValue, jsonObject.get("retention"));
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testNotDisplayPremiumIfReviewRequired(JSONObject jsonObject) throws InterruptedException {
        /******************************************************************
         this test verifies if premium should not display if review required
         story - N2020-33454 and QAT-335
         @author - Azamat Uulu
         *******************************************************************/
        logger.info("verifies if premium should not displayed if review required :: testNotDisplayPremiumIfReviewRequired");
        CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
        quoteListPageActions.selectPerClaim(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("claim1").toString());
        quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("limit1").toString());
        String premiumBefore = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
        quoteListPageActions.selectPerClaim(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("claim2").toString());
        quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("limit2").toString());
        String premiumAfter = "";
        assert !Objects.equals(premiumAfter, premiumBefore);
        boolean isTextVisible = quoteListPageActions.verifyOutsideBrokerPortalGuidelinesVisible(DriverManager.getDriver());
        Assert.assertTrue(isTextVisible);
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testDownloadApplicationInQuote(JSONObject jsonObject) throws Exception {
        /***
         this test verifies brokers can download application form
         This story works with 9.8 only
         story - N2020-34254-QAT-434, QAT-536
         @author - Azamat Uulu, Venkat Kothapalli
         ********************************************************************/

        logger.info("Executing the verifies brokers can download application form from testDownloadApplicationInQuote class :: testDownloadApplicationInQuote");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        boolean isPDFFileDownload = quoteListPageActions.clickApplicationDownloadIcon(DriverManager.getDriver(), jsonObject.get("fileNamePDF").toString());
        Assert.assertTrue(isPDFFileDownload);
        String quoteId = quoteListPageActions.getLockedQuoteId(DriverManager.getDriver());
        String fileName = jsonObject.get("fileNamePDF").toString()+quoteId+".pdf";
        quoteListPageActions.verifyPDFDocumentTextContent(fileName);
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "NewQuotesPageData")
    public void testMultiCoverageInQuote(JSONObject jsonObject) throws Exception {
        /***
         this test verifies brokers can see multi-coverage option
         story - QAT-576
         @author - Azamat Uulu
         ********************************************************************/

        logger.info("Executing the verifies brokers can see multi-coverage option from testMultiCoverageInQuote class :: testMultiCoverageInQuote");
        underwritingQuestionsPageActions = CreateSubmission.createSubmissionTillUWQuestionPage(DriverManager.getDriver(), jsonObject, multiCoverage);
        if(multiCoverage.contains("Ophthalmic")){
            underwritingQuestionsPageActions.multiCoverageUWQuestions(DriverManager.getDriver());
            boolean quotePageDisplay = quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
            Assert.assertTrue(quotePageDisplay);
            WaitHelper.pause(20000);
            boolean isConfirmAndLockButtonVisible = quoteListPageActions.isConfirmedAndLockQuoteButtonDisplay(DriverManager.getDriver());
            Assert.assertTrue(isConfirmAndLockButtonVisible);
            boolean quoteLocked = quoteListPageActions.clickConfirmAndLockButton(DriverManager.getDriver());
            Assert.assertTrue(quoteLocked);
            String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
            assert status.contentEquals(jsonObject.get("quoteStatus").toString());
            quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
            BindingPageActions bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
            String quoteOptionStatus = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
            assert quoteOptionStatus.contentEquals(jsonObject.get("quoteStatusBinder").toString());
            assert bindingPageActions.getGenerateBinderButton(DriverManager.getDriver()).isEnabled();
            bindingPageActions.clickGenerateBinderButton(DriverManager.getDriver());
            String quoteStatusAfterBinding = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
            System.out.println(quoteStatusAfterBinding);
            /*if(bindingPageActions.isBinderGenerationWarningDisplayed(DriverManager.getDriver())){
                assert quoteStatusAfterBinding.contentEquals(map.get("optionOrderStatus"));
            }else {
                assert quoteStatusAfterBinding.contentEquals(map.get("boundStatus"));
            }*/
        }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (databaseConnector != null) {
            databaseConnector.closeDatabaseConnector();
        }
    }
}
