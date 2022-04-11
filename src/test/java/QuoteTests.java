import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
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

public class QuoteTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuoteOptionTests");
        logger.info("Executing the tests from QuoteOptionTests class  :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        InsuredPageActions insuredPageActions = PageObjectManager.getInsuredPageActions();
        databaseConnector = new DatabaseConnector();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
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
            if(map.get("product").equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else{
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


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testLockQuote(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether applicant can lock a quote
         story - N2020-28633
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testDeleteQuoteOption from QuoteOptionTests class :: testLockQuote");

        logger.info("verifying creating new quote creation :: testCreateInsured");
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
            if(map.get("product").equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else{
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
                quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
                if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())){
                    if(quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())){
                        quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                        quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                    }else{
                        quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                    }
                }else {
                    Assert.fail("Confirm and quote button is disabled for some reason, some of the quotes missing premium");
                }
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testAddQuote(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can delete the new quote option
         story - N2020-28633
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
            if(map.get("product").equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else{
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
            if(!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
                if(quoteListPageActions.checkIfQuotesTabIsDisabled(DriverManager.getDriver())){
                        quoteListPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), 0);
                }else{
                    quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                }
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                logger.info("open quote exist for the submission, new quote can't be created until existing quote is locked");
                if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())){
                    quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                    quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
                }
            }else{
                quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
            }
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
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
        String submissionId;
        if(submissionCount>0){
            for (HashMap<Object, Object> id : submissionIds) {
                submissionId = id.get("id").toString();
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionId);
                if (dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver())) {
                    break;
                }
                dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            }

                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
                boolean pdfDownload = quoteListPageActions.clickPDFFileDownload(DriverManager.getDriver(), map.get("pdfFilename"));
                Assert.assertTrue(pdfDownload);
                boolean wordDownload = quoteListPageActions.clickWORDFileDownload(DriverManager.getDriver(), map.get("wordFilename"), map.get("wordPDFFilename"));
                Assert.assertTrue(wordDownload);

        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testConfirmAndLockQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can click and confirm lock quote option
         story - N2020-28645-QAT-174
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
            if(map.get("product").equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else{
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
            }else {
                logger.info("UW continue button is disabled, means UW questions are not answered");
                underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            }
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            if(!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            quoteListPageActions.addNewQuoteOption(DriverManager.getDriver(), 0, map.get("claim"), map.get("limit"), map.get("retention"));
            if (quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver())){
                /*String quoteSuccessStatusMessage = quoteListPageActions.verifySuccessConfirmAndLockMessage(DriverManager.getDriver());
                Assert.assertEquals(quoteSuccessStatusMessage, map.get("quoteSuccessMessage"));*/
                quoteListPageActions.verifyStatusConfirmAndLockReadyToPlaceOrder(DriverManager.getDriver());
                assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
                assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
            }else {
                Assert.fail("Confirm and quote button is disabled for some reason");
            }
        }

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
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
            if(map.get("product").equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else{
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
            }else {
                logger.info("continue button is disabled, means UW questions are not answered");
                underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            }
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            if(!quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
                quoteListPageActions.clickQuotesTab(DriverManager.getDriver());
            }
        }
        if (quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())) {
            assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
            assert quoteListPageActions.verifyQuotePreview(DriverManager.getDriver());
        }

    }
}
