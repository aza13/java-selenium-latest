package QuotesPage;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.DatabaseQueries;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.JsonDataProvider;
import utils.dbConnector.DatabaseConnector;
import workflows.AnswerUnderwriterQuestions;
import workflows.FillApplicantDetails;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExistingSubmissionQuoteTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(ExistingSubmissionQuoteTests.class);
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
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData")
    public void testAddAndDeleteQuoteOption(JSONObject jsonObject) throws InterruptedException {
        /***
         this verifies whether broker can add the new quote option
         story - N2020-28632
         @author - Venkat Kottapalli
         **/
        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testAddAndDeleteQuoteOption");
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), coverage);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), jsonObject.get("status").toString());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.selectActiveQuote(DriverManager.getDriver());
//        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), jsonObject, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), jsonObject, coverage);
        }
//        assert quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
        if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
            int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
            if (jsonObject.get("functionality").equals("addQuoteOption")) {
                quoteListPageActions.clickAddOptionButton(DriverManager.getDriver());
                quoteListPageActions.addNewQuoteOption(DriverManager.getDriver(), optionCountBefore, jsonObject.get("claim").toString(), jsonObject.get("limit").toString(), jsonObject.get("retention").toString());
                int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                assert optionCountAfter == optionCountBefore + 1;
            } else if (jsonObject.get("functionality").equals("deleteQuoteOption")) {
                if (optionCountBefore > 1) {
                    quoteListPageActions.deleteQuoteOption(DriverManager.getDriver());
                    int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                    assert optionCountAfter == optionCountBefore - 1;
                } else if (optionCountBefore == 1) {
                    quoteListPageActions.deleteQuoteOption(DriverManager.getDriver());
                    int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                    assert optionCountAfter == optionCountBefore;
                }
            }
        } else {
            throw new SkipException("open quote doesn't exist or we landed on binder page, neither can add nor delete quote option");
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData")
    public void testAddingQuoteToExistingSubmission(JSONObject jsonObject) throws InterruptedException {
        /***
         this verifies whether user can add quote to an existing submission
         story - N2020-28633, N2020-28634
         @author - Venkat Kottapalli
         **/
        logger.info("Executing the testDeleteQuoteOption from QuoteOptionTests class :: testAddingQuoteToExistingSubmission");
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), coverage);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), jsonObject.get("status").toString());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), jsonObject, coverage);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), jsonObject, coverage);
            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfQuotesTabIsDisabled(DriverManager.getDriver())) {
                    logger.info("adding quote from the template");
                    quoteListPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), 0);
                } else {
                    quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                }
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                logger.info("open quote already exists for the submission, new quote can't be created until existing quote is locked");
                if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                    if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                    }
                }
            }
            int quotesCountBefore = quoteListPageActions.getQuotesCount(DriverManager.getDriver());
            quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
            logger.info("option count is 1, because only one quote will be open at a time");
            String optionCount = "1";
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), optionCount, jsonObject.get("claim").toString());
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), optionCount, jsonObject.get("limit").toString());
            quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), optionCount, jsonObject.get("retention").toString());
            int quotesCountAfter = quoteListPageActions.getQuotesCount(DriverManager.getDriver());
            assert quotesCountAfter == quotesCountBefore+1;
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData")
    public void testBrokerDownloadConfirmedQuote(JSONObject jsonObject) throws InterruptedException, SQLException {
        /***
         this test verifies brokers can download confirmed quote validation
         story - N2020-28652-QAT-156
         @author - Azamat Uulu
         **/
        logger.info("verifying brokers can download confirmed quote :: testBrokerDownloadConfirmedQuote");
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_SUBMISSIONS_WITH_CONFIRMED_QUOTES);
        int submissionCount = submissionIds.size();
        boolean confirmedQuote = false;
        String submissionId;
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("submission_id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    confirmedQuote = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (confirmedQuote) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                boolean pdfDownload = quoteListPageActions.clickPDFFileDownload(DriverManager.getDriver(), jsonObject.get("pdfFilename").toString());
                Assert.assertTrue(pdfDownload);

            } else {
                logger.info("No confirmed quotes available, to download the quote ");
            }
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData")
    public void testQuoteOptionPlaceOrder(JSONObject jsonObject) throws InterruptedException, SQLException {
        /***
         this test verifies brokers can download confirmed quote validation
         story - N2020-28655-QAT-247
         @author - Venat Kottapalli
         **/
        logger.info("verifying quote option placing order functionality :: testQuoteOptionPlaceOrder");
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_SUBMISSIONS_WITH_CONFIRMED_QUOTES);
        if (submissionIds != null) {
            int submissionCount = submissionIds.size();
            String submissionId = null;
            boolean confirmedQuote = false;
            if (submissionCount > 0) {
                for (HashMap<Object, Object> id : submissionIds) {
                    submissionId = id.get("submission_id").toString();
                    dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                    if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                        confirmedQuote = true;
                        break;
                    }
                    dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
                }
                if (confirmedQuote) {
                    logger.info("confirmed quotes available, placing the order");
                    quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                    if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                        quoteListPageActions.expandTheQuote(DriverManager.getDriver());
                        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
                        BindingPageActions bindingPageActions = quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
                        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
                        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                        String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver()).trim();
                        assert quoteStatus.equals(jsonObject.get("quoteStatus"));
                    }
                } else {
                    logger.info("No confirmed quotes available, to place the order");
                }
            }
        } else {
            throw new SkipException("There are no confirmed quotes in DB");
        }
    }


    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testUpdatedOptionMaxAggLimitAndPremium(JSONObject jsonObject) throws InterruptedException {
        /***
         this verifies whether option max agg limit and premium are updated or not
         story - N2020-30385, 28679
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testUpdatedOptionMaxAggLimitAndPremium");
        dashboardPageActions.clickQuotesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), coverage);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), jsonObject.get("status").toString());
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("claim1").toString());
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("limit1").toString());

            String premiumBefore = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitBefore = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("claim2").toString());
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), jsonObject.get("optionCount").toString(), jsonObject.get("limit2").toString());

            String premiumAfter = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitAfter = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            assert !Objects.equals(premiumAfter, premiumBefore);
            assert !Objects.equals(policyAggLimitAfter, policyAggLimitBefore);
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "QuotesPageData")
    public void testDownloadApplicationInQuote(JSONObject jsonObject) throws InterruptedException, SQLException {
        /***
         this test verifies brokers can download application form
         This story works with 9.5 only
         story - N2020-34254-QAT-434
         @author - Azamat Uulu
         **/
        logger.info("verifying brokers can download application form :: testDownloadApplicationInQuote");
        List<HashMap<Object, Object>> submissionIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_SUBMISSIONS_WITH_CONFIRMED_QUOTES);
        int submissionCount = submissionIds.size();
        boolean confirmedQuote = false;
        String submissionId;
        if (submissionCount > 0) {
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("submission_id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    confirmedQuote = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (confirmedQuote) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                boolean pdfDownload = quoteListPageActions.clickApplicationDownload(DriverManager.getDriver(), jsonObject.get("pdfFilename").toString());
                assert pdfDownload;

            } else {
                logger.info("No confirmed quotes available, to download the quote ");
            }
        }
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (databaseConnector != null) {
            databaseConnector.closeDatabaseConnector();
        }
    }
}
