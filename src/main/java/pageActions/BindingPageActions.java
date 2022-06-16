package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import static pageObjects.BindingPageObjects.*;
import static pageObjects.QuoteListPageObjects.deleteIconLocator;

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
        WaitHelper.pause(5000);
        return ClickHelper.isElementExist(driver, preSubjectivities);
    }

    public boolean isPostSubjectivitiesDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(5000);
        return ClickHelper.isElementExist(driver, postSubjectivities);
    }

    public void uploadFile(WebDriver driver) throws InterruptedException, AWTException {
        WaitHelper.pause(5000);
        ClickHelper.clickElement(driver, preSubjSelectFilesButton);
        WaitHelper.pause(3000);
        ClickHelper.clickElement(driver, clickAndDragLink);
        WaitHelper.pause(3000);
        // creating object of Robot class
        Robot rb = new Robot();

        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\InsuredData.txt";

        // copying File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WaitHelper.pause(3000);
    }

    public void EnterMessageToPreSubjectivitiesUnderWriterTextBox(WebDriver driver){

        TextHelper.enterText(driver, firstMessageToUWTextArea, "Sample text automation verification");
    }

    public WebElement binderSubmitButton(WebDriver driver){

        return driver.findElement(enabledSubmitButton);
    }

    public void clickConfirmationContinueButton(WebDriver driver){
        if(ClickHelper.isElementExist(driver, confirmationDialog)){
            ClickHelper.clickElement(driver, submitConfirmationButton);
        }
    }

    public boolean isMessageToUnderWriterDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, messageToUnderWriter);
    }

    public void clickPostSubjectivitiesExpandButton(WebDriver driver)throws InterruptedException{
        WaitHelper.pause(10000);
        ClickHelper.clickElement(driver, postSubjectivitiesExpandButton);
    }

    public void EnterMessageToPostSubjectivitiesUnderWriterTextBox(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(3000);
        TextHelper.enterText(driver, messageToPostSubjectivitiesUnderWriterTextBox, "Sample text automation verification");
    }

    public void clickAddFilesButton(WebDriver driver){
        WaitHelper.waitForElementClickable(driver, addFilesButton);
        ClickHelper.clickElement(driver, addFilesButton);
    }

    public WebElement getFileDeleteIcon(WebDriver driver){
        return driver.findElement(deleteIconLocator);
    }

    public WebElement getFilePresentIcon(WebDriver driver){
        return driver.findElement(filePresentIcon);
    }

    public boolean verifyRejectedStatus(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, rejectedStatus);
    }

    public boolean verifyWaivedStatus(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, WaivedStatus);
    }

    public boolean verifyAcceptedStatus(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, AcceptedStatus);
    }

    public void clickSubmitBinder(WebDriver driver){

        ClickHelper.clickElement(driver, enabledSubmitButton);
    }
}
