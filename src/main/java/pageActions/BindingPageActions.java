package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageObjects.BindingPageObjects.*;
import static pageObjects.LoginPageObjects.forgotEmailTextField;
import static pageObjects.QuoteListPageObjects.quoteExpiry;

public class BindingPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageActions.class);

    public void clickOnExitDashboard(WebDriver driver){
        WaitHelper.waitForElementClickable(driver, exitToDashboard);
        ClickHelper.clickElement(driver, exitToDashboard);
    }

    public boolean isBindingTabSelected(WebDriver driver){

        return ClickHelper.isElementExist(driver, bindingTabSelected);
    }

    public void VerifyQuoteHeaderInformationInBindingPage(WebDriver driver, String applicant, String product){

        List<WebElement> elements = driver.findElements(quoteHeaderInformation);
        assert elements.size() == 3;
        assert elements.get(0).getText().equals(applicant);
        assert elements.get(1).getText().equals(product);
        assert elements.get(2).getText().contains("Proposed Policy Period:");
    }

    public void clickPolicyCardExpandIconInBindingPage(WebDriver driver){

        ClickHelper.clickElement(driver, policyExpandMoreIcon);
    }

    public boolean isPreSubjectivitiesDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, preSubjectivities);
    }

    public boolean isPostSubjectivitiesDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, postSubjectivities);
    }

    public boolean isMessageToUnderWriterDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, messageToUnderWriter);
    }

    public void EnterMessageToPreSubjectivitiesUnderWriterTextBox(WebDriver driver, String text) throws InterruptedException{
        WaitHelper.pause(10000);
        TextHelper.enterText(driver, messageToPreSubjectivitiesUnderWriterTextBox, text);
    }

    public void clickPostSubjectivitiesExpandButton(WebDriver driver)throws InterruptedException{
        WaitHelper.pause(10000);
        ClickHelper.clickElement(driver, postSubjectivitiesExpandButton);
    }

    public void EnterMessageToPostSubjectivitiesUnderWriterTextBox(WebDriver driver, String text) throws InterruptedException{
        WaitHelper.pause(10000);
        TextHelper.enterText(driver, messageToPostSubjectivitiesUnderWriterTextBox, text);
    }

    public boolean verifyRejectedStatus(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, rejectedStatus);
    }
}
