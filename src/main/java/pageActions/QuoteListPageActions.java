package pageActions;

import base.BaseTest;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.fileDownload.FileDownloadUtil;

import java.util.ArrayList;
import java.util.List;
import static pageObjects.QuoteListPageObjects.*;
import static pageObjects.QuoteListPageObjects.inactiveQuote;


public class QuoteListPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteListPageActions.class);

    public boolean isQuoteListPageDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(5000);
       return ClickHelper.isElementExist(driver, quotesPageSelected);
    }

    public void clickAddOptionButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, addOptionButton);
        ClickHelper.clickElement(driver, addOptionButton);
    }

    public List<WebElement> getAllQuoteOptions(WebDriver driver){

        List<WebElement> elementList = driver.findElements(quoteOptionsGenericLocator);
        if(elementList.size()>0){
            return elementList;
        }else{
            return null;
        }
    }

    public void selectPerClaim(WebDriver driver, int optionCount, String claim) throws InterruptedException {
        String perClaimDropdownXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='groupLimit']/div";
        By perClaimDropdown = By.xpath(perClaimDropdownXpath);
        WaitHelper.waitForElementVisibility(driver, perClaimDropdown);
        WebElement dropdown = driver.findElement(perClaimDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, perClaimOptionGenericLocator, claim);
    }

    public void selectAggregateLimit(WebDriver driver, int optionCount, String aggLimit) throws InterruptedException {
        String aggregateLimitXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='aggregateLimit']/div";
        By aggregateLimitDropdown = By.xpath(aggregateLimitXpath);
        WaitHelper.waitForElementVisibility(driver, aggregateLimitDropdown);
        WebElement dropdown = driver.findElement(aggregateLimitDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, perClaimOptionGenericLocator, aggLimit);
    }

    public void selectRetentionOption(WebDriver driver, int optionCount, String retention) throws InterruptedException {
        String retentionOptionXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='retentionGroup']/div";
        By retentionDropdown = By.xpath(retentionOptionXpath);
        WaitHelper.waitForElementVisibility(driver, retentionDropdown);
        WebElement dropdown = driver.findElement(retentionDropdown);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, perClaimOptionGenericLocator, retention);
    }

    public int getQuoteOptionCount(WebDriver driver){
        List<WebElement> elementList = getAllQuoteOptions(driver);
        return elementList.size();
    }

    public void addNewQuoteOption(WebDriver driver, int count, String claim, String aggLimit, String retention) throws InterruptedException {
        int optionCount = count+1;
        clickAddOptionButton(driver);
        ScrollHelper.scrollToBottom(driver);
        selectPerClaim(driver,optionCount, claim);
        selectAggregateLimit(driver, optionCount, aggLimit);
        selectRetentionOption(driver, optionCount, retention);
    }

    public String getGivenQuoteOptionPremium(WebDriver driver, int optionCount){

        String optionPremiumXpath = "//div[text()='Option"+optionCount+"']/span";
        By optionPremium = By.xpath(optionPremiumXpath);
        return TextHelper.getText(driver, optionPremium, "text");
    }

    public boolean checkIfOpenQuoteExist(WebDriver driver){
        return ClickHelper.isElementExist(driver, lockIconOpenLocator);
    }

    public boolean checkIfLockedQuoteExist(WebDriver driver){
        return ClickHelper.isElementExist(driver, lockIconLocator);
    }

    public boolean checkIfQuoteListContainerDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, quoteListContainer);
    }

    public void deleteQuoteOption(WebDriver driver) throws InterruptedException {
        List<WebElement> deleteIcons = driver.findElements(deleteIconLocator);
        deleteIcons.get(0).click();
        WaitHelper.pause(5000);
    }

    public boolean addNewQuoteButton(WebDriver driver){
        return ClickHelper.isElementExist(driver, addQuoteButton);
    }

    public void addNewQuote(WebDriver driver, String quoteType){
        logger.info("adding quote to the submission based on quote type custom/4 option/6 options ");
        try{
            ClickHelper.clickElement(driver, addQuoteButton);
            String newQuoteXpath = "//ul//li";
            By newQuoteOption = By.xpath(newQuoteXpath);
            List<WebElement> options = driver.findElements(newQuoteOption);
            for (WebElement opt : options) {
                if (opt.getText().contains(quoteType)) {
                    opt.click();
                    break;
                }
            }
        }catch (Exception e){
            logger.error("failed to add the quote to submission based on the quote type"+e.getMessage());
            throw e;
        }
    }

    public void clickConfirmQuoteButton(WebDriver driver) throws InterruptedException {
        logger.info("clicking on confirm quote button :: clickConfirmQuoteButton");
        try{
            ClickHelper.clickElement(driver, confirmAndLockQuoteButton);
            WaitHelper.pause(20000);
        }catch (Exception e){
            logger.error("failed to click the confirm quote button :: clickConfirmQuoteButton"+e.getMessage());
            throw e;
        }
    }

    public void clickQuotesTab(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, clickOnQuotesTab);
        ClickHelper.clickElement(driver, clickOnQuotesTab);
        WaitHelper.pause(3000);
    }

    public boolean clickPDFFileDownload(WebDriver driver, String filename) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder(driver);

        ClickHelper.clickElement(driver, clickAsPDFDownloadButton);
        WaitHelper.pause(15000);

        return FileDownloadUtil.verifyPDFFileDownload(filename);
    }

    public boolean clickWORDFileDownload(WebDriver driver, String filename1, String filename2) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder(driver);

        ClickHelper.clickElement(driver, clickAsWordDownloadButton);
        WaitHelper.pause(15000);

        return FileDownloadUtil.verifyWORDFileDownload(filename1, filename2);
    }

    public boolean verifyPDFFileAvailable(WebDriver driver){
        return driver.findElement(clickAsPDFDownloadButton).isDisplayed();
    }

    public boolean verifyWORDFileAvailable(WebDriver driver){
        return driver.findElement(clickAsWordDownloadButton).isDisplayed();
    }

    public void verifyStatusConfirmAndLockInProgress(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, statusQuoteInProgress);
        ClickHelper.isElementExist(driver, statusQuoteInProgress);

    }
    public void verifyStatusConfirmAndLockReadyToPlaceOrder(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver,  statusQuoteReadyToPlaceOrder);
        ClickHelper.isElementExist(driver,  statusQuoteReadyToPlaceOrder);

    }

    public boolean clickConfirmAndLock(WebDriver driver) throws InterruptedException {
        if(ClickHelper.isElementExist(driver, confirmAndLockDisabledButton)){
            logger.error("Confirm and Lock button is disabled");
            return false;
        }else{
            WaitHelper.waitForElementVisibility(driver, confirmAndLockButton);
            ClickHelper.clickElement(driver, confirmAndLockButton);
            WaitHelper.pause(20000);
            return true;
        }
    }

    public String verifySuccessConfirmAndLockMessage(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteLockSuccessMessage);
        return TextHelper.getText(driver, quoteLockSuccessMessage, "text");
    }

    public boolean checkIfQuoteLockSuccessMessageDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, quoteLockSuccessMessage);
    }

    public boolean verifyQuotePreviewOptionVisible(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quotePreviewButton);
        return ClickHelper.isElementExist(driver, quotePreviewButton);
    }

    public boolean verifyQuotePreview(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, quotePreviewButton);
        ClickHelper.clickElement(driver, quotePreviewButton);
        WaitHelper.pause(15000);
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        if(tabs2.size()>1){
            driver.switchTo().window(tabs2.get(1));
            String newTabPreviewWindow = driver.getTitle();
            if(newTabPreviewWindow != null){
                driver.close();
            }
            driver.switchTo().window(tabs2.get(0));
            return true;
        }
        return false;
    }

    public boolean checkIfSubmitReviewDialogDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, submitReviewDialog);
    }

    public void enterQuoteReviewText(WebDriver driver){
        TextHelper.enterText(driver, submitReviewTextArea, "Quote Review Text");
    }

    public void clickSubmitForReview(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, submitReviewSubmitButton);
        ClickHelper.clickElement(driver, submitReviewSubmitButton);
    }

    public boolean isInactiveTextDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, inactiveQuote);
        return ClickHelper.isElementExist(driver, inactiveQuote);
    }

    public boolean isPDFFileIconDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, clickAsPDFDownloadButton);
    }

    public boolean isWordFileIconDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, clickAsWordDownloadButton);
    }

    public boolean verifyQuoteIsVisible(WebDriver driver){
        return ClickHelper.isElementExist(driver, quoteListPageHeader);
    }
}
