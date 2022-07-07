import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;

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
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName,newInsuredWebsite);
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
            if(ConstantVariable.PRODUCT.equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else if(ConstantVariable.PRODUCT.contains("Ophthalmic")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass3"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNoOfPhysicians(DriverManager.getDriver(), map.get("physiciansCount"));
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            }else{
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            }
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        underwritingQuestionsPageActions.clickExitQuestion(DriverManager.getDriver());
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testHardDeclineAfterRatingCriteria(Map<String, String> map) throws InterruptedException {
        /***
         this test hard decline after rating criteria
         story - N2020-28624 QAT-171
         @author - Azamat Uulu
         Updated By - Sheetal
         **/

        logger.info("verifying :: test hard decline after rating criteria");
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
            if(ConstantVariable.PRODUCT.equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            } else{

                ratingCriteriaPageActions.enterRatingCriteriaNoPhysiciansRevenueAndRecords(DriverManager.getDriver(), map.get("noPhysicians"), map.get("revenue"), map.get("records"));
            }

            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
        }
        ratingCriteriaPageActions.verifyAndClickHardDeclinePopup(DriverManager.getDriver());
        String actualFirstStatus = dashboardPageActions.firstAvailableStatus(DriverManager.getDriver());
        assert actualFirstStatus.equals("Declined");

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testProposedPolicyPeriod(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers can see proposed policy period  criteria
         story - N2020-28622
         @author - Azamat Uulu
         **/

        logger.info("verifying :: proposed policy period");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName,newInsuredWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());

        if (!insuredPageActions.isCreateNewInsuredTextDisplayed(DriverManager.getDriver())){
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
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());

        ratingCriteriaPageActions.clickRatingCriteriaEffectiveDateCalenderButton(DriverManager.getDriver());
        boolean val = ratingCriteriaPageActions.viewRatingCriteriaExpirationDateField(DriverManager.getDriver());
        Assert.assertTrue(val);
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
            if(ConstantVariable.PRODUCT.equals("NetGuard® SELECT")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNetWorth(DriverManager.getDriver(), map.get("netWorth"));
            }else if(ConstantVariable.PRODUCT.contains("Ophthalmic")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(DriverManager.getDriver(), map.get("businessClass3"));
                ratingCriteriaPageActions.clickBusinessClassOption(DriverManager.getDriver());
                ratingCriteriaPageActions.enterNoOfPhysicians(DriverManager.getDriver(), map.get("physiciansCount"));
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
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
