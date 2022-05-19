import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.FakeDataHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;
import utils.fileReader.TextFileReader;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class  QuotesPageTests extends BaseTest {

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
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            if (map.get("product").equals("NetGuard® SELECT")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            } else {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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
         story - N2020-28633
         @author - Venkat Kottapalli
         **/
        logger.info("test verifying locking a quote :: testLockQuote");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName, newInsuredWebsite);
        TextFileReader.writeDataToTextFile(ConstantVariable.INSURED_DATA_FILEPATH, newInsuredName + ";" + newInsuredWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        if (!insuredPageActions.isCreateNewInsuredTextDisplayed(DriverManager.getDriver())) {
            insuredPageActions.clickNewInsuredButton(DriverManager.getDriver());
        }
        insuredPageActions.enterEmailAddress(DriverManager.getDriver());
        insuredPageActions.enterInsuredPhoneNumber(DriverManager.getDriver());
        insuredPageActions.enterPhysicalAddress(DriverManager.getDriver());
        insuredPageActions.enterPhyCity(DriverManager.getDriver());
        insuredPageActions.enterPhyZipcode(DriverManager.getDriver());
        insuredPageActions.selectPhyState(DriverManager.getDriver());
        insuredPageActions.clickSameAsPhyAddress(DriverManager.getDriver());
        insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            if (map.get("product").equals("NetGuard® SELECT")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            } else {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())) {
                    if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                    } else {
                        quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                        quoteListPageActions.isQuoteExpiryDisplayed(DriverManager.getDriver());
                    }
                } else {
                    Assert.fail("Confirm and quote button is disabled for some reason, some of the quotes missing premium");
                }
            }
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
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), ConstantVariable.PRODUCT);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            if (map.get("product").equals("NetGuard® SELECT")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            } else {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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
                if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())) {
                    if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                    } else {
                        quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    }
                }
            } else {
                quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
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
                boolean wordDownload = quoteListPageActions.clickWORDFileDownload(DriverManager.getDriver(), map.get("wordFilename"), map.get("wordPDFFilename"));
                Assert.assertTrue(wordDownload);
            } else {
                logger.info("No confirmed quotes available, to download the quote ");
            }

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
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName, newInsuredWebsite);
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
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            }
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {
            boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
            if (uwQuestionsAnswered) {
                logger.info("UW continue button is enabled, means UW questions are answered");
            } else {
                logger.info("UW continue button is disabled, means UW questions are not answered");
                underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            }
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            String quoteId = quoteListPageActions.getOpenQuoteId(DriverManager.getDriver());
            //quoteListPageActions.addNewQuoteOption(DriverManager.getDriver(), 0, map.get("claim"), map.get("limit"), map.get("retention"));
            if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                } else {
                    quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    quoteListPageActions.verifyStatusConfirmAndLockReadyToPlaceOrder(DriverManager.getDriver());
                    assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
                    assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
                    quoteListPageActions.clickPlaceOrderButton(DriverManager.getDriver());
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
                    /*String updateQuery = UPDATE_IN_REVIEW_SUBMISSION_TO_ACTIVE+submissionId+";";
                    databaseConnector.update(updateQuery);*/
                    WaitHelper.pause(10000);
                    dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                    String quoteStatus = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
                    assert quoteStatus.contentEquals("Order Placed");
                }

            } else {
                Assert.fail("Confirm and quote button is disabled for some reason");
            }
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testQuotePreview(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can click preview quote option
         story - N2020-28644-QAT-229
         @author - Azamat Uulu
         **/

        logger.info("Executing the testQuotePreview from QuoteTests class :: testQuotePreview");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName, newInsuredWebsite);
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
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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
                        quoteListPageActions.clickPlaceOrderButton(DriverManager.getDriver());
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testQuoteOutsideBoundSoftDeclined(Map<String, String> map) throws InterruptedException, SQLException {
        /***
         this test verifies Broker Portal Quotes Outside the Bounds Will Be Soft Declined
         story - N2020-28646-QAT-234
         @author - Azamat Uulu
         **/
        logger.info("verifying Quotes Outside the Bounds Will Be Soft Declined functionality :: testQuoteOutsideBoundSoftDeclined");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName, newInsuredWebsite);
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
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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

            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("claim"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit"));
            quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver());
            quoteListPageActions.verifySoftDeclinePopup(DriverManager.getDriver());

            String actualFirstStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
            assert actualFirstStatus.equals("Cancelled");
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testQuoteOptionCoverageGroupValidation(Map<String, String> map) throws InterruptedException, SQLException {
        /***
         this test verifies Broker Portal Quotes Can Select/Unselect Coverage Groups for an Option
         story - N2020-30895 and N2020-28635/28636 QAT-231
         @author - Azamat Uulu
         **/
        logger.info("verifying Quotes Broker Can Select/Unselect Coverage Groups for an Option :: testQuoteOptionCoverageGroupValidation");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName, newInsuredWebsite);
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
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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

            if (!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());

            int quoteOptionsCount = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
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
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("claim"));
            String selectedPerClaimValue = quoteListPageActions.clickClaimCheckbox(DriverManager.getDriver(), map.get("optionCount"));
            Assert.assertEquals(selectedPerClaimValue, map.get("claim"));

            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit"));
            String aggLimit = quoteListPageActions.getAggLimitSelectedValue(DriverManager.getDriver());
            Assert.assertEquals(aggLimit, map.get("limit"));

            quoteListPageActions.selectRetentionOption(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("retention"));
            String retentionValue = quoteListPageActions.getRetentionSelectedValue(DriverManager.getDriver());
            Assert.assertEquals(retentionValue, map.get("retention"));
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testUpdatedOptionMaxAggLimitAndPremium(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether option max agg limit and premium are updated or not
         story - N2020-30385, 28679
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testUpdatedOptionMaxAggLimitAndPremium");
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("claim1"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit1"));

            String premiumBefore = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitBefore = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            quoteListPageActions.selectPerClaim(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("claim2"));
            quoteListPageActions.selectAggregateLimit(DriverManager.getDriver(), Integer.parseInt(map.get("optionCount")), map.get("limit2"));

            String premiumAfter = quoteListPageActions.getFirstOptionPremium(DriverManager.getDriver());
            String policyAggLimitAfter = quoteListPageActions.getFirstMaxPolicyAggLimit(DriverManager.getDriver());

            assert !Objects.equals(premiumAfter, premiumBefore);
            assert !Objects.equals(policyAggLimitAfter, policyAggLimitBefore);

        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        databaseConnector.closeDatabaseConnector();
    }
}
