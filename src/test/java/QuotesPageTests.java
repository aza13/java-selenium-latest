import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.DatabaseQueries;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;
import workflows.AnswerUnderwriterQuestions;
import workflows.CreateApplicant;
import workflows.FillApplicantDetails;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class QuotesPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuotesPageTests.class);
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testAddAndDeleteQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can add the new quote option
         story - N2020-28632
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testAddAndDeleteQuoteOption");
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), product);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                if (map.get("functionality").equals("addQuoteOption")) {
                    quoteListPageActions.clickAddOptionButton(DriverManager.getDriver());
                    quoteListPageActions.addNewQuoteOption(DriverManager.getDriver(), optionCountBefore, map.get("claim"), map.get("limit"), map.get("retention"));
                    int optionCountAfter = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                    assert optionCountAfter == optionCountBefore + 1;
                } else if (map.get("functionality").equals("deleteQuoteOption")) {
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
            }
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testLockQuote(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether applicant can lock a quote
         story - N2020-28633 and N2020-28708
         @author - Venkat Kottapalli
         **/
        logger.info("test verifying locking a quote :: testLockQuote");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
            if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                    assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
                    if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                        if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                            quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                        } else {
                            quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                            assert quoteListPageActions.isQuoteExpiryDisplayed(DriverManager.getDriver());
                            assert quoteListPageActions.verifyIfLockedQuoteExist(DriverManager.getDriver());
                            assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
                            assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
                            quoteListPageActions.expandTheQuote(DriverManager.getDriver());
                        }
                    } else {
                        Assert.fail("Confirm and quote button is disabled for some reason, some of the quotes missing premium");
                    }
                }
            }
        } else {
            Assert.fail("Underwriter questions not displayed after clicking Continue button on Rating Criteria");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testAddQuote(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can delete the new quote option
         story - N2020-28633, N2020-28634
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testDeleteQuoteOption from QuoteOptionTests class :: testAddQuote");
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), product);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
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
                logger.info("open quote exist for the submission, new quote can't be created until existing quote is locked");
                if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
                    if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                    } else {
                        quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    }
                }
            } else {
                quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
                quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim"));
                quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("retention"));
                quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit"));

            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testBrokerDownloadConfirmedQuote(Map<String, String> map) throws InterruptedException, SQLException {
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
                submissionId = id.get("id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    confirmedQuote = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (confirmedQuote) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                boolean pdfDownload = quoteListPageActions.clickPDFFileDownload(DriverManager.getDriver(), map.get("pdfFilename"));
                Assert.assertTrue(pdfDownload);

            } else {
                logger.info("No confirmed quotes available, to download the quote ");
            }

        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testConfirmDatesModal(Map<String, String> map) throws InterruptedException, ParseException {
        /*****************************************************************
         this test validates confirm dates modal
         story - N2020-35623
         @author - Venkat Kottapalli
         ******************************************************************/

        logger.info("Executing the testConfirmDatesModal from BindingPageTests class :: testVerifyQuoteBinding");
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
        assert status.contentEquals(map.get("quoteStatus"));
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        assert quoteListPageActions.validateConfirmDatesModalFields(DriverManager.getDriver());
        String effDate = quoteListPageActions.getEffectiveDate(DriverManager.getDriver());
        long dateDifference = quoteListPageActions.validateEffectiveDate(DriverManager.getDriver());
        if (dateDifference > 7){
            // eff date defaults to empty/null
            assert effDate == null;
        }else{
            // then the date defaults to the Effective Date that was entered on the rating criteria
            assert effDate != null;
        }
        quoteListPageActions.clickConfirmDatesConfirmButton(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testContactUnderwriterModalBeforeLock(Map<String, String> map) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies contact underwriter modal
         story - N2020-33999
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
            quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
            assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed2(DriverManager.getDriver());
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            assert quoteListPageActions.submitReviewCancelButton(DriverManager.getDriver()).isDisplayed();
            quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
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
    public void testContactUnderwriterModalAfterLock(Map<String, String> map) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies contact underwriter modal
         story - N2020-35238 - QAT-546
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
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
            boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
            assert quoteLocked;
            quoteListPageActions.clickContactUnderwriter(DriverManager.getDriver());
            assert quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver());
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
            // this part will be modified as per the future stories
            quoteListPageActions.clickOnExitDashboard(DriverManager.getDriver());
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
            assert quoteStatus.contentEquals("Active");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testConfirmAndLockQuoteOption(Map<String, String> map) throws InterruptedException, SQLException {
        /***
         this verifies whether broker can click and confirm lock quote option
         story - N2020-28645, 28655 -QAT-174
         @author - Azamat Uulu
         **/
        logger.info("Executing the testConfirmAndLockQuoteOption from QuoteTests class :: testConfirmAndLockQuoteOption");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
        boolean quoteLocked = quoteListPageActions.lockTheQuote(DriverManager.getDriver());
        assert quoteLocked;
        String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
        assert status.contentEquals(map.get("quoteStatus"));
        assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
        assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
        logger.info("placing the quote order and verifying it;s status in dashboard");
        quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
        quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
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
        BindingPageActions bindingPageActions = PageObjectManager.getBindingPageActions();
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
        String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
        assert quoteStatus.contentEquals("Order Placed");
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testQuotePreview(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can click preview quote option
         story - N2020-28644-QAT-229
         @author - Azamat Uulu
         **/

        logger.info("Executing the testQuotePreview from QuoteTests class :: testQuotePreview");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfQuotesTabIsDisabled(DriverManager.getDriver())) {
                    quoteListPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), 0);
                } else {
                    quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                }
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            assert quoteListPageActions.verifyQuotePreview(DriverManager.getDriver());
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testQuoteOptionPlaceOrder(Map<String, String> map) throws InterruptedException, SQLException {
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
                    submissionId = id.get("id").toString();
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
                        quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
                    }
                    dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                    String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver()).trim();
                    assert quoteStatus.equals(map.get("quoteStatus"));
                } else {
                    logger.info("No confirmed quotes available, to place the order");
                }
            }
        } else {
            throw new SkipException("There are no confirmed quotes in DB");
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testQuoteOutsideBoundSoftDeclined(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies Broker Portal Quotes Outside the Bounds Will Be Soft Declined
         story - N2020-28646-QAT-234
         @author - Azamat Uulu
         **/
        logger.info("verifying Quotes Outside the Bounds Will Be Soft Declined functionality :: testQuoteOutsideBoundSoftDeclined");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit"));
            quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver());
            if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
            } else {
                quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
                assert Objects.equals(status, "Ready to Place Order");
                quoteListPageActions.clickConfirmDatesAndPlaceOrderButton(DriverManager.getDriver());
                quoteListPageActions.submitOrderConfirmation(DriverManager.getDriver());
                quoteListPageActions.verifySoftDeclinePopup(DriverManager.getDriver());
                String actualFirstStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
                assert actualFirstStatus.equals("Cancelled");
            }

        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testQuoteOptionCoverageGroupValidation(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies Broker Portal Quotes Can Select/Unselect Coverage Groups for an Option
         story - N2020-30895 and N2020-28635/28636 QAT-231
         @author - Azamat Uulu
         **/
        logger.info("verifying Quotes Broker Can Select/Unselect Coverage Groups for an Option :: testQuoteOptionCoverageGroupValidation");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            logger.info("verifying the fields, when coverage unchecked");
            boolean optionCoverageGroupUnSelect = quoteListPageActions.verifyOptionCoverageGroupUnSelect(DriverManager.getDriver());
            Assert.assertFalse(optionCoverageGroupUnSelect);
            assert quoteListPageActions.verifyWarningMsgWhenUncheckedOptionCoverageGroup(DriverManager.getDriver());
            boolean isPremiumAmountDisplay = quoteListPageActions.isPremiumAmountDisplay(DriverManager.getDriver());
            Assert.assertFalse(isPremiumAmountDisplay);
            boolean isConfirmAndLockButtonVisible = quoteListPageActions.isConfirmedAndLockQuoteButtonDisplay(DriverManager.getDriver());
            Assert.assertFalse(isConfirmAndLockButtonVisible);
            logger.info("verifying the fields, when coverage checked");
            boolean optionCoverageGroupSelect = quoteListPageActions.verifyOptionCoverageGroupSelect(DriverManager.getDriver());
            Assert.assertTrue(optionCoverageGroupSelect);
            boolean defaultSelectedCoverage = quoteListPageActions.verifyDefaultCoverageCheckboxSelected(DriverManager.getDriver());
            Assert.assertTrue(defaultSelectedCoverage);
            logger.info("verifying whether selected values are saved or not in new option");
            quoteListPageActions.clickAddOptionButton(DriverManager.getDriver());
            boolean isSelectVisible = quoteListPageActions.isSelectVisibleToNewAddOption(DriverManager.getDriver());
            Assert.assertTrue(isSelectVisible);
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim"));
            String selectedPerClaimValue = quoteListPageActions.clickClaimCheckbox(DriverManager.getDriver(), map.get("optionCount"));
            Assert.assertEquals(selectedPerClaimValue, map.get("claim"));

            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit"));
            String aggLimit = quoteListPageActions.getAggLimitSelectedValue(DriverManager.getDriver());
            Assert.assertEquals(aggLimit, map.get("limit"));

            boolean valueSelected = quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("retention"));
            if (valueSelected) {
                String retentionValue = quoteListPageActions.getRetentionSelectedValue(DriverManager.getDriver());
                Assert.assertEquals(retentionValue, map.get("retention"));
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testUpdatedOptionMaxAggLimitAndPremium(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether option max agg limit and premium are updated or not
         story - N2020-30385, 28679
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testUpdatedOptionMaxAggLimitAndPremium");
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByCoverageName(DriverManager.getDriver());
        dashboardPageActions.selectCoverageInFilter(DriverManager.getDriver(), product);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim1"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit1"));

            String premiumBefore = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitBefore = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim2"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit2"));

            String premiumAfter = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitAfter = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            assert !Objects.equals(premiumAfter, premiumBefore);
            assert !Objects.equals(policyAggLimitAfter, policyAggLimitBefore);

        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testNotDisplayPremiumIfReviewRequired(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies if premium should not displayed if review required
         story - N2020-33454 and QAT-335
         @author - Azamat Uulu
         **/
        logger.info("verifies if premium should not displayed if review required :: testNotDisplayPremiumIfReviewRequired");
        CreateApplicant.createApplicant(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            FillApplicantDetails.fillApplicantDetails(DriverManager.getDriver(), map);
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            AnswerUnderwriterQuestions.answerUnderwriterQuestions(DriverManager.getDriver(), map);
            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfQuotesTabIsDisabled(DriverManager.getDriver())) {
                    quoteListPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), 0);
                } else {
                    quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                }
            }
        }

        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim1"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit1"));
            String premiumBefore = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), map.get("optionCount"), map.get("claim2"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit2"));
            String premiumAfter = "";
            assert !Objects.equals(premiumAfter, premiumBefore);
            boolean isTextVisible = quoteListPageActions.verifyOutsideBrokerPortalGuidelinesVisible(DriverManager.getDriver());
            Assert.assertTrue(isTextVisible);


        }
    }
    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData", enabled = false)
    public void testDownloadApplicationInQuote(Map<String, String> map) throws InterruptedException, SQLException {
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
                submissionId = id.get("id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    confirmedQuote = true;
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }
            if (confirmedQuote) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                boolean pdfDownload = quoteListPageActions.clickApplicationDownload(DriverManager.getDriver(), map.get("pdfFilename"));
                Assert.assertFalse(pdfDownload);

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
