package pageActions;

import base.BaseTest;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import utils.fileDownload.FileDownloadUtil;

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

    public boolean isBindingTabSelected(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, bindingTabSelected);
    }

    public boolean isGenerateBinderButtonExist(WebDriver driver) {
        return ClickHelper.isElementExist(driver, generateBinderButton);
    }

    public WebElement getGenerateBinderButton(WebDriver driver){
        try{
            return driver.findElement(generateBinderButton);
        }catch (Exception e){
            logger.error("Failed to click on Generate Binder button "+e.getMessage());
            throw e;
        }
    }

    public boolean isGenerateBinderDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, generateBinderButton);
    }

    public String getQuoteStatus(WebDriver driver) throws InterruptedException {
        try{
            WaitHelper.pause(10000);
            return TextHelper.getText(driver, quoteStatus, "text").trim();
        }catch (Exception e){
            logger.error("Failed to get the quote option status in binder page "+e.getMessage());
            throw e;
        }
    }

    public void clickGenerateBinderButton(WebDriver driver){
        try{
            ClickHelper.clickElement(driver, generateBinderButton);
        }catch (Exception e){
            logger.error("Failed to click on Generate Binder button "+e.getMessage());
            throw e;
        }
    }

    public void verifyQuoteHeaderInformationInBindingPage(WebDriver driver, String applicant, String product){
        List<WebElement> elements = driver.findElements(quoteHeaderInformation);
        assert elements.size() == 3;
        assert elements.get(0).getText().equals(applicant);
        assert elements.get(1).getText().equals(product);
        assert elements.get(2).getText().contains("Proposed Policy Period:");
    }

    public void clickPolicyCardExpandIconInBindingPage(WebDriver driver){

        ClickHelper.clickElement(driver, policyExpandMoreIcon);
    }

    public boolean isPriorSubjectivitiesDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, priorSubjectivities);
    }

    public boolean isPostSubjectivitiesDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, postSubjectivities);
    }

    public void clickPreSubjSelectFilesButton(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, preSubjSelectFilesButton);
        WaitHelper.pause(3000);
    }

    public void clickAndDragLink(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, clickAndDragLink);
        WaitHelper.pause(3000);
    }

    public void uploadFile(WebDriver driver, String relativeFilePath) throws InterruptedException, AWTException {
        ClickHelper.clickElement(driver, clickAndDragLink);
        WaitHelper.pause(5000);

        logger.info("creating object of Robot class");
        Robot rb = new Robot();
        String filePath = System.getProperty("user.dir")+relativeFilePath;

        logger.info("copying File path to Clipboard");
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        logger.info("press Contol+V for pasting");
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        logger.info("release Contol+V for pasting");
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        logger.info("for pressing and releasing Enter");
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WaitHelper.pause(3000);
    }

    public void uploadFileUsingSikuli(WebDriver driver, String imageToEnter, String filePath, String imageToSubmitted ) throws InterruptedException, FindFailed {
        clickAndDragLink(driver);
        String rootPath = System.getProperty("user.dir");
        SikuliHelper.uploadFile(rootPath+imageToEnter, rootPath+filePath, rootPath+imageToSubmitted);
    }

    public boolean isFileMaximumSizeTextDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        ScrollHelper.scrollElementIntoView(driver, singleFileMaximumSizeText);
        return ClickHelper.isElementExist(driver, singleFileMaximumSizeText);
    }

    public boolean isFileTypeWarningDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, fileSizeExceededText);
    }

    public boolean isFileTypeWarningDisplayed2(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, invalidFileTypeWarning);
    }

    public void enterMessageToPreSubjectivitiesUnderWriterTextBox(WebDriver driver){

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

    public void enterMessageToPostSubjectivitiesUnderWriterTextBox(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(3000);
        TextHelper.enterText(driver, messageToPostSubjectivitiesUnderWriterTextBox, "Sample text automation verification");
    }

    public void clickAddFilesButton(WebDriver driver){
        WaitHelper.waitForElementClickable(driver, addFilesButton);
        ClickHelper.clickElement(driver, addFilesButton);
    }

    public void clickFileDeleteIcon(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, fileDeleteIcon);
        WaitHelper.waitForProgressbarInvisibility(driver);
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

    public void clickSubmitBinder(WebDriver driver) throws InterruptedException {

        ClickHelper.clickElement(driver, enabledSubmitButton);
        WaitHelper.waitForProgressbarInvisibility(driver);
    }

    public void enableBinderSubmitButton(WebDriver driver) throws InterruptedException {
        try{
            if (!binderSubmitButton(driver).isEnabled()) {
                enterMessageToPreSubjectivitiesUnderWriterTextBox(driver);
                clickPostSubjectivitiesExpandButton(driver);
                enterMessageToPostSubjectivitiesUnderWriterTextBox(driver);
                clickSubmitBinder(driver);
            }
        }catch (Exception e){
            logger.info("Failed to enable binder submit button :: enableBinderSubmitButton");
            throw e;
        }

    }

    public boolean verifyBinderText(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(5000);
        return ClickHelper.isElementExist(driver, BinderText);
    }

    public boolean verifyPreBinderText(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, PreBinderText);
    }

    public boolean isFileSizeExceededWarningDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, fileSizeExceededText);
    }

    public boolean isBinderIssuedShortlyText(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, bindersWillBeIssuedShortlyText);
    }

    public String getPriorSubjectivityStatus(WebDriver driver){
        return TextHelper.getText(driver, priorSubjectivityStatus, "text");
    }

    public boolean clickBinderDownload(WebDriver driver, String filename) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder();

        ClickHelper.clickElement(driver, clickBinderPDFButton);
        WaitHelper.pause(15000);

        return FileDownloadUtil.verifyPDFFileDownload(filename);
    }
}
