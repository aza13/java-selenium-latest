import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.TestDataProvider;

import java.util.Map;

public class QuoteOptionTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteOptionTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuoteOptionTests");
        logger.info("Executing the tests from QuoteOptionTests class  :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testAddQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can add the new quote option
         story - N2020-28632
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testAddQuoteOption");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
            ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
            ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
            assert underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver())) {        // continue to create quote
            underwritingQuestionsPageActions.clickQuotesTab(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                assert quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
                int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                quoteListPageActions.addNewQuoteOption(DriverManager.getDriver(), optionCountBefore, map.get("claim"), map.get("limit"), map.get("retention"));
                int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                assert optionCountAfter == optionCountBefore + 1;
            }
        } else if (!underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver())) {
            // answer uw questions
            underwritingQuestionsPageActions.answerUnderWriterQuestions(DriverManager.getDriver(), "No");
            System.out.println("UW questions has to be answered");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testDeleteQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can delete the new quote option
         story - N2020-28633
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testDeleteQuoteOption from QuoteOptionTests class :: testAddQuoteOption");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
            ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
            ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
            assert underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver())) {        // continue to create quote
            underwritingQuestionsPageActions.clickQuotesTab(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                assert quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
                int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                if (optionCountBefore>1){
                    quoteListPageActions.deleteQuoteOption(DriverManager.getDriver());
                }else if(optionCountBefore==1){
                    quoteListPageActions.deleteQuoteOption(DriverManager.getDriver());
                }
                int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                assert optionCountAfter == optionCountBefore - 1;
            }
        } else if (!underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver())) {
            // answer uw questions
            underwritingQuestionsPageActions.answerUnderWriterQuestions(DriverManager.getDriver(), "No");
            System.out.println("UW questions has to be answered");
        }

    }
}
