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
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.TestDataProvider;
import utils.fileReader.TextFileReader;

import java.util.List;
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

    public void createInsured() throws InterruptedException {
        /***
         this method creates a new  insured and writes it to text file
         story - N2020-28293
         **/
        logger.info("verifying creating new quote creation :: testCreateInsured");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_OFFICE_ID);
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), ConstantVariable.PRODUCT, newInsuredName,newInsuredWebsite);
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
    }



    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData", priority = 1)
    public void testClearancesSubmissionFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli

         **/
        logger.info("verifying submission clearance results :: testClearancesSubmissionFunctionality");
        createInsured();
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String[] insuredData = TextFileReader.readDataFromTextFile(ConstantVariable.INSURED_DATA_FILEPATH).split(";");
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"),  insuredData[0],  insuredData[1]);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(map.get("functionality"), "submit")) {
                for (int i = 0; i <= count; i++) {
                    String name = insuranceCards.get(i).getText();
                    if (name.contains(insuredData[0])) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), i+1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), map.get("clearanceText"));
                            insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
                            assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                            break;
                        } else if (insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver())) {
                            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
                            throw new SkipException("Duplicate submission was displayed");
                        } else if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
                            logger.info("Rating Criteria Page Displayed");
                            assert ratingCriteriaPageActions.ratingCriteriaExitButton(DriverManager.getDriver()).isDisplayed();
                            throw new SkipException("No clearance associated with insured, Rating criteria page displayed");
                        }
                    }
                }
            }  else {
                logger.info("No insureds displayed, skipping the test");
                throw new SkipException("No insureds displayed, skipping the test");
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData", priority = 2)
    public void testCancelClearancesFunctionality(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli
         **/
        logger.info("verifying submission clearance results :: testCancelClearancesFunctionality");
        createInsured();
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String[] insuredData = TextFileReader.readDataFromTextFile(ConstantVariable.INSURED_DATA_FILEPATH).split(";");
        dashboardPageActions.CreateNewQuote(DriverManager.getDriver(), map.get("product"),  insuredData[0],  insuredData[1]);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(map.get("functionality"), "cancel")) {
                for (int i = 0; i <= count; i++) {
                    String name = insuranceCards.get(i).getText();
                    if (name.contains(insuredData[0])) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), i+1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), map.get("clearanceText"));
                            insuredPageActions.clickClearanceCancelQuoteButton(DriverManager.getDriver());
                            assert dashboardPageActions.myQuotesTab(DriverManager.getDriver()).isDisplayed();
                            break;
                        } else if (insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver())) {
                            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
                            throw new SkipException("Duplicate submission was displayed");
                        } else if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver())) {
                            logger.info("Rating Criteria Displayed");
                            throw new SkipException("No clearance associated with insured, Rating criteria page displayed");
                        }
                    }
                }
            } else {
                logger.info("No insureds displayed, skipping the test");
                throw new SkipException("No insureds displayed, skipping the test");
            }
        }
    }

    @Test(dataProvider = "ask-me", dataProviderClass = TestDataProvider.class, description = "InsuredPageData")
    public void testSubmissionClearanceComplete(Map<String, String> map) throws InterruptedException {
        /***
         this test verifies Submissions with no clearances can transition to the quote
         story - N2020-28329
         @author - Venkat Kottapalli
         **/
        logger.info("verifying duplicate submissions :: testCheckDuplicateSubmission");
        dashboardPageActions.clickProfileSettings(DriverManager.getDriver());
        dashboardPageActions.enterBrokerId(DriverManager.getDriver(), ConstantVariable.BROKER_ID);
        dashboardPageActions.enterAgencyId(DriverManager.getDriver(), ConstantVariable.AGENT_ID);
        dashboardPageActions.enterAgencyOfficeId(DriverManager.getDriver(), ConstantVariable.AGENT_OFFICE_ID);
        dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), map.get("applicantName"));
        dashboardPageActions.clickQuoteCardContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
    }

}
