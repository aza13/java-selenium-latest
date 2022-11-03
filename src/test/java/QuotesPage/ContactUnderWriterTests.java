package QuotesPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;
import workflows.AnswerUnderwriterQuestions;
import workflows.CreateApplicant;
import workflows.FillApplicantDetails;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class ContactUnderWriterTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(ContactUnderWriterTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuotesPageTests");
        logger.info("Executing the tests from QuotesPageTests class  :: beforeClassSetUp");
        databaseConnector = new DatabaseConnector();
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        GsonBuilder gsonMapBuilder = new GsonBuilder();
        Gson gsonObject = gsonMapBuilder.create();
    }

    @BeforeMethod
    public void preTestSetup(Object[] data) throws InterruptedException {
        HashMap<String, String> map = (HashMap<String, String>) data[0];
        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testContactUnderwriterModalBeforeLock(Map<String, String> map) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies contact underwriter modal
         story - N2020-33999
         @author - Venkat Kottapalli
         ******************************************************************/
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
            quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
            assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed2(DriverManager.getDriver());
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            assert quoteListPageActions.submitReviewCancelButton(DriverManager.getDriver()).isDisplayed();
            quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
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
            assert quoteStatus.contentEquals("In Review");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testContactUnderwriterModalAfterLock(Map<String, String> map) throws InterruptedException {
        /*****************************************************************
         this test verifies contact underwriter modal
         story - N2020-35238 - QAT-546
         @author - Venkat Kottapalli
         ******************************************************************/
        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
        assert quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
        assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver());
        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (databaseConnector != null) {
            databaseConnector.closeDatabaseConnector();
        }
    }
}
