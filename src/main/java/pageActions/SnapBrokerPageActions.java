package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.SnapBrokerPageObjects.*;

public class SnapBrokerPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapBrokerPageActions.class);

    public void enterBrokerName(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, brokerName);
            String broker_name = PageObjectManager.getFakeDataHelper().firstName();
            TextHelper.enterText(driver, brokerName, broker_name);
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Broker name :: enterBrokerName" + e);
            logger.error("Failed to enter on the Broker name :: enterBrokerName");
            throw (e);
        }
    }

    public void enterBrokerEmail(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, brokerEmail);
            String broker_email = PageObjectManager.getFakeDataHelper().email();
            TextHelper.enterText(driver, brokerEmail, broker_email);
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Broker email :: enterBrokerEmail" + e);
            logger.error("Failed to enter on the Broker email :: enterBrokerEmail");
            throw (e);
        }
    }

    public void enterInfContactNumber(WebDriver driver, int n){
        try{
            WaitHelper.waitForElementVisibility(driver, infContactNumber);
            int num = PageObjectManager.getFakeDataHelper().randomInt(n);
            TextHelper.enterText(driver, infContactNumber, String.valueOf(num));
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Inf contact number :: enterInfContactNumber" + e);
            logger.error("Failed to enter on the Inf contact number :: enterInfContactNumber");
            throw (e);
        }
    }

    public void enterInfContactId(WebDriver driver, int n){
        try{
            WaitHelper.waitForElementVisibility(driver, infContactId);
            int num = PageObjectManager.getFakeDataHelper().randomInt(n);
            TextHelper.enterText(driver, infContactId, String.valueOf(num));
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Inf contact number :: enterInfContactId" + e);
            logger.error("Failed to enter on the Inf contact number :: enterInfContactId");
            throw (e);
        }
    }


    public void enterPhoneNumber(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, phoneNumber);
            String num = PageObjectManager.getFakeDataHelper().phoneNumber();
            TextHelper.enterTextUsingJS(driver, phoneNumber, String.valueOf(num));
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Inf contact number :: enterPhoneNum" + e);
            logger.error("Failed to enter on the Inf contact number :: enterPhoneNum");
            throw (e);
        }
    }

    public void searchAgencyOffice(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, agencyOffice);
            TextHelper.enterText(driver, agencyOffice, "agency");
            WaitHelper.waitForElementVisibility(driver, agencyFirstOption);
            ClickHelper.clickElement(driver, agencyFirstOption);
            ClickHelper.clickElement(driver, submitButton);
//            ClickHelper.clickElement(driver, agencySearchButton);
        }catch (Exception e){
            testLogger.fail("Failed to enter on the Inf contact number :: searchAgencyOffice" + e);
            logger.error("Failed to enter on the Inf contact number :: searchAgencyOffice");
            throw (e);
        }
    }

    public void fillBrokerContactInfo(WebDriver driver){
        enterBrokerName(driver);
        enterBrokerEmail(driver);
        enterInfContactNumber(driver, 12);
        enterInfContactId(driver, 12);
        enterPhoneNumber(driver);
        searchAgencyOffice(driver);
    }

}
