import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.BindingPageActions;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.TestDataProvider;
import utils.fileReader.TextFileReader;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SubmissionClearancesTests extends BaseTest {

    private static final Logger logger = Logger.getLogger(SubmissionClearancesTests.class);
    private DashboardPageActions dashboardPageActions;
    private RatingCriteriaPageActions ratingCriteriaPageActions;

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetUp() {
        classLogger = extentReport.createTest("SubmissionClearancesTests");
        logger.info("Creating object for LoginPageTest :: beforeClassSetUp");
        dashboardPageActions = PageObjectManager.getDashboardPageActions();
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testClearancesSubmissionFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli
         **/

        logger.info("verifying submission clearance results :: testClearancesSubmissionFunctionality");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT,  map.get("name"),  map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(map.get("functionality"), "submit")) {
                    int index = FakeDataHelper.getRandomNumber(0, count-1);
                    String name = insuranceCards.get(index).getText().toLowerCase(Locale.ROOT);
                    if (name.contains(map.get("name"))) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), index+1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), map.get("clearanceText"));
                            insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
                            assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                        } else if (insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver())) {
                            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
                            logger.info("No clearance associated with insured, Rating criteria page displayed");
                            assert true;
                        } else if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
                            logger.info("No clearance associated with insured, Rating criteria page displayed");
                            assert ratingCriteriaPageActions.ratingCriteriaExitButton(DriverManager.getDriver()).isDisplayed();
                            assert true;
                        }
                    }

            }  else {
                logger.info("No insureds displayed, skipping the test");
                throw new SkipException("No insureds displayed, skipping the test");
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testCancelClearancesFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli
         **/
        logger.info("verifying submission clearance results :: testCancelClearancesFunctionality");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT,  map.get("name"),  map.get("website"));
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(map.get("functionality"), "cancel")) {
                    int index = FakeDataHelper.getRandomNumber(0, count-1);
                    String name = insuranceCards.get(index).getText().toLowerCase(Locale.ROOT);
                    if (name.contains(map.get("name"))) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), index + 1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), map.get("clearanceText"));
                            insuredPageActions.clickClearanceCancelQuoteButton(DriverManager.getDriver());
                            assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                        } else if (insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver())) {
                            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
                            logger.info("Duplicate submission was displayed");
                            assert true;
                        } else if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
                            logger.info("No clearance associated with insured, Rating criteria page displayed");
                            assert ratingCriteriaPageActions.ratingCriteriaExitButton(DriverManager.getDriver()).isDisplayed();
                            assert true;
                        }
                    }
            } else {
                logger.info("No insureds displayed, skipping the test");
                throw new SkipException("No insureds displayed, skipping the test");
            }
        }
    }


    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testClearancesReviewFunctionality(Map<String, String> map) throws InterruptedException, AWTException {
        /***
         this test verifies clearances review
         story - N2020-32093
         @author - Venkat Kottapalli
         **/
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT_2, newInsuredName,newInsuredWebsite);
        TextFileReader.writeDataToTextFile(ConstantVariable.INSURED_DATA_FILEPATH, newInsuredName+";"+newInsuredWebsite);
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
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
        ratingCriteriaPageActions.ratingCriteriaExitButton(DriverManager.getDriver()).click();
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName,newInsuredWebsite);
        dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.selectInsuredCard(DriverManager.getDriver(), newInsuredName);
        if(insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())){
            BindingPageActions bindingPageActions = PageObjectManager.getBindingPageActions();
            bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
            bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
        }
        insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());

    }

}
