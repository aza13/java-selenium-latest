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


public class QuoteListPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteListPageActions.class);

    public boolean isQuoteListPageDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteListPageHeader);
       return ClickHelper.isElementExist(driver, quoteListPageHeader);
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
        DropdownHelper.selectValueFromBootstrapDropdown(driver, perClaimDropdown, perClaimOptionGenericLocator, claim);

    }

    public void selectAggregateLimit(WebDriver driver, int optionCount, String aggLimit) throws InterruptedException {
        String aggregateLimitXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='aggregateLimit']/div";
        By aggregateLimitDropdown = By.xpath(aggregateLimitXpath);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, aggregateLimitDropdown, perClaimOptionGenericLocator, aggLimit);

    }

    public void selectRetentionOption(WebDriver driver, int optionCount, String retention) throws InterruptedException {
        String retentionOptionXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='retentionGroup']/div";
        By retentionDropdown = By.xpath(retentionOptionXpath);
        DropdownHelper.selectValueFromBootstrapDropdown(driver, retentionDropdown, perClaimOptionGenericLocator, retention);
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

    public void deleteQuoteOption(WebDriver driver) throws InterruptedException {
        List<WebElement> deleteIcons = driver.findElements(deleteIconLocator);
        deleteIcons.get(0).click();
        WaitHelper.pause(5000);
    }

    public void clickQuotesTab(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, clickOnQuotesTab);
        ClickHelper.clickElement(driver, clickOnQuotesTab);
        WaitHelper.pause(3000);
    }

    public boolean clickPDFFileDownload(WebDriver driver, String filename) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder(driver);

        ClickHelper.clickElement(driver, clickAsPDFDownloadButton);
        WaitHelper.pause(20000);

        return FileDownloadUtil.verifyPDFFileDownload(filename);
    }

    public boolean clickWORDFileDownload(WebDriver driver, String filename1, String filename2) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder(driver);

        ClickHelper.clickElement(driver, clickAsWordDownloadButton);
        WaitHelper.pause(20000);

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

    public void clickConfirmAndLock(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, confirmAndLockButton);
        ClickHelper.clickElement(driver, confirmAndLockButton);
        WaitHelper.pause(20000);

    }

    public String verifySuccessConfirmAndLockMessage(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteSuccessMessage);
        return TextHelper.getText(driver, quoteSuccessMessage, "text");
    }

    public boolean verifyQuotePreviewOptionVisible(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quotePreviewButton);
        return ClickHelper.isElementExist(driver, quotePreviewButton);
    }

    public void clickQuotePreviewOption(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, quotePreviewButton);
        ClickHelper.clickElement(driver, quotePreviewButton);
        WaitHelper.pause(10000);

        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        String newTabPreviewWindow = driver.getTitle();
        if(newTabPreviewWindow != null){
            driver.close();
        }
        driver.switchTo().window(tabs2.get(0));

    }
}