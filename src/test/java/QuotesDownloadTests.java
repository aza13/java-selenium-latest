import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.TestDataProvider;

import java.util.Map;

public class QuotesDownloadTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuotesDownloadTests.class);
    private DashboardPageActions dashboardPageActions;
    private QuoteListPageActions quotesPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("QuotesPageTests");
        logger.info("Creating object for LoginPageTest :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        quotesPageActions = PageObjectManager.getQuoteListPageActions() ;
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "QuotesPageData")
    public void testBrokerDownloadConfirmedQuote(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies brokers can download confirmed quote validation
         story - N2020-28652
         @author - Azamat Uulu
         **/
        logger.info("verifying brokers can download confirmed quote :: testBrokerDownloadConfirmedQuote");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), map.get("brokerId"));
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), map.get("agentId"));
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), map.get("agencyOfficeId"));

        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("reffNumber").replaceAll("^\"|\"$", ""));
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());

        quotesPageActions.clickQuotesTab(DriverManager.getDriver());
        String text = quotesPageActions.getQuotesWelcomeText(DriverManager.getDriver());
        assert text.contentEquals("Quote List");

        quotesPageActions.clickPDFFileDownload(DriverManager.getDriver());


    }
}