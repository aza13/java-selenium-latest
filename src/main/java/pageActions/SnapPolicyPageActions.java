package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.SnapPolicyPageObjects.*;

public class SnapPolicyPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapDashboardPageActions.class);

    public void enterPolicyNumberInSearchBox(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, policySearch);
            TextHelper.enterText(driver, policySearch, "H20");
        }catch (Exception e){
            testLogger.fail("Failed to click on the Broker Menu :: clickOnBrokerMenu" + e);
            logger.error("Failed to click on the Broker Menu :: clickOnBrokerMenu");
            throw (e);
        }
    }

    public void clickPolicySearchButton(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, policySearchButton);
            ClickHelper.clickElement(driver, policySearchButton);
        }catch (Exception e){
            testLogger.fail("Failed to click on the Broker Menu :: policySearchButton" + e);
            logger.error("Failed to click on the Broker Menu :: policySearchButton");
            throw (e);
        }
    }


}
