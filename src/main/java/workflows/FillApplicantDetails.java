package workflows;

import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import pageActions.RatingCriteriaPageActions;

public class FillApplicantDetails {

    private static final Logger logger = Logger.getLogger(FillApplicantDetails.class);

    private FillApplicantDetails(){}

    public static void fillApplicantDetails(WebDriver driver, JSONObject jsonObject, String coverage) throws InterruptedException {
        logger.info("fill applicant details, Business class :: fillApplicantDetails");
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) {
            if (coverage.contains("NetGuard® SELECT")) {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, jsonObject.get("businessClass2").toString());
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterNetWorth(driver, jsonObject.get("netWorth").toString());
            }else if(coverage.contains("Ophthalmic") || coverage.contains("AAO")){
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, jsonObject.get("businessClass3").toString());
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterNoOfPhysicians(driver, jsonObject.get("physiciansCount").toString());
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(driver, jsonObject.get("revenue").toString(), jsonObject.get("records").toString());
            }else {
                ratingCriteriaPageActions.enterTextToBusinessClassDropDown(driver, jsonObject.get("businessClass").toString());
                ratingCriteriaPageActions.clickBusinessClassOption(driver);
                ratingCriteriaPageActions.enterRatingCriteriaRevenueAndRecords(driver, jsonObject.get("revenue").toString(), jsonObject.get("records").toString());
            }
        }
        WaitHelper.pause(3000);
    }
}
