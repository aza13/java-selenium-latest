import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;
import utils.fileReader.ConfigDataReader;
import workflows.AnswerUnderwriterQuestions;
import workflows.CreateApplicant;
import workflows.FillApplicantDetails;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class BindingPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;
    private BindingPageActions bindingPageActions;


    private BindingPageTests() {
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("BindingPageTests");
        logger.info("Creating object for BindingPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        databaseConnector = new DatabaseConnector();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        bindingPageActions = PageObjectManager.getBindingPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testVerifyQuoteBinding(Map<String, String> map) throws InterruptedException, SQLException, AWTException {
        /*****************************************************************
         this test verifies quote option Binding and Subjectivity
         story - N2020-33007, 23922,32926, 32930, 32950, 32704
         @author - Venkat Kottapalli, Sheetal
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        String newInsuredName = CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
            if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                } else {
                    quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    quoteListPageActions.verifyStatusConfirmAndLockReadyToPlaceOrder(DriverManager.getDriver());
                    assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
                    assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
                    quoteListPageActions.clickPlaceOrderButton(DriverManager.getDriver());
                    quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
                    assert bindingPageActions.isPreSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isPostSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isMessageToUnderWriterDisplayed(DriverManager.getDriver());
                    bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
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
                    String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
                    assert quoteStatus.contentEquals("Order Placed");
                    dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
                    assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
                    bindingPageActions.verifyQuoteHeaderInformationInBindingPage(DriverManager.getDriver(), newInsuredName, product);
                    bindingPageActions.clickPolicyCardExpandIconInBindingPage(DriverManager.getDriver());
                    if(!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()){
                        bindingPageActions.EnterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
                        bindingPageActions.EnterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickSubmitBinder(DriverManager.getDriver());
                    }
                    bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
                    bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.INVALID_FILE_TYPE);
                    assert bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
                    bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
                    bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
                    assert bindingPageActions.getFileDeleteIcon(DriverManager.getDriver()).isDisplayed();
                    assert bindingPageActions.getFilePresentIcon(DriverManager.getDriver()).isDisplayed();
                    bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
                    bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
                    bindingPageActions.clickConfirmationContinueButton(DriverManager.getDriver());
                    assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                }
            } else {
                Assert.fail("Confirm and quote button is disabled for some reason");
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testFileSizeValidationForBinder(Map<String, String> map) throws InterruptedException, AWTException {
        /*****************************************************************
         this test verifies maximum file size that can be uploaded to a Binder
         story - N2020-33918, N2020-34632
         @author - Venkat Kottapalli
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                } else {
                    quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    quoteListPageActions.verifyStatusConfirmAndLockReadyToPlaceOrder(DriverManager.getDriver());
                    quoteListPageActions.clickPlaceOrderButton(DriverManager.getDriver());
                    quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
                    if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.OPEN_STATUS_STRING)){
                        assert !bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }else if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.ACCEPTED_STATUS_STRING)){
                        assert bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }else if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.WAIVED_STATUS_STRING)){
                        assert bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }
                    assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
                    if (!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()) {
                        bindingPageActions.EnterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
                        bindingPageActions.EnterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickSubmitBinder(DriverManager.getDriver());
                    }
                    bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
                    assert bindingPageActions.isFileMaximumSizeTextDisplayed(DriverManager.getDriver());
                    for(int n=1; n<=12; n++) {
                        bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.WORD_DOC_FILE_PATH);
                    }
                    bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
                    assert bindingPageActions.isFileTypeWarningDisplayed(DriverManager.getDriver());
                }
            }
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testSubjectivityStatus(Map<String, String> map) throws InterruptedException {
        /*****************************************************************
         this test verifies Subjectivity status is Rejected, Accepted and Waived
         story - N2020-32716, 32708, 33154
         @author -  Sheetal
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testRejectSubjectivity");
        //dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("submissionName1"));
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        assert bindingPageActions.verifyWaivedStatus(DriverManager.getDriver());
        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
        assert bindingPageActions.verifyRejectedStatus(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("submissionName2"));
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        assert bindingPageActions.verifyPreBinderText(DriverManager.getDriver());
        assert bindingPageActions.verifyAcceptedStatus(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("submissionName3"));
        String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.equals("Bound");
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        assert bindingPageActions.verifyBinderText(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());

    }
    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testDownloadBinder(Map<String, String> map) throws InterruptedException, SQLException {
        /***
         this test verifies brokers can download Binder
         story - N2020-32942 -QAT-463
         @author - Azamat Uulu
         **/
        logger.info("verifying brokers can download binder document :: testDownloadBinder");
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_SUBMISSIONS_WITH_BINDER_DOCUMENT);
        int submissionCount = submissionIds.size();
        boolean bindingPage = false;
        String submissionId;
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    bindingPage = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (bindingPage) {
                assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
                boolean pdfDownload = bindingPageActions.clickBinderDownload(DriverManager.getDriver(), map.get("pdfFilename"));
                Assert.assertTrue(pdfDownload);

            } else {
                logger.info("No binder available, to download the binder ");
            }

        }
    }
}
