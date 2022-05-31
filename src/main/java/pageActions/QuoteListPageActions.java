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

    public void selectPerClaim(WebDriver driver, String optionCount, String claim) throws InterruptedException {
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
        ScrollHelper.scrollToBottom(driver);
        selectPerClaim(driver,Integer.toString(optionCount), claim);
        selectAggregateLimit(driver, optionCount, aggLimit);
        selectRetentionOption(driver, optionCount, retention);
        WaitHelper.pause(10000);
    }

    public String getGivenQuoteOptionPremium(WebDriver driver, int optionCount){

        String optionPremiumXpath = "//div[starts-with(@data-qa, 'option_card_"+optionCount+"')]//div[text()='Max. Policy Aggregate Limit']/preceding-sibling::div//span";
        By optionPremium = By.xpath(optionPremiumXpath);
        return TextHelper.getText(driver, optionPremium, "text");
    }

    public boolean checkIfOpenQuoteExist(WebDriver driver){
        return ClickHelper.isElementExist(driver, lockIconOpenLocator);
    }

    public boolean verifyIfLockedQuoteExist(WebDriver driver){
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

    public boolean checkIfQuotesTabIsDisabled(WebDriver driver){
        return ClickHelper.isElementExist(driver, quotesTabDisabled);
    }

    public void clickQuotesTab(WebDriver driver) throws InterruptedException {
        if(ClickHelper.isElementExist(driver, quotesTabDisabled)){
            logger.warn("Quotes tab is disabled");
        }else{
            ClickHelper.clickElement(driver, quotesTab);
            WaitHelper.pause(3000);
        }
    }

    public void selectQuoteTemplateOption(WebDriver driver, int index) throws InterruptedException {
        try{
            List<WebElement> templateOptions = driver.findElements(quoteTemplateOption);
            if(templateOptions.size()>0){
                logger.info("selecting quote template option :: selectQuoteTemplateOption");
                templateOptions.get(index).click();
                WaitHelper.pause(7000);
            }
        }catch (Exception e){
            logger.error("failed to select the quote template option :: "+e.getMessage());
            throw e;
        }
    }

    public boolean clickPDFFileDownload(WebDriver driver, String filename) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder();

        ClickHelper.clickElement(driver, clickAsPDFDownloadButton);
        WaitHelper.pause(15000);

        return FileDownloadUtil.verifyPDFFileDownload(filename);
    }

    public boolean clickWORDFileDownload(WebDriver driver, String filename1, String filename2) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder();

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
        WaitHelper.pause(10000);
        if(ClickHelper.isElementExist(driver, confirmAndLockDisabledButton)){
            logger.error("Confirm and Lock button is disabled");
            return false;
        }else{
            WaitHelper.waitForElementVisibility(driver, confirmAndLockButton);
            ClickHelper.clickElement(driver, confirmAndLockButton);
            WaitHelper.pause(30000);
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

    public boolean verifyQuotePreviewOptionVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, quotePreviewButton);
    }

    public boolean verifyQuotePreview(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(5000);
        ClickHelper.clickElement(driver, quotePreviewButton);
        WaitHelper.pause(10000);
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


    public boolean verifyQuoteIsVisible(WebDriver driver) {
        return WaitHelper.isElementEnabled(driver, quoteListPageHeader);
    }

    public void expandTheQuote(WebDriver driver){
        ClickHelper.clickElement(driver, quoteExpandMoreIcon);
    }

    public void clickPlaceOrderButton(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, quotePlaceOrderButton);
        ClickHelper.clickElement(driver, quotePlaceOrderButton);
        WaitHelper.pause(5000);

    }

    public void submitOrderConfirmation(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, orderConfirmationDialog);
        TextHelper.enterText(driver, orderConfirmationTextArea, "Place Order Testing");
        ClickHelper.clickElement(driver, orderConfirmationSubmitButton);
        WaitHelper.pause(5000);
    }

    public String getOpenQuoteId(WebDriver driver){
        String quoteString = TextHelper.getText(driver, openQuoteIdLocator, "text");
        assert quoteString != null;
        String quoteId = quoteString.split("#")[1];
        return quoteId;
    }

    public void verifySoftDeclinePopup(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, SoftDeclineHeader);
        WaitHelper.waitForElementVisibility(driver, softDeclineText);
        TextHelper.enterText(driver, submitReviewTextArea, "Quote Review Text");
        ClickHelper.clickElement(driver, cancelSoftDecline);
        WaitHelper.pause(6000);
    }

    public boolean verifyDefaultCoverageCheckboxSelected(WebDriver driver){
        return ClickHelper.isElementExist(driver, coverageGroupCheckbox);
    }

    public void checkDefaultCoverageCheckboxSelectUnSelect(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, firstQuoteOptionPremium);
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.pause(5000);
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.waitForElementVisibility(driver, firstQuoteOptionPremium);
    }

    public boolean verifyOptionCoverageGroupUnSelect(WebDriver driver) throws InterruptedException {
        boolean isFieldVisible = false;
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.pause(7000);
        if(ClickHelper.isElementExist(driver, groupLimit)){
            isFieldVisible = true;
        }else if(ClickHelper.isElementExist(driver, aggregateLimit)){
            isFieldVisible = true;
        }else if(ClickHelper.isElementExist(driver, deductible)){
            isFieldVisible = true;
        }
        return isFieldVisible;
    }

    public boolean verifyOptionCoverageGroupSelect(WebDriver driver) throws InterruptedException {

        boolean isFieldVisible = false;
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.pause(5000);
        if(ClickHelper.isElementExist(driver, groupLimit)){
            if(ClickHelper.isElementExist(driver, aggregateLimit)){
                if(ClickHelper.isElementExist(driver, deductible)){
                    isFieldVisible = true;
                }
            }
        }
        return isFieldVisible;
    }

    public boolean verifyWarningMsgWhenUncheckedOptionCoverageGroup(WebDriver driver) throws InterruptedException {
        return ClickHelper.isElementExist(driver, warningMsg);
    }

    public boolean isPremiumAmountDisplay(WebDriver driver){
        return WaitHelper.isElementDisplayed(driver, firstQuoteOptionPremium);
    }

    public boolean isConfirmedAndLockQuoteButtonDisplay(WebDriver driver){
        return WaitHelper.isElementEnabled(driver, confirmAndLockQuoteButton);
    }

    public boolean isSelectVisibleToNewAddOption(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        List<WebElement> optionDropDown = driver.findElements(selectDropDown);
        if(optionDropDown.size()==3){
                return true;
        }
        return false;
    }

    public String clickClaimCheckbox(WebDriver driver, String selectCheckbox) throws InterruptedException {
        WaitHelper.pause(3000);
        String chooseCheckbox = "(//input[@type='checkbox'])["+selectCheckbox+"]";
        By chooseCoverageGroupCheckbox = By.xpath(chooseCheckbox);
        ClickHelper.clickElement(driver, chooseCoverageGroupCheckbox);
        WaitHelper.pause(3000);
        ClickHelper.clickElement(driver, chooseCoverageGroupCheckbox);
        String perClaimDropdownXpath = "//div[@data-qa='option_card_"+selectCheckbox+"']//div[@data-qa='groupLimit']/div";
        By perClaimDropdown = By.xpath(perClaimDropdownXpath);
        WaitHelper.waitForElementVisibility(driver, perClaimDropdown);
        WebElement dropdownValue = driver.findElement(perClaimDropdown);
        return dropdownValue.getText();
    }

    public String getAggLimitSelectedValue(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        WebElement dropdownValue = driver.findElement(aggregateLimit);
        return dropdownValue.getText();
    }

    public String getRetentionSelectedValue(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        WebElement dropdownValue = driver.findElement(deductible);
        return dropdownValue.getText();
    }

    public String getFirstOptionPremium(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(6000);
        WebElement premiumElement = driver.findElement(firstQuoteOptionPremium);
        return premiumElement.getText();
    }

    public String getFirstMaxPolicyAggLimit(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(6000);
        WebElement policyAggLimit = driver.findElement(firstQuoteOptionMaxPolicyAggLimit);
        return policyAggLimit.getText();
    }

    public boolean isQuoteExpiryDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, quoteExpiry);
    }

    public boolean checkIfFetchingOptionCoveragesMessageDisplayed(WebDriver driver){
        return ClickHelper.isElementExist(driver, fetchingOptionCoverages);
    }
}
