import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import workflows.AnswerUnderwriterQuestions;
import workflows.CreateApplicant;
import workflows.FillApplicantDetails;

import java.sql.SQLException;
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
         this test Brokers can answers all underwriter questions
         story - N2020-28623-QAT-165
         @author - Azamat Uulu
         **/

        logger.info("verifying :: Under Writing Questions");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
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
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
            quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
            if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                } else {
                    quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                }
            }
            underwritingQuestionsPageActions.clickUnderwritingQuestionsPageTab(DriverManager.getDriver());
            boolean isEditButtonVisible1 = underwritingQuestionsPageActions.checkEditButtonIsVisible(DriverManager.getDriver());

            if(!isEditButtonVisible1) {
                underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            } else {
                underwritingQuestionsPageActions.clickEditButtonIsVisible(DriverManager.getDriver());
                underwritingQuestionsPageActions.checkEditConfirmMsgIsVisible(DriverManager.getDriver());
                underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            }
            boolean inactiveTextPresent = quoteListPageActions.isInactiveTextDisplayed(DriverManager.getDriver());
            Assert.assertTrue(inactiveTextPresent);
            boolean pdfFileIconValue = quoteListPageActions.isPDFFileIconDisplayed(DriverManager.getDriver());
            Assert.assertFalse(pdfFileIconValue);
            boolean wordFileIconValue = quoteListPageActions.isWordFileIconDisplayed(DriverManager.getDriver());
            Assert.assertFalse(wordFileIconValue);
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "UWQuestionsPageData")
    public void testSoftDeclineAfterUWQuestions(Map<String, String> map) throws InterruptedException, SQLException {

        /***
         this test soft decline after UW Questions
         story - /N2020-28674 -QAT-184
         @author - Azamat Uulu
         Updated By - Sheetal
         **/

        logger.info("verifying :: hard decline after UW Questions");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName, newInsuredWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.enterEmailAddress(DriverManager.getDriver());
        insuredPageActions.enterInsuredPhoneNumber(DriverManager.getDriver());
        assert insuredPageActions.verifyValidPhoneNumberFormat(DriverManager.getDriver());
        insuredPageActions.enterPhysicalAddress(DriverManager.getDriver());
        insuredPageActions.enterPhyCity(DriverManager.getDriver());
        insuredPageActions.enterPhyZipcode(DriverManager.getDriver());
        insuredPageActions.selectPhyState(DriverManager.getDriver());
        insuredPageActions.clickSameAsPhyAddress(DriverManager.getDriver());
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());

        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            if (map.get("product").equals("NetGuard® SELECT")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            } else {
                ratingCriteriaPageActions.enterRatingCriteriaNoPhysiciansRevenueAndRecords(DriverManager.getDriver(), map.get("noPhysicians"), map.get("revenue"), map.get("records"));
            }
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
            if (uwQuestionsAnswered) {
                logger.info("continue button is enabled, means UW questions are answered");
            } else {
                logger.info("continue button is disabled, means UW questions are not answered");
                underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
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
