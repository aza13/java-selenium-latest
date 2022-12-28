import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import helper.ScrollHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageActions.BindingPageActions;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.dataProvider.JsonDataProvider;
import utils.fileReader.ConfigDataReader;
import utils.fileReader.TextFileReader;

import java.awt.*;
import java.util.List;
import java.util.Locale;
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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "ClearancesPageData")
    public void testClearancesSubmissionFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli
         **/
        logger.info("verifying submission clearance results :: testClearancesSubmissionFunctionality");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage,  jsonObject.get("name").toString(),  jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(jsonObject.get("functionality"), "submit")) {
                    int index = FakeDataHelper.getRandomNumber(0, count-1);
                    String name = insuranceCards.get(index).getText().toLowerCase(Locale.ROOT);
                    if (name.contains(jsonObject.get("name").toString())) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), index+1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), jsonObject.get("clearanceText").toString());
                            insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
                            assert dashboardPageActions.clickQuotesTab(DriverManager.getDriver()).isDisplayed();
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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "ClearancesPageData")
    public void testCancelClearancesFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies whether user can proceed for submission creation based on clearances results
         story - N2020-28325, 28326
         @author - Venkat Kottapalli
         **/
        logger.info("verifying submission clearance results :: testCancelClearancesFunctionality");
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage,  jsonObject.get("name").toString(),  jsonObject.get("website").toString());
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        List<WebElement> insuranceCards = insuredPageActions.getAllInsuredNames(DriverManager.getDriver());
        int count = insuranceCards.size();
        if (count > 0) {
            if (Objects.equals(jsonObject.get("functionality"), "cancel")) {
                    int index = FakeDataHelper.getRandomNumber(0, count-1);
                    String name = insuranceCards.get(index).getText().toLowerCase(Locale.ROOT);
                    if (name.contains(jsonObject.get("name").toString())) {
                        insuredPageActions.selectInsuredCardWithIndex(DriverManager.getDriver(), index + 1);
                        if (insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())) {
                            insuredPageActions.enterClearanceText(DriverManager.getDriver(), jsonObject.get("clearanceText").toString());
                            insuredPageActions.clickClearanceCancelQuoteButton(DriverManager.getDriver());
                            assert dashboardPageActions.clickQuotesTab(DriverManager.getDriver()).isDisplayed();
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

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "ClearancesPageData")
    public void testClearancesReviewFunctionality(JSONObject jsonObject) throws InterruptedException {
        /***
         this test verifies clearances review
         story - N2020-32093, 34636, 34137
         @author - Venkat Kottapalli
         **/
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        String newInsuredName = FakeDataHelper.fullName();
        String newInsuredWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, newInsuredName,newInsuredWebsite);
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
        ratingCriteriaPageActions = insuredPageActions.clickContinueInsuredFormButton(DriverManager.getDriver());
        assert ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(DriverManager.getDriver());
        ratingCriteriaPageActions.ratingCriteriaExitButton(DriverManager.getDriver()).click();
        dashboardPageActions.clickNewQuote(DriverManager.getDriver());
        dashboardPageActions.createNewQuote(DriverManager.getDriver(), coverage, newInsuredName,newInsuredWebsite);
        insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
        insuredPageActions.selectInsuredCard(DriverManager.getDriver(), newInsuredName);
        if(insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())){
            BindingPageActions bindingPageActions = PageObjectManager.getBindingPageActions();
            bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.PDF_2MB_DOC_FILE_PATH);
            assert insuredPageActions.isFileSizeLagerThan2MbTextDisplayed(DriverManager.getDriver());
            assert !insuredPageActions.isClearanceSubmitButtonEnabled(DriverManager.getDriver());
            bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
            bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.INVALID_FILE_TYPE);
            assert bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
            assert !insuredPageActions.isClearanceSubmitButtonEnabled(DriverManager.getDriver());
            bindingPageActions.clickFileDeleteIcon(DriverManager.getDriver());
            bindingPageActions.uploadFileUsingJavaScript(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
            assert !bindingPageActions.isFileTypeWarningDisplayed2(DriverManager.getDriver());
        }else if (insuredPageActions.duplicateSubmissionDialog(DriverManager.getDriver())) {
            insuredPageActions.clickDuplicateCancelButton(DriverManager.getDriver());
            logger.info("No clearance associated with insured, Rating criteria page displayed");
            assert true;
        }else{
            insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
        }
    }

    @Test(dataProvider = "jsonDataReader", dataProviderClass = JsonDataProvider.class, description = "ClearancesPageData")
    public void testRenewalPolicyClearanceValidation(JSONObject jsonObject) throws InterruptedException, AWTException {
        /***
         this test verifies resolving clearances for renewal policy
         story - N2020-34674
         @author - Venkat Kottapalli
         ***/
        dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
        dashboardPageActions.clickPoliciesFilterList(DriverManager.getDriver());
        dashboardPageActions.clickPolicyFilterByStatus(DriverManager.getDriver());
        dashboardPageActions.selectStatusInFilter(DriverManager.getDriver(), ConstantVariable.ACTIVE_STRING);
        dashboardPageActions.clickApplyFiltersButton(DriverManager.getDriver());
        for(int n=0; n<=10; n++){
            WebElement element = dashboardPageActions.getPolicyRenewButton(DriverManager.getDriver());
            if(element!=null){
                String policyNumber = element.findElement(By.xpath("((//button[text()='Renew'])[1]/parent::div/parent::div/preceding-sibling::div)[2]//p")).getText().trim();
                String applicantName = element.findElement(By.xpath("//parent::div/parent::div/preceding-sibling::div//div[@data-qa='legalname']")).getText().trim();
                String product = element.findElement(By.xpath("((//button[text()='Renew'])[1]/parent::div/parent::div/preceding-sibling::div)[3]//p")).getText().trim();
                dashboardPageActions.clickNewQuote(DriverManager.getDriver());
                dashboardPageActions.createNewQuote(DriverManager.getDriver(), ConfigDataReader.getInstance().getProperty("coverage"), applicantName, "https://www.master.com/");
                InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(DriverManager.getDriver());
                insuredPageActions.clickContinueInsuredButton(DriverManager.getDriver());
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
                insuredPageActions.clickCancelInsuredSearch(DriverManager.getDriver());
                dashboardPageActions.enterTextToSearchBox(DriverManager.getDriver(), policyNumber);
                dashboardPageActions.clickMyPoliciesTab(DriverManager.getDriver());
                WebElement renewButton = dashboardPageActions.getPolicyRenewButton(DriverManager.getDriver());
                renewButton.click();
                if(insuredPageActions.isClearanceDialogModalDisplayed(DriverManager.getDriver())){
                    BindingPageActions bindingPageActions = PageObjectManager.getBindingPageActions();
                    bindingPageActions.clickPreSubjSelectFilesButton(DriverManager.getDriver());
                    bindingPageActions.uploadFile(DriverManager.getDriver(), ConstantVariable.PDF_DOC_FILE_PATH);
                }
                insuredPageActions.clickClearanceSubmitButton(DriverManager.getDriver());
            }else{
                ScrollHelper.scrollToBottom(DriverManager.getDriver());
            }
        }
    }
}
