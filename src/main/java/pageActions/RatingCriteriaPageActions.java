package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageObjects.RatingCriteriaPageObjects.ratingCriteriaExitButton;
import static pageObjects.RatingCriteriaPageObjects.ratingCriteriaHeader;

public class RatingCriteriaPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(RatingCriteriaPageActions.class);

    public boolean isRatingCriteriaPageDisplayed(WebDriver driver){

       return ClickHelper.isElementExist(driver, ratingCriteriaHeader);

    }

    public WebElement ratingCriteriaExitButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, ratingCriteriaExitButton);
        return driver.findElement(ratingCriteriaExitButton);
    }
}
