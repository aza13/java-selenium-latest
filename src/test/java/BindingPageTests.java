import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.ClickHelper;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.*;
import utils.dataProvider.TestDataProvider;
import utils.dbConnector.DatabaseConnector;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.DatabaseQueries.GET_SUBMISSION_ID_WITH_QUOTE_ID;

public class BindingPageTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;
    private UnderwritingQuestionsPageActions underwritingQuestionsPageActions;
    private QuoteListPageActions quoteListPageActions;
    private DatabaseConnector databaseConnector;
    private BindingPageActions bindingPageActions;


    public BindingPageTests() {
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("BindingPageTests");
        logger.info("Creating object for BindingPageTests :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
        databaseConnector = new DatabaseConnector();
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaActions();
        underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        bindingPageActions = PageObjectManager.getBindingPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testVerifyQuoteBinding(Map<String, String> map) throws InterruptedException, SQLException {
        /*****************************************************************
         this test verifies quote option Binding and Subjectivity
         story - N2020-33007, 23922,32926, 32930
         @author - Venkat Kottapalli, Sheetal
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testVerifyQuoteBinding");
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
                    assert bindingPageActions.isPreSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isPostSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isPreSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isPostSubjectivitiesDisplayed(DriverManager.getDriver());
                    assert bindingPageActions.isMessageToUnderWriterDisplayed(DriverManager.getDriver());
                    bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
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
                    assert quoteStatus.contentEquals("Order Placed");
                    dashboardPageActions.clickFirstAvailableContinueButton(DriverManager.getDriver());
                    assert bindingPageActions.isBindingTabSelected(DriverManager.getDriver());
                    bindingPageActions.VerifyQuoteHeaderInformationInBindingPage(DriverManager.getDriver(), newInsuredName, ConstantVariable.PRODUCT);
                    bindingPageActions.clickPolicyCardExpandIconInBindingPage(DriverManager.getDriver());
                    if(!bindingPageActions.binderSubmitButton(DriverManager.getDriver()).isEnabled()){
                        bindingPageActions.enterTextToFirstSubjectivity(DriverManager.getDriver());
                        String enterText = FakeDataHelper.fullName();
                        bindingPageActions.EnterMessageToPreSubjectivitiesUnderWriterTextBox(DriverManager.getDriver(),enterText);
                        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
                        bindingPageActions.EnterMessageToPostSubjectivitiesUnderWriterTextBox(DriverManager.getDriver(),enterText);
                    }
                    bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
                    bindingPageActions.clickConfirmationContinueButton(DriverManager.getDriver());
                    assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                }
            } else {
                Assert.fail("Confirm and quote button is disabled for some reason");
            }
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "BindingPageData")
    public void testRejectSubjectivity(Map<String, String> map) throws InterruptedException {
        /*****************************************************************
         this test verifies Subjectivity status is Rejected, Accepted and Waived
         story - N2020-32716, 32708
         @author -  Sheetal
         ******************************************************************/

        logger.info("Executing the testVerifyQuoteBinding from BindingPageTests class :: testRejectSubjectivity");//dashboardPageActions.clickNewQuote(DriverManager.getDriver());
       // dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("submissionID1"));
        String submissionID1 = "12980782", submissionID2 ="12980838";
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionID1);
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        bindingPageActions.verifyWaivedStatus(DriverManager.getDriver());
        bindingPageActions.clickPostSubjectivitiesExpandButton(DriverManager.getDriver());
        bindingPageActions.verifyRejectedStatus(DriverManager.getDriver());
        bindingPageActions.clickOnExitDashboard(DriverManager.getDriver());
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), submissionID2);
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        bindingPageActions.verifyAcceptedStatus(DriverManager.getDriver());
    }
}
