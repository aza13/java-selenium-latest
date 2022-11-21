import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

import java.util.Map;

public class RatingCriteriaPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(RatingCriteriaPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;


    private DatabaseConnector databaseConnector;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;


    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("RatingCriteriaPageTests");
        logger.info("Creating object for RatingCriteriaPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        databaseConnector = new DatabaseConnector();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testBusinessClassRatingCriteria(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers Business Class criteria
         story - N2020-30438
         @author - Azamat Uulu
         **/

        logger.info("verifying :: business class rating criteria");
        CreateApplicant.createApplicant(DriverManager.getDriver(),"");
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, "");
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        underwritingQuestionsPageActions.clickExitQuestion(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData", enabled = false)
    public void  testHardDeclineAfterRatingCriteria(Map<String, String> map) throws InterruptedException {
        /***
         this test hard decline after rating criteria
         story - N2020-28624 QAT-171
         @author - Azamat Uulu
         Updated By - Sheetal
         **/

        logger.info("verifying :: test hard decline after rating criteria");
        CreateApplicant.createApplicant(DriverManager.getDriver(),"");
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, "");
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        ratingCriteriaPageActions.verifyAndClickHardDeclinePopup(DriverManager.getDriver());
        String actualFirstStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
        assert actualFirstStatus.equals("Declined");
    }



    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testProposedPolicyPeriod(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers can see proposed policy period  criteria -- this needs to be verified
         story - N2020-28622
         @author - Azamat Uulu
         **/
        logger.info("verifying :: proposed policy period");
        CreateApplicant.createApplicant(DriverManager.getDriver(),"");
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
        ratingCriteriaPageActions.verifyEffectiveDateField(DriverManager.getDriver());
        ratingCriteriaPageActions.verifyExpirationDate(DriverManager.getDriver());
        ratingCriteriaPageActions.clickRatingCriteriaExitButton(DriverManager.getDriver());
        dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testBrokerReturnPreviousRatingAndUWPages(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers can return to Previous pages i.e. Rating Criteria and UW View
         story - N2020-28641 QAT-236
         @author - Azamat Uulu
         **/

        logger.info("verifying :: Brokers can return to Previous pages i.e. Rating Criteria and UW View");
        CreateApplicant.createApplicant(DriverManager.getDriver(),"");
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, "");
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map, "");
        }
        if(!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
            quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
        }
        quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
        underwritingQuestionsPageActions.clickUnderwritingQuestionsTab(DriverManager.getDriver());
        ratingCriteriaPageActions.clickDetailsPageTab(DriverManager.getDriver());

        if(ratingCriteriaPageActions.checkEffectiveDateIsVisible(DriverManager.getDriver())){
            boolean isEditButtonVisible = ratingCriteriaPageActions.checkEditButtonIsVisible(DriverManager.getDriver());
            if(isEditButtonVisible) {
                ratingCriteriaPageActions.clickEditButtonIsVisible(DriverManager.getDriver());
                ratingCriteriaPageActions.checkEditConfirmMsgCancelIsVisible(DriverManager.getDriver());
                ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
                underwritingQuestionsPageActions.clickUnderwritingQuestionsTab(DriverManager.getDriver());
                underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
                quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
                ratingCriteriaPageActions.clickDetailsPageTab(DriverManager.getDriver());
                ratingCriteriaPageActions.clickEditButtonIsVisible(DriverManager.getDriver());
                ratingCriteriaPageActions.checkEditConfirmMsgIsVisible(DriverManager.getDriver());

                boolean isQuestionTabVisible = underwritingQuestionsPageActions.verifyQuestionIsVisible(DriverManager.getDriver());
                boolean isQuoteTabVisible = quoteListPageActions.verifyQuoteIsVisible(DriverManager.getDriver());

                Assert.assertFalse(isQuestionTabVisible);
                Assert.assertFalse(isQuoteTabVisible);

                ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
                underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
                quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){databaseConnector.closeDatabaseConnector();}

}
