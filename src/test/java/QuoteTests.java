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
import utils.fileReader.TextFileReader;

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
    public void testAddAndDeleteQuoteOption(Map<String, String> map) throws InterruptedException {
        /***
         this verifies whether broker can add the new quote option
         story - N2020-28632
         @author - Venkat Kottapalli
         **/

        logger.info("Executing the testAddQuoteOption from QuoteOptionTests class :: testAddAndDeleteQuoteOption");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
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
        }
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
        if (!uwQuestionsAnswered) {        // continue to create quote
            underwritingQuestionsPageActions.clickQuotesTab(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                if (map.get("functionality").equals("addQuoteOption")) {
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
        } else {
            // answer uw questions
            underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
            underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            System.out.println("UW questions has to be answered");
            underwritingQuestionsPageActions.clickQuotesTab(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                assert quoteListPageActions.isQuoteListPageDisplayed(DriverManager.getDriver());
                int optionCountBefore = quoteListPageActions.getQuoteOptionCount(DriverManager.getDriver());
                if (map.get("functionality").equals("addQuoteOption")) {
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
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_OFFICE_ID);
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName, newInsuredWebsite);
        TextFileReader.writeDataToTextFile(ConstantVariable.INSURED_DATA_FILEPATH, newInsuredName + ";" + newInsuredWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        if (!insuredPageActions.isCreateNeInsuredTextDisplayed(DriverManager.getDriver())) {
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
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
        ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
        ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
        ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
        ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
        boolean quoteList = quoteListPageActions.checkIfQuoteListContainerDisplayed(DriverManager.getDriver());
        if (!uwQuestionsAnswered && quoteList) {
            logger.info("when UW questions are answered and navigated to quotes page");
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                logger.info("open quote exist for the submission, new quote can't be created until existing quote is locked");
//                quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
            } else {
                quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
            }
        } else if (uwQuestionsAnswered) {
            // answer uw questions
            logger.info("when UW questions are not answered");
            underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
            underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            logger.info("UW questions has to be answered");
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                    logger.info("open quote exist for the submission, new quote can't be created until it is locked");
                    quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
                } else {
                    quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
                }
            } else {
                underwritingQuestionsPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), "4 options");
            }

        } else {
            logger.info("when UW questions answered and quote template options displayed on UW page");
            underwritingQuestionsPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), "4 options");
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
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_OFFICE_ID);
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), ConstantVariable.PRODUCT);
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), map.get("status"));
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
            ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
            ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
            ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(DriverManager.getDriver());
        boolean quoteList = quoteListPageActions.checkIfQuoteListContainerDisplayed(DriverManager.getDriver());
        if (!uwQuestionsAnswered && quoteList) {
            logger.info("when UW questions are answered and navigated to quotes page");
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                logger.info("open quote exist for the submission, new quote can't be created until existing quote is locked");
//                quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
            } else {
                quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
            }
        } else if (uwQuestionsAnswered) {
            // answer uw questions
            logger.info("when UW questions are not answered");
            underwritingQuestionsPageActions.answerUWQuestionButtons(DriverManager.getDriver(), map.get("uwQuestionsAnswer"));
            underwritingQuestionsPageActions.answerUWQuestionDropdowns(DriverManager.getDriver(), map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            logger.info("UW questions has to be answered");
            underwritingQuestionsPageActions.clickUWQuestionsContinueButton(DriverManager.getDriver());
            if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                if (quoteListPageActions.checkIfOpenQuoteExist(DriverManager.getDriver())) {
                    logger.info("open quote exist for the submission, new quote can't be created until it is locked");
                    quoteListPageActions.clickConfirmQuoteButton(DriverManager.getDriver());
                } else {
                    quoteListPageActions.addNewQuote(DriverManager.getDriver(), "Custom Quote");
                }
            } else {
                underwritingQuestionsPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), "4 options");
            }

        } else {
            logger.info("when UW questions answered and quote template options displayed on UW page");
            underwritingQuestionsPageActions.selectQuoteTemplateOption(DriverManager.getDriver(), "4 options");
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

        logger.info("Executing the testConfirmAndLockQuoteOption from QuoteOptionTests class :: testConfirmAndLockQuoteOption");

        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);

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
}
