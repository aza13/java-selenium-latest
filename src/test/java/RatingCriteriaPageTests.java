import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.TestDataProvider;

import java.text.ParseException;
import java.util.Map;

public class RatingCriteriaPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(DashboardPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("RatingCriteriaPageTests");
        logger.info("Creating object for RatingCriteriaPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "RatingCriteriaPageData")
    public void  testBusinessClassRatingCriteria(Map<String, String> map) throws InterruptedException, ParseException {
        /***
         this test Brokers Business Class criteria
         story - N2020-30438
         **/

        logger.info("verifying :: business class rating criteria");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"), map.get("applicantName"), map.get("website"));
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions.clickRatingCriteriaButton(DriverManager.getDriver());
        assert ratingCriteriaPageActions.ratingCriteriaTitle(DriverManager.getDriver()).isDisplayed();
        dashboardPageActions.clickExitRatingCriteria(DriverManager.getDriver());
        dashboardPageActions.clickFilterList(DriverManager.getDriver());
        dashboardPageActions.clickSubmissionFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(),"Active");
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions.clickBusinessClassDropdown(DriverManager.getDriver());
        ratingCriteriaPageActions.inputValueToRatingCriteria(DriverManager.getDriver(), map.get("numberOfResidentialUnits"), map.get("totalCommercialSquareFeet"));
        ratingCriteriaPageActions.clickRatingCriteriaContinueButton(DriverManager.getDriver());




    }

}
