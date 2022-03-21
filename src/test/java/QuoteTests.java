import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;

import java.util.Map;

public class QuoteTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private InsuredPageActions insuredPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuoteOptionTests");
        logger.info("Executing the tests from QuoteOptionTests class  :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        insuredPageActions = PageObjectManager.getInsuredPageActions();
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

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testBrokerDownloadConfirmedQuote(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies brokers can download confirmed quote validation
         story - N2020-28652-QAT-156
         @author - Azamat Uulu
         **/

        logger.info("verifying brokers can download confirmed quote :: testBrokerDownloadConfirmedQuote");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("reffNumber").replaceAll("^\"|\"$", ""));
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        quoteListPageActions.clickQuotesTab(DriverManager.getDriver());

        boolean pdfDownload = quoteListPageActions.clickPDFFileDownload(DriverManager.getDriver(), map.get("pdfFilename"));
        Assert.assertTrue(pdfDownload);

        boolean wordDownload = quoteListPageActions.clickWORDFileDownload(DriverManager.getDriver(), map.get("wordFilename"), map.get("wordPDFFilename"));
        Assert.assertTrue(wordDownload);

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuoteOptionPageData")
    public void testConfirmAndLockQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can click and confirm lock quote option
         story - N2020-28645-QAT-174
         @author - Azamat Uulu
         **/

        logger.info("Executing the testConfirmAndLockQuoteOption from QuoteTests class :: testConfirmAndLockQuoteOption");

        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));

        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName,newInsuredWebsite);
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
            ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
            ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
            ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.ratingCriteriaPageClick(DriverManager.getDriver());
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());

        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {        // continue to create quote

            underwritingQuestionsPageActions.isGeneralHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickGeneralHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isEnhancementsHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickEnhancementsHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isRequiredHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickRequiredHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isITDepartmentHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickITDepartmentHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isInternalSecurityHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickInternalSecurityHeader(DriverManager.getDriver());

            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        }

        if(quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
            quoteListPageActions.verifyStatusConfirmAndLockInProgress(DriverManager.getDriver());
            quoteListPageActions.clickConfirmAndLock(DriverManager.getDriver());
            String quoteSuccessStatusMessage = quoteListPageActions.verifySuccessConfirmAndLockMessage(DriverManager.getDriver());
            Assert.assertEquals(quoteSuccessStatusMessage, map.get("quoteSuccessMessage"));
            quoteListPageActions.verifyStatusConfirmAndLockReadyToPlaceOrder(DriverManager.getDriver());
            assert quoteListPageActions.verifyPDFFileAvailable(DriverManager.getDriver());
            assert quoteListPageActions.verifyWORDFileAvailable(DriverManager.getDriver());
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

        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));

        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName,newInsuredWebsite);
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
            ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
            ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
            ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.ratingCriteriaPageClick(DriverManager.getDriver());
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());

        }
        if (underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver())) {        // continue to create quote

            underwritingQuestionsPageActions.isGeneralHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickGeneralHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isEnhancementsHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickEnhancementsHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isRequiredHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickRequiredHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isITDepartmentHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickITDepartmentHeader(DriverManager.getDriver());
            underwritingQuestionsPageActions.isInternalSecurityHeaderDisplayed(DriverManager.getDriver());
            underwritingQuestionsPageActions.clickInternalSecurityHeader(DriverManager.getDriver());

            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        }

        if(quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver())){
           assert quoteListPageActions.verifyQuotePreviewOptionVisible(DriverManager.getDriver());
           quoteListPageActions.clickQuotePreviewOption(DriverManager.getDriver());

        }

    }
}
