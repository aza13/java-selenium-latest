import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;
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
import static constants.DatabaseQueries.UPDATE_SUBJECTIVITY_STATUS;

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

    @BeforeMethod
    public void beforeMethod() {

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testVerifyQuoteBinding(Map<String, String> map) throws InterruptedException, SQLException {
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
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
        logger.info(("validating the Generate Binder button - 35242, QAT-550"));
        logger.info("the generate binding button displayed when there no pre-binding subjectivities" +
                " or all pre-binding subjectivities status is either Accepted or Waived");
        boolean priorSubj = bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
        if(!priorSubj){
            logger.info("if prior subjectivity not displayed");
            assert bindingPageActions.getGenerateBinderButton(DriverManager.getDriver()).isDisplayed();
            assert bindingPageActions.getGenerateBinderButton(DriverManager.getDriver()).isEnabled();
        }else{
            // need to revisit this part
            String priorSubjStatus = bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver());
            assert Objects.equals(priorSubjStatus, "Open");
            logger.info("generate binder button should not be displayed");
            assert !bindingPageActions.isGenerateBinderDisplayed(DriverManager.getDriver());
        }
        String quoteStatus = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(map.get("quoteStatus"));
        // here either no pre-binding subjectivities or their status should be Accepted or Waived
        //to-do status should be verified
        //if status of the subjectivity changed from Accepted or Waived Generate Binder will not be displayed

        //bindingPageActions = quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
        logger.info("validating subjectivities on binder page");
        assert bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
        assert bindingPageActions.isPostSubjectivitiesDisplayed(DriverManager.getDriver());
        assert bindingPageActions.isMessageToUnderWriterDisplayed(DriverManager.getDriver());
        logger.info("fetching the submission using quote from db");
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
        bindingPageActions.clickPolicyCardExpandIconInBindingPage(DriverManager.getDriver());
        logger.info("enabling the binder submit button if disabled");
        if (!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()) {
            bindingPageActions.enterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
            bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
            bindingPageActions.enterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
            bindingPageActions.clickSubmitBinder(DriverManager.getDriver());
        }
        logger.info("validating the status of the submission");
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
        String quoteStatusDashboard = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatusDashboard.contentEquals("Order Placed");
        String subjectivityStatusQuery = UPDATE_SUBJECTIVITY_STATUS+quoteId+";";
        int queryResult = databaseConnector.update(subjectivityStatusQuery);
        if(queryResult == 1){
            WaitHelper.pause(30000);
            dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
            assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
            bindingPageActions.verifyQuoteHeaderInformationInBindingPage(DriverManager.getDriver(), newInsuredName, product);
        }else{
            System.out.print("query not executed successfully");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testGenerateBinderButtonValidations(Map<String, String> map) throws InterruptedException {
        /*****************************************************************
         this test validates the generate button presence in different conditions'
         story - QAT-550
         @author - Venkat Kottapalli
         ******************************************************************/

        logger.info("Executing the testGenerateBinderButtonValidations from BindingPageTests class :: testGenerateBinderButtonValidations");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
        logger.info(("validating the Generate Binder button - 35242, QAT-550"));
        logger.info("the generate binding button displayed when there no pre-binding subjectivities" +
                " or all pre-binding subjectivities status is either Accepted or Waived");
        boolean priorSubj = bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
        if (priorSubj) {
            logger.info("if prior subjectivity is displayed");
            String priorSubjStatus = bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver());
            if(Objects.equals(priorSubjStatus, "Accepted") || Objects.equals(priorSubjStatus, "Waived")){
                assert bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
            }else if(Objects.equals(priorSubjStatus, "Open")){
                assert !bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
            }
        } else {
            assert bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testFileUploadValidationsInBinder(Map<String, String> map) throws InterruptedException, AWTException {
        /*****************************************************************
         this test verifies maximum file size and file type that can be uploaded to a Binder
         story - N2020-33918, N2020-34632
         @author - Venkat Kottapalli
         ******************************************************************/

        logger.info("Executing the testFileUploadValidationsInBinder from BindingPageTests class :: testFileUploadValidationsInBinder");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals("Ready to Place Order");
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());

        String quoteStatus = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(map.get("quoteStatus"));

        //quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
        logger.info("validating the status of the prior subj status");
                    /*if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.OPEN_STATUS_STRING)){
                        assert !bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }else if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.ACCEPTED_STATUS_STRING)){
                        assert bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }else if(Objects.equals(bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver()), ConstantVariable.WAIVED_STATUS_STRING)){
                        assert bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
                    }*/
        bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
        if(map.get("fileType").contentEquals("fileTypeValidation")){
            logger.info("validating the invalid file type warning & valid file upload functionality");
            bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.INVALID_FILE_TYPE);
            assert bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
            bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
            bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
            assert bindingPageActions.getFileDeleteIcon(DriverManager.getDriver()).isDisplayed();
            assert bindingPageActions.getFilePresentIcon(DriverManager.getDriver()).isDisplayed();
            bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
        }else if(map.get("fileType").contentEquals("fileSizeValidation")){
            bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
            logger.info("validating the maximum file upload size warning");
            assert bindingPageActions.isFileMaximumSizeTextDisplayed(DriverManager.getDriver());
            for (int n = 1; n <= 12; n++) {
                bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.WORD_DOC_FILE_PATH);
            }
            bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
            assert bindingPageActions.isFileTypeWarningDisplayed(DriverManager.getDriver());
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
