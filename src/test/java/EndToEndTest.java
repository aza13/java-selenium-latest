import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.JsonDataProvider;
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

public class EndToEndTest extends BaseTest {

    private static final Logger logger = Logger.getLogger(EndToEndTest.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;
    private BindingPageActions bindingPageActions;


    private EndToEndTest() {
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("EndToEndTest");
        logger.info("Creating object for EndToEndTest :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        databaseConnector = new DatabaseConnector();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        bindingPageActions = PageObjectManager.getBindingPageActions();
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "BindingPageData")
    public void testEndToEndWorkflow(JSONObject jsonObject) throws InterruptedException, SQLException, AWTException {
        /*****************************************************************
         this test verifies quote option Binding and Subjectivity
         story - N2020-33007, 23922,32926, 32930, 32950, 32704
         @author - Venkat Kottapalli, Sheetal
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        String newInsuredName = CreateApplicant.createApplicant(DriverManager.getDriver(), coverage);
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), jsonObject, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), jsonObject, coverage);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            // add quote option and delete it
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
            if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                } else {
                    quoteListPageActions.verifyQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
                    assert Objects.equals(status, "Ready to Place Order");
                    assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
                    assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
                    quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
                    quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
                    assert bindingPageActions.isPriorSubjectivitiesDisplayed(DriverManager.getDriver());
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
                    bindingPageActions.verifyQuoteHeaderInformationInBindingPage(DriverManager.getDriver(), newInsuredName, coverage);
                    bindingPageActions.clickPolicyCardExpandIconInBindingPage(DriverManager.getDriver());
                    if(!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()){
                        bindingPageActions.enterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
                        bindingPageActions.enterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver());
                        bindingPageActions.clickSubmitBinder(DriverManager.getDriver());
                    }
                    bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
                    bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.INVALID_FILE_TYPE);
                    assert bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
                    bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
                    bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
                    assert bindingPageActions.getFileDeleteIcon(DriverManager.getDriver()).isDisplayed();
                    assert bindingPageActions.isFilePresentIconDisplayed(DriverManager.getDriver());
                    bindingPageActions.clickAddFilesButton(DriverManager.getDriver());
                    bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
                    bindingPageActions.clickConfirmationContinueButton(DriverManager.getDriver());
                    assert dashboardPageActions.clickQuotesTab(DriverManager.getDriver()).isDisplayed();
                }
            } else {
                Assert.fail("Confirm and quote button is disabled for some reason");
            }
        }
    }

}
