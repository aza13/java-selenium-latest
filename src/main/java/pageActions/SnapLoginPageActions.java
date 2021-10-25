package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.SnapLoginPageObjects.*;

public class SnapLoginPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(SnapLoginPageActions.class);

    public void clickLetsGoButton(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, letsGoButton);
            ClickHelper.clickElement(driver, letsGoButton);
        }catch (Exception e){
            testLogger.fail("Failed to click on the lets go button :: clickLetsGoButton" + e);
            logger.error("Failed to click on the lets go button :: clickLetsGoButton");
            throw (e);
        }
    }

    public void enterUserName(WebDriver driver, String user_name){
        try{
            WaitHelper.waitForElementVisibility(driver, msUserName);
            TextHelper.enterText(driver, msUserName, user_name);
        }catch (Exception e){
            testLogger.fail("Failed to enter user name :: enterUserName" + e);
            logger.error("Failed to enter user name :: enterUserName");
            throw (e);
        }
    }

    public void clickSignInNextButton(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibility(driver, signInNextButton);
            ClickHelper.clickElement(driver, signInNextButton);
        }catch (Exception e){
            testLogger.fail("Failed to click on sign in button :: clickSignInNextButton" + e);
            logger.error("Failed to click on sign in button :: clickSignInNextButton");
            throw (e);
        }

    }

    public void enterUserId(WebDriver driver, String user_id){
        WaitHelper.waitForElementVisibility(driver, userId);
        TextHelper.enterText(driver, userId, user_id);
    }

    public void enterPassword(WebDriver driver, String pwd){
        WaitHelper.waitForElementVisibility(driver, password);
        TextHelper.enterText(driver, password, pwd);
    }

    public void clickOktaSignIn(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, oktaSignIn);
        ClickHelper.clickElement(driver, oktaSignIn);
    }

    public void enterAnswer(WebDriver driver, String answer){
        WaitHelper.waitForElementVisibility(driver, answerField);
        TextHelper.enterText(driver, answerField, answer);
    }

    public void clickVerifyButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, verifyButton);
        ClickHelper.clickElement(driver, verifyButton);
    }

    public void clickYesButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, yesButton);
        ClickHelper.clickElement(driver, yesButton);
    }

    public SnapDashboardPageActions enterLoginCredentials(WebDriver driver, String user, String pwd, String answer){
        clickLetsGoButton(driver);
        enterUserName(driver, user);
        clickSignInNextButton(driver);
        enterUserId(driver, user);
        enterPassword(driver, pwd);
        clickOktaSignIn(driver);
        enterAnswer(driver, answer);
        clickVerifyButton(driver);
        clickYesButton(driver);
        return PageObjectManager.getSnapDashboardPageActions();
    }

}
