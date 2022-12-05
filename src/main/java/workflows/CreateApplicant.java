package workflows;

import base.PageObjectManager;
import constants.ConstantVariable;
import helper.FakeDataHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import utils.fileReader.TextFileReader;
import java.util.Arrays;
import java.util.List;

public class CreateApplicant {

    private static final Logger logger = Logger.getLogger(CreateApplicant.class);
    static InsuredPageActions insuredPageActions;
    static String newApplicantName, newApplicantWebsite, email, physicalAddress, phyCity, phyZipcode;
    private CreateApplicant(){}

    public static String createApplicant(WebDriver driver, String product) throws InterruptedException {
        logger.info("verifying creating new quote creation :: testCreateInsured");
        DashboardPageActions dashboardPageActions = PageObjectManager.getDashboardPageActions();
        dashboardPageActions.clickNewQuote(driver);
        newApplicantName = FakeDataHelper.fullName();
        newApplicantWebsite = FakeDataHelper.website();
        dashboardPageActions.createNewQuote(driver, product, newApplicantName,newApplicantWebsite);
        TextFileReader.writeDataToTextFile(ConstantVariable.INSURED_DATA_FILEPATH, newApplicantName+";"+newApplicantWebsite);
        insuredPageActions = dashboardPageActions.clickContinueButton(driver);
        if (!insuredPageActions.isCreateNewInsuredTextDisplayed(driver)){
            insuredPageActions.clickNewInsuredButton(driver);
        }
        email = insuredPageActions.enterEmailAddress(driver);
        insuredPageActions.enterInsuredPhoneNumber(driver);
        physicalAddress = insuredPageActions.enterPhysicalAddress(driver);
        phyCity = insuredPageActions.enterPhyCity(driver);
        phyZipcode = insuredPageActions.enterPhyZipcode(driver);
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

    public static List<String> getApplicantDetails(WebDriver driver) throws InterruptedException{

        List<String> storeApplicantDetails = Arrays.asList(newApplicantName,newApplicantWebsite,email,physicalAddress,phyCity,phyZipcode);
        return storeApplicantDetails;
    }
}
