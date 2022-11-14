package workflows;

import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.RatingCriteriaPageActions;
import utils.fileReader.ConfigDataReader;

import java.util.Map;

public class FillApplicantDetails {

    private static final Logger logger = Logger.getLogger(FillApplicantDetails.class);

    private FillApplicantDetails(){}

    public static void fillApplicantDetails(WebDriver driver, Map<String, String> map) throws InterruptedException {
        logger.info("fill applicant details, Business class :: fillApplicantDetails");
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) {
            if (ConfigDataReader.getInstance().getProperty("product").equals("Catholic Mutual - NetGuard® Plus")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, map.get("businessClass2"));
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterNetWorth(driver, map.get("netWorth"));
            }else if(ConfigDataReader.getInstance().getProperty("product").contains("Ophthalmic") || ConfigDataReader.getInstance().getProperty("product").contains("AAO")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, map.get("businessClass3"));
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterNoOfPhysicians(driver, map.get("physiciansCount"));
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(driver, map.get("revenue"), map.get("records"));
            }else {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, map.get("businessClass"));
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(driver, map.get("revenue"), map.get("records"));
            }
        }
        WaitHelper.pause(3000);
    }
}
