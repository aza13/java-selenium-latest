import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.TestDataProvider;
import utils.fileReader.ConfigDataReader;
import workflows.AnswerUnderwriterQuestions;
import workflows.CreateApplicant;
import workflows.FillApplicantDetails;
import java.util.Map;

public class UWPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(UWPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private QuoteListPageActions quoteListPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("UWPageTests");
        logger.info("Creating object for UWPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "UWQuestionsPageData")
    public void testBrokerAnswersUnderWriterQuestions(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies whether Brokers can answer all underwriter questions
         story - N2020-28623-QAT-165
         @author - Azamat Uulu
         **/

        logger.info("verifying :: Under Writing Questions");
        CreateApplicant.createApplicant(DriverManager.getDriver(), coverage);
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map, coverage);
            assert true;
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "UWQuestionsPageData")
    public void testQuotesInvalidatedWhenEdited(Map<String, String> map) throws InterruptedException {
        /***
         this test Quotes Can Be Invalidated When Rating/UW are Edited
         story - N2020-28642 QAT-238
         @author - Azamat Uulu
         **/
        logger.info("verifying :: Quotes Can Be Invalidated When Rating/UW are Edited");
        CreateApplicant.createApplicant(DriverManager.getDriver(), coverage);
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map, coverage);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
            if(ConfigDataReader.getInstance().getProperty("coverage").contains("Ophthalmic")){
                quoteListPageActions.selectBRRPCoverageWithoutInvestigation(DriverManager.getDriver());
                quoteListPageActions.selectBRRPCoverageWithInvestigation(DriverManager.getDriver());
            }
//            quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
            if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                }
            }
            underwritingQuestionsPageActions.clickUnderwritingQuestionsPageTab(DriverManager.getDriver());
            boolean isEditButtonVisible1 = underwritingQuestionsPageActions.checkEditButtonIsVisible(DriverManager.getDriver());
            if (isEditButtonVisible1) {
                underwritingQuestionsPageActions.clickEditButtonIsVisible(DriverManager.getDriver());
                underwritingQuestionsPageActions.checkEditConfirmMsgIsVisible(DriverManager.getDriver());
            }
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            boolean inactiveTextPresent = quoteListPageActions.isInactiveTextDisplayed(DriverManager.getDriver());
            Assert.assertTrue(inactiveTextPresent);
            boolean pdfFileIconValue = quoteListPageActions.isPDFFileIconDisplayed(DriverManager.getDriver());
            Assert.assertFalse(pdfFileIconValue);
            boolean wordFileIconValue = quoteListPageActions.isWordFileIconDisplayed(DriverManager.getDriver());
            Assert.assertFalse(wordFileIconValue);
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "UWQuestionsPageData")
    public void testSoftDeclineAfterUWQuestions(Map<String, String> map) throws InterruptedException {
        /***
         this test soft decline after UW Questions
         story - /N2020-28674 -QAT-184
         @author - Azamat Uulu
         Updated By - Sheetal
         **/

        logger.info("verifying :: hard decline after UW Questions");
        CreateApplicant.createApplicant(DriverManager.getDriver(), coverage);
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
            if (uwQuestionsAnswered) {
                logger.info("continue button is enabled, means UW questions are answered");
            } else {
                logger.info("continue button is disabled, means UW questions are not answered");
                if(ConfigDataReader.getInstance().getProperty("coverage").contains("NetGuard")){
                    underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
                    underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
                }else{
                    underwritingQuestionsPageActions.answerFirstUWQuestion(DriverManager.getDriver());
                    underwritingQuestionsPageActions.answerUWQuestionButtonsOMICProduct2(DriverManager.getDriver());
                }
            }
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            underwritingQuestionsPageActions.verifySoftDeclinePopupHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.enterSoftDeclineTextAndSubmit(DriverManager.getDriver());

            String title = dashboardPageActions.getMyQuotesTabTitle(DriverManager.getDriver()).trim();
            assert title.contentEquals(map.get("myQuotes"));
            String firstAvailableStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
            assert firstAvailableStatus.equals("In Review");
        }
    }

}
