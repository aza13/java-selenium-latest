package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.SnapDashboardPageObjects.*;
import static java.lang.Thread.sleep;

public class SnapDashboardPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapDashboardPageActions.class);

    public void clickOnBrokerMenu(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, brokerMenu);
            ClickHelper.clickElement(driver, brokerMenu);
        }catch (Exception e){
            testLogger.fail("Failed to click on the Broker Menu :: clickOnBrokerMenu" + e);
            logger.error("Failed to click on the Broker Menu :: clickOnBrokerMenu");
            throw (e);
        }
    }

    public SnapBrokerPageActions clickOnCreateBroker(WebDriver driver) throws InterruptedException {
        try{
            WaitHelper.waitForElementVisibility(driver, createBroker);
            sleep(5000);
            ClickHelper.clickElement(driver, createBroker);
            return PageObjectManager.getSnapBrokerPageActions();
        }catch (Exception e){
            testLogger.fail("Failed to click on the Broker Menu :: clickOnBrokerMenu" + e);
            logger.error("Failed to click on the Broker Menu :: clickOnBrokerMenu");
            throw (e);
        }
    }

    public void clickPolicyMenu(WebDriver driver) throws InterruptedException {
        try{
            WaitHelper.waitForElementVisibility(driver, policyMenu);
            sleep(5000);
            ClickHelper.clickElement(driver, policyMenu);
        }catch (Exception e){
            testLogger.fail("Failed to click on the Broker Menu :: clickPolicyMenu" + e);
            logger.error("Failed to click on the Broker Menu :: clickPolicyMenu");
            throw (e);
        }
    }

        public SnapPolicyPageActions clickPolicyDashboard(WebDriver driver) throws InterruptedException {
            try{
                WaitHelper.waitForElementVisibility(driver, policyDashboard);
                sleep(5000);
                ClickHelper.clickElement(driver, policyDashboard);
                return PageObjectManager.getSnapPolicyPageActions();
            }catch (Exception e){
                testLogger.fail("Failed to click on the Broker Menu :: clickPolicyDashboard" + e);
                logger.error("Failed to click on the Broker Menu :: clickPolicyDashboard");
                throw (e);
            }

    }


}
