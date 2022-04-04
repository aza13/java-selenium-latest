import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.DashboardPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.dataProvider.TestDataProvider;

import java.util.Map;

public class UnderwritingQuestionsPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageTests.class);

    private DashboardPageActions dashboardPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("UnderwritingQuestionsPageTests");
        logger.info("Creating object for UnderwritingQuestionsPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();

    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "UnderwritingQuestionsPageData")
    public void  testBrokerAnswersUnderWriterQuestions(Map<String, String> map) throws InterruptedException {
        /***
         this test Brokers can answers all underwriter questions
         story - N2020-28623-QAT-165
         @author - Azamat Uulu
         **/

        logger.info("verifying :: Under Writing Questions");

        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("reffNumber").replaceAll("^\"|\"$", ""));
        dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
        underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(DriverManager.getDriver());

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
}
