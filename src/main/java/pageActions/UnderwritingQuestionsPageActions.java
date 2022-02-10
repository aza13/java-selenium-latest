package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.UnderwritingQuestionsPageObjects.*;


public class UnderwritingQuestionsPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageActions.class);

    public boolean isUnderwritingQuestionsPageDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, underwritingQuestionsHeader);
       return ClickHelper.isElementExist(driver, underwritingQuestionsHeader);
    }


}
