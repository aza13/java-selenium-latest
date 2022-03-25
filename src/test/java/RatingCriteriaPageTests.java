import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import constants.DatabaseQueries;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingCriteriaPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;


    private DatabaseConnector databaseConnector;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;


    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("RatingCriteriaPageTests");
        logger.info("Creating object for RatingCriteriaPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();


        databaseConnector = new DatabaseConnector();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testBusinessClassRatingCriteria(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers Business Class criteria
         story - N2020-30438
         @author - Azamat Uulu
         **/

        logger.info("verifying :: business class rating criteria");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions.clickRatingCriteriaButton(DriverManager.getDriver());
        assert ratingCriteriaPageActions.ratingCriteriaTitle(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.clickExitRatingCriteria(DriverManager.getDriver());
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickFilterByProductName(DriverManager.getDriver());
        dashboardPageActions.selectProductInFilter(DriverManager.getDriver(), map.get("product"));
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(),"Active");
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(DriverManager.getDriver(), map.get("numberOfResidentialUnits"), map.get("totalCommercialSquareFeet"));
        ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());



    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testHardDeclineAfterRatingCriteria(Map<String, String> map) throws InterruptedException, ParseException, SQLException {
        /***
         this test hard decline after rating criteria
         story - N2020-28624
         **/

        String quoteId="";
        List<HashMap<Object, Object>> listValueQuoteIds =
                databaseConnector.getResultSetToList(DatabaseQueries.GET_QUOTE_FOR_HARD_DECLINE);
        if (listValueQuoteIds != null) {
            quoteId=listValueQuoteIds.get(0).get("id").toString();
            System.out.println(quoteId);
        } else{
            throw new SkipException("Unable to get policy Ids from the DB ");
        }
            logger.info("verifying :: hard decline after rating criteria");
            dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
            dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), (quoteId));
            dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
            ratingCriteriaPageActions.enterValueRatingCriteriaTextBox(DriverManager.getDriver(), map.get("revenue"), map.get("records"));
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
            assert ratingCriteriaPageActions.hardDeclineText(DriverManager.getDriver()).isDisplayed();
            ratingCriteriaPageActions.clickRatingCriteriaOkButton(DriverManager.getDriver());
            ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());
            dashboardPageActions.clickClearSearchButton(DriverManager.getDriver());
            dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(),(quoteId));
            String statusAfterDecline = dashboardPageActions.getQuoteStatus(DriverManager.getDriver());
            String actualStatus = "Declined";
            assert statusAfterDecline.equals(actualStatus);

    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testProposedPolicyPeriod(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers can see proposed policy period  criteria
         story - N2020-28622
         @author - Azamat Uulu
         **/

        logger.info("verifying :: proposed policy period");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), newInsuredName,newInsuredWebsite);
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

    @AfterClass(alwaysRun = true)
    public void tearDown(){databaseConnector.closeDatabaseConnector();}

}
