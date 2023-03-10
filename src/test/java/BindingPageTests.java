import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.BindingPageActions;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.dbConnector.DatabaseConnector;
import utils.fileDownload.FileDownloadUtil;
import workflows.CreateSubmission;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static constants.DatabaseQueries.*;

public class BindingPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;
    private BindingPageActions bindingPageActions;


    private BindingPageTests() {
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("BindingPageTests");
        logger.info("Creating object for BindingPageTests :: beforeClassSetUp");
        databaseConnector = new DatabaseConnector();
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testVerifyQuoteBinding(JSONObject jsonObject) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies quote option Binding and Subjectivity
         story - N2020-33007, 23922,32926, 32930, 32950, 32704
         @author - Venkat Kottapalli, Sheetal
         ******************************************************************/
        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
        String quoteStatus = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals(jsonObject.get("quoteStatus").toString());

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
        logger.info("fetching new quote Id using the submission Id from db");
        String newQuoteIdQuery = GET_QUOTE_ID_WITH_SUBMISSION_ID + submissionId + "' ORDER BY id DESC LIMIT 1;";
        List<HashMap<Object, Object>> newQuoteIds =
                databaseConnector.getResultSetToList(newQuoteIdQuery);
        int quoteIdsCount = newQuoteIds.size();
        String newQuoteId = null;
        if (quoteIdsCount > 0) {
            for (HashMap<Object, Object> id : newQuoteIds) {
                newQuoteId = id.get("id").toString();
                break;
            }
        }
        boolean generateBinder = bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
        if (!generateBinder) {
            logger.info("generate binder button not displayed, trying to change the subj status");
            String subjectivityStatusQuery = UPDATE_SUBJECTIVITY_STATUS + newQuoteId + ";";
            int queryResult = databaseConnector.update(subjectivityStatusQuery);
            if (queryResult == 1) {
                WaitHelper.pause(15000);
                dashboardPageActions = bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                String quoteStatusDashboard = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
                assert quoteStatusDashboard.contentEquals("Order Placed");
                dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
                assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
//                bindingPageActions.verifyQuoteHeaderInformationInBindingPage(DriverManager.getDriver(), newInsuredName, product);
            } else {
                logger.error("query not executed successfully");
                assert false;
            }
        }
        logger.info("Remove Binder will be issued shortly verbiage - QAT 720");
        assert !bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
        assert bindingPageActions.getGenerateBinderButton(DriverManager.getDriver()).isEnabled();
        bindingPageActions.clickGenerateBinderButton(DriverManager.getDriver());
        String quoteStatusAfterBinding = bindingPageActions.getQuoteStatus(DriverManager.getDriver());
        if(bindingPageActions.isBinderGenerationWarningDisplayed(DriverManager.getDriver())){
            assert quoteStatusAfterBinding.contentEquals(jsonObject.get("optionOrderStatus").toString());
        }else {
            assert quoteStatusAfterBinding.contentEquals(jsonObject.get("boundStatus").toString());
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testValidateSubjectivitiesAndQuoteStatus(JSONObject jsonObject) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies subjectivities in Binding page & Quote Status in the Dashboard page
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testValidateSubjectivitiesAndQuoteStatus from BindingPageTests class :: testValidateSubjectivitiesAndQuoteStatus");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
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
        logger.info("validating subjectivities on binder page");
        assert bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
        assert bindingPageActions.isPostSubjectivitiesDisplayed(DriverManager.getDriver());
        assert bindingPageActions.isMessageToUnderWriterDisplayed(DriverManager.getDriver());

        bindingPageActions.clickPolicyCardExpandIconInBindingPage(DriverManager.getDriver());
        logger.info("enabling the binder submit button if disabled");
        if (!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()) {
            bindingPageActions.enterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
            bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
            bindingPageActions.enterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
            bindingPageActions.clickSubmitBinder(DriverManager.getDriver());
        }
        logger.info("validating the status of the submission in dashboard");
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
        String quoteStatusDashboard = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatusDashboard.contentEquals("Order Placed");
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testGenerateBinderButtonValidations(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test validates the generate button presence in different conditions'
         story - QAT-550
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testGenerateBinderButtonValidations from BindingPageTests class :: testGenerateBinderButtonValidations");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
        logger.info(("validating the Generate Binder button - 35242, QAT-550"));
        logger.info("the generate binder button will be displayed when there no pre-binding subjectivities" +
                " or all pre-binding subjectivities status is either Accepted or Waived");
        boolean priorSubj = bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
        if (priorSubj) {
            logger.info("if prior subjectivity is displayed");
            String priorSubjStatus = bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver());
            if (Objects.equals(priorSubjStatus, "Accepted") || Objects.equals(priorSubjStatus, "Waived")) {
                assert bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
            } else assert !Objects.equals(priorSubjStatus, "Open") || !bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
        } else {
            assert bindingPageActions.isGenerateBinderButtonExist(DriverManager.getDriver());
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testFileUploadValidationsInBinder(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test verifies maximum file size and file type that can be uploaded to a Binder
         story - N2020-33918, N2020-34632
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testFileUploadValidationsInBinder from BindingPageTests class :: testFileUploadValidationsInBinder");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals("Ready to Place Order");
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());

        logger.info("validating the status of the prior subj status");
        String priorSubjStatus = bindingPageActions.getPriorSubjectivityStatus(DriverManager.getDriver());
        if (Objects.equals(priorSubjStatus, ConstantVariable.OPEN_STATUS_STRING)) {
            assert !bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
        } else assert !Objects.equals(priorSubjStatus, ConstantVariable.ACCEPTED_STATUS_STRING) && !Objects.equals(priorSubjStatus, ConstantVariable.WAIVED_STATUS_STRING) || bindingPageActions.isBinderIssuedShortlyText(DriverManager.getDriver());
        bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
        if (jsonObject.get("fileType").toString().contentEquals("fileTypeValidation")) {
            logger.info("validating the invalid file type warning & valid file upload functionality");
            bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.INVALID_FILE_TYPE);
            assert bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
            assert bindingPageActions.isFileDeleteIconDisplayed(DriverManager.getDriver());
            bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
            assert !bindingPageActions.isFileDeleteIconDisplayed(DriverManager.getDriver());
            bindingPageActions.clickSelectFilesCancelButton(DriverManager.getDriver());
            bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
            bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
            logger.info("checking file attachment on select files modal");
            assert bindingPageActions.isFilePresentIconDisplayed(DriverManager.getDriver());
            bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
            logger.info("checking file attachment on binder page");
            assert bindingPageActions.isFilePresentIconDisplayed(DriverManager.getDriver());
        } else if (jsonObject.get("fileType").toString().contentEquals("fileSizeValidation")) {
            logger.info("validating the maximum file upload size warning");
            assert bindingPageActions.isFileMaximumSizeTextDisplayed(DriverManager.getDriver());
            for (int n = 1; n <= 12; n++) {
                bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.WORD_DOC_FILE_PATH);
            }
            bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
            assert bindingPageActions.isFileTypeWarningDisplayed(DriverManager.getDriver());
        }
    }


    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData", enabled = false)
    public void testSubjectivityStatus(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test verifies Subjectivity status is Rejected, Accepted and Waived
         story - N2020-32716, 32708, 33154
         @author -  Sheetal
         ******************************************************************/
        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testRejectSubjectivity");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), jsonObject.get("submissionName1").toString());
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        bindingPageActions = PageObjectManager.getBindingPageActions();
        assert bindingPageActions.verifyWaivedStatus(DriverManager.getDriver());
        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
        assert bindingPageActions.verifyRejectedStatus(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), jsonObject.get("submissionName2").toString());
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        assert bindingPageActions.verifyPreBinderText(DriverManager.getDriver());
        assert bindingPageActions.verifyAcceptedStatus(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), jsonObject.get("submissionName3").toString());
        String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.equals("Bound");
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        assert bindingPageActions.verifyBinderText(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testDownloadBinder(JSONObject jsonObject) throws InterruptedException, SQLException {
        /*******
         this test verifies brokers can download Binder and Invoices
         story - N2020-32942, 35714, QAT-463,QAT-548
         @author - Azamat Uulu
         ****************/
        logger.info("verifying brokers can download binder document invoices :: testDownloadBinder");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_SUBMISSIONS_WITH_BINDER_DOCUMENT);
        int submissionCount = submissionIds.size();
        boolean bindingPage = false;
        String submission_id = "";
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submission_id = id.get("id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submission_id);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    bindingPage = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (bindingPage) {
                BindingPageActions bindingPageActions = PageObjectManager.getBindingPageActions();
                assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
                String quoteIDs = String.format(DatabaseQueries.GET_QUOTE_ID, submission_id);
                List<HashMap<Object,Object>> quoteID = databaseConnector.getResultSetToList( quoteIDs);

                String brokerFileName = jsonObject.get("brokerFilename").toString()+quoteID.get(0).get("id")+".docx";
                bindingPageActions.clickBrokerInvoiceDownload(DriverManager.getDriver());
                boolean brokerInvoiceDownloaded = bindingPageActions.verifyIfBrokerInvoiceDownloaded(brokerFileName);
                Assert.assertTrue(brokerInvoiceDownloaded);
                String clientFileName = jsonObject.get("clientFilename").toString()+quoteID.get(0).get("id")+".docx";
                bindingPageActions.clickClientInvoiceDownload(DriverManager.getDriver());
                boolean clientInvoiceDownload = bindingPageActions.verifyIfClientInvoiceDownloaded(clientFileName);
                Assert.assertTrue(clientInvoiceDownload);
//                boolean pdfDownload = bindingPageActions.clickBinderDownload(DriverManager.getDriver(), jsonObject.get("pdfFilename").toString());
//                Assert.assertTrue(pdfDownload);
            } else {
                logger.info("No binder available, to download the binder ");
            }
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testValidateConfirmDatesPlaceOrderFunctionality(JSONObject jsonObject) throws InterruptedException {
        /*****************************************************************
         this test verifies the functionality of Confirm Dates and Place Order Button
         story - N2020-35641, QAT-645
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testValidateConfirmDatesPlaceOrderFunctionality :: BindingPageTests");
        quoteListPageActions = CreateSubmission.createSubmissionTillQuotePage(DriverManager.getDriver(), jsonObject, coverage);
        logger.info("validating download icons of quote list page");
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        String effDate = quoteListPageActions.getConfirmDatesEffectiveDate(DriverManager.getDriver()).getAttribute("value");
        String expDate = quoteListPageActions.getConfirmDatesExpirationDate(DriverManager.getDriver()).getAttribute("value");
        bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
        assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
        String policyPeriod = bindingPageActions.getProposedPolicyPeriod(DriverManager.getDriver());
        assert policyPeriod.contains(effDate);
        assert policyPeriod.contains(expDate);
    }


}
