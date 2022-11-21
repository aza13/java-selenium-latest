package workflows;

import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.fileReader.ConfigDataReader;
import utils.fileReader.TextFileReader;

public class CreateApplicant {

    private static final Logger logger = Logger.getLogger(CreateApplicant.class);

    private CreateApplicant(){}

    public static String createApplicant(WebDriver driver, String product) throws InterruptedException {
        logger.info("verifying creating new quote creation :: testCreateInsured");
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        dashboardPageActions.clickNewQuote(driver);
        String newApplicantName = FakeDataHelper.fullName();
        String newApplicantWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(driver, ConfigDataReader.getInstance().getProperty(product), newApplicantName,newApplicantWebsite);
        TextFileReader.writeDataToTextFile(ConstantVariable.INSURED_DATA_FILEPATH, newApplicantName+";"+newApplicantWebsite);
        InsuredPageActions insuredPageActions = dashboardPageActions.clickContinueButton(driver);
        if (!insuredPageActions.isCreateNewInsuredTextDisplayed(driver)){
            insuredPageActions.clickNewInsuredButton(driver);
        }
        insuredPageActions.enterEmailAddress(driver);
        insuredPageActions.enterInsuredPhoneNumber(driver);
        insuredPageActions.enterPhysicalAddress(driver);
        insuredPageActions.enterPhyCity(driver);
        insuredPageActions.enterPhyZipcode(driver);
        insuredPageActions.selectPhyState(driver);
        insuredPageActions.clickSameAsPhyAddress(driver);
        insuredPageActions.clickContinueInsuredFormButton(driver);
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if(ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) {
            return newApplicantName;
        }else{
            return null;
        }
    }
}
