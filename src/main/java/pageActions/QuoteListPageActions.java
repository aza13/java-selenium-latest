package pageActions;

import base.BaseTest;
import base.DriverManager;
import base.PageObjectManager;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.fileDownload.FileDownloadUtil;
import utils.fileReader.ConfigDataReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.openqa.selenium.support.locators.RelativeLocator.with;
import static pageObjects.BindingPageObjects.exitToDashboard;
import static pageObjects.QuoteListPageObjects.*;


public class QuoteListPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(QuoteListPageActions.class);

    public boolean isQuoteListPageDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(10000);
       return ClickHelper.isElementExist(driver, quotesPageSelected);
    }

    public void clickAddOptionButton(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, addOptionButton);
        ClickHelper.clickElement(driver, addOptionButton);
    }

    public List<WebElement> getAllQuoteOptions(WebDriver driver){
        List<WebElement> elementList = driver.findElements(quoteOptionsGenericLocator);
        if(!elementList.isEmpty()){
            return elementList;
        }else{
            return Collections.emptyList();
        }
    }

    public boolean verifyIfAggregateLimitIsDisabled(WebDriver driver){
        try{
            return !driver.findElement(aggregateLimitLocator).isEnabled();
        }catch (Exception e){
            logger.error("failed to verify if the aggregate limit dropdown is disabled");
            throw (e);
        }
    }

    public boolean verifyIfDeductibleIsDisabled(WebDriver driver){
        try{
            return !driver.findElement(deductibleLocator).isEnabled();
        }catch (Exception e){
            logger.error("failed to verify if the deductible dropdown is disabled");
            throw (e);
        }
    }

    public String getSelectedClaim(WebDriver driver){
        try{
            return driver.findElement(perClaimLocator).getAttribute("value");
        }catch (Exception e){
            logger.error("failed to get the selected per claim from the dropdown "+e.getMessage());
            throw (e);
        }
    }

    public String getSelectedAggregateLimit(WebDriver driver){
        try{
            return driver.findElement(aggregateLimitLocator).getAttribute("value");
        }catch (Exception e){
            logger.error("failed to get the aggregate limit dropdown "+e.getMessage());
            throw (e);
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
        WaitHelper.pause(5000);
    }

    public boolean selectRetentionOption(WebDriver driver, int optionCount, String retention) throws InterruptedException {
        String retentionOptionXpath = "//div[@data-qa='option_card_"+optionCount+"']//div[@data-qa='retentionGroup']/div";
        String errorIconXpath = "//div[@data-qa='option_card_"+optionCount+"']//*[@data-testid='ErrorOutlineIcon']";
        By errorIcon = By.xpath(errorIconXpath);
        By retentionDropdown = By.xpath(retentionOptionXpath);
        WebElement dropdown = driver.findElement(retentionDropdown);

        if( !ClickHelper.isElementExist(driver, errorIcon)){
            if(dropdown.isEnabled()){
                DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, perClaimOptionGenericLocator, retention);
            }
            return true;
        }else{
            return false;
        }
    }

    public int getQuotesCount(WebDriver driver){
        logger.info("this method returns number of quotes in the quote list page");
        List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@data-qa, 'quote_builder_card_')]"));
        return elementList.size();
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

    public boolean checkIfOpenQuoteExist(WebDriver driver){
        return ClickHelper.isElementExist(driver, lockIconOpenLocator);
    }

    public boolean verifyIfLockedQuoteExist(WebDriver driver){
        return ClickHelper.isElementExist(driver, lockIconLocator);
    }

    public void deleteQuoteOption(WebDriver driver) throws InterruptedException {
        List<WebElement> deleteIcons = driver.findElements(deleteIconLocator);
        deleteIcons.get(0).click();
        WaitHelper.pause(5000);
    }

    public void addNewQuote(WebDriver driver, String quoteType) throws InterruptedException {
        logger.info("adding quote to the submission based on quote type custom/4 option/6 options ");
        try{
            WaitHelper.pause(10000);
            ClickHelper.clickElement(driver, addQuoteButton);
            String newQuoteXpath = "//ul//li";
            By newQuoteOption = By.xpath(newQuoteXpath);
            List<WebElement> options = driver.findElements(newQuoteOption);
            assert !options.isEmpty();
            for (WebElement opt : options) {
                if (opt.getText().contains(quoteType)) {
                    opt.click();
                    break;
                }
            }
            WaitHelper.waitForProgressbarInvisibility(driver);
        }catch (Exception e){
            logger.error("failed to add the quote to submission based on the quote type"+e.getMessage());
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
            if(!templateOptions.isEmpty()){
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

    public boolean clickApplicationDownload(WebDriver driver, String filename) throws InterruptedException {

        FileDownloadUtil.checkFileExistInDownloadFolder();
        ClickHelper.clickElement(driver, clickAsApplicationButton);
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

    public boolean verifyStatusConfirmAndLockInProgress(WebDriver driver){
        return ClickHelper.isElementExist(driver, statusQuoteInProgress);

    }
    public String getQuoteStatus(WebDriver driver) throws InterruptedException {
        try{
            WaitHelper.waitForElementVisibilityCustom(driver, statusQuoteReadyToPlaceOrder, 30);
            return TextHelper.getText(driver,  statusQuoteReadyToPlaceOrder, "text");
        }catch (Exception e){
            logger.error("Failed to get quote status in Quotes List page "+e.getMessage());
            throw e;
        }

    }

    public boolean clickConfirmAndLockButtonIfDisplayed(WebDriver driver) {
        try{
            boolean clicked = false;
            if(ConfigDataReader.getInstance().getProperty("coverage").contains("Ophthalmic")) {
                logger.info("if the product is Ophthalmic, selects BRRP coverages");
                selectBRRPCoverageWithoutInvestigation(DriverManager.getDriver());
                selectBRRPCoverageWithInvestigation(DriverManager.getDriver());
            }else if(getSelectedClaim(driver)==null || getAggLimitSelectedValue(driver)==null) {
                int optionCount = getQuoteOptionCount(driver);
                selectPerClaim(driver, Integer.toString(optionCount), "$ 500k");
                selectAggregateLimit(driver, optionCount, "$ 500k");
                selectRetentionOption(driver, optionCount, "$ 10,000");
                WaitHelper.pause(5000);
            }else{
                logger.info("it waits for maximum 36 seconds for all the options");
                int n=0;
                while(ClickHelper.isElementExist(driver, confirmAndLockDisabledButton)){
                    WaitHelper.pause(3000);
                    n++;
                    if(n==12) break;
                }
            }
            if(driver.findElement(confirmAndLockButton).isDisplayed()){
                ClickHelper.clickElement(driver, confirmAndLockButton);
                WaitHelper.waitForProgressbarInvisibility(driver);
                clicked = true;
            }
            return clicked;
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }catch (Exception e){
            logger.error("failed to click the confirm and lock button "+e.getMessage());
            return false;
        }
    }

    public boolean lockTheQuote(WebDriver driver) throws InterruptedException {
        /***
         * This method clicks on the confirm and lock button
         * on successful quote lock it returns true, otherwise false
         */
        logger.info("This method clicks on the confirm and lock button");
        if (clickConfirmAndLockButtonIfDisplayed(driver)) {
            if (checkIfSubmitReviewDialogDisplayed(driver)) {
                enterQuoteReviewText(driver);
                clickSubmitForReview(driver);
            }
            return true;
        }else{
            logger.error("locking the quote is failed");
            return false;
        }
    }

    public String verifySuccessConfirmAndLockMessage(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, quoteLockSuccessMessage);
        return TextHelper.getText(driver, quoteLockSuccessMessage, "text");
    }

    public boolean verifyQuoteLockSuccessMessageDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
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
        ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
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

    public boolean checkIfSubmitReviewDialogDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibilityCustom(driver, submitReviewDialog, 15);
        return ClickHelper.isElementExist(driver, submitReviewDialog);
    }

    public String getSubmitReviewDialogText(WebDriver driver){
        try{
            return TextHelper.getText(driver, submitReviewDialogText, "text").trim();
        }catch (Exception e){
            logger.error("failed to get the text of submit review tex :: getSubmitReviewDialogText "+e.getMessage());
            throw (e);
        }
    }

    public boolean checkIfSubmitReviewDialogDisplayed2(WebDriver driver){
        return ClickHelper.isElementExist(driver, submitForReviewModal);
    }

    public void enterQuoteReviewText(WebDriver driver){
        TextHelper.enterText(driver, submitReviewTextArea, "Quote Review Text");
    }

    public void clickSubmitForReview(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, submitReviewSubmitButton);
        ClickHelper.clickElement(driver, submitReviewSubmitButton);
    }

    public WebElement submitReviewCancelButton(WebDriver driver){
        try{
            return driver.findElement(submitReviewCancelButton);
        }catch (Exception e){
            logger.error("Failed to return cancel button of Submit for Review modal "+e.getMessage());
            throw e;
        }

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
    public boolean verifyOutsideBrokerPortalGuidelinesVisible (WebDriver driver) {
        return WaitHelper.isElementDisplayed(driver, valueOutsideBrokerPortalGuidelines);
    }

    public void expandTheQuote(WebDriver driver){
        ClickHelper.clickElement(driver, quoteExpandMoreIcon);
    }

    public void clickConfirmDatesAndPlaceOrderButton(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, confirmDatesAndPlaceOrderButton);
        try{
            WebElement button = driver.findElement(confirmDatesAndPlaceOrderButton);
            if(button.isDisplayed()){
                button.click();
                WaitHelper.waitForProgressbarInvisibility(driver);
            }
        }catch (Exception e){
            logger.error("Failed to click on Confirm dates and Place order button "+e.getMessage());
            throw e;
        }
    }

    public BindingPageActions submitOrderConfirmation(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, orderConfirmationDialog);
        TextHelper.enterText(driver, orderConfirmationTextArea, "Place Order Testing");
        ClickHelper.clickElement(driver, orderConfirmationSubmitButton);
        WaitHelper.waitForProgressbarInvisibility(driver);
        return PageObjectManager.getBindingPageActions();
    }

    public BindingPageActions clickConfirmDatesConfirmButton(WebDriver driver) throws InterruptedException {
        try{
            ClickHelper.clickElement(driver, confirmDatesConfirmButton);
            WaitHelper.waitForProgressbarInvisibility(driver);
            return PageObjectManager.getBindingPageActions();
        }catch (Exception e){
            logger.error("Failed to click on the confirm button "+ e.getMessage());
            throw e;
        }
    }

    public WebElement getConfirmDatesEffectiveDate(WebDriver driver){
        try{
            return driver.findElement(confirmDatesEffectiveDate);
        }catch (Exception e){
            logger.error("failed get the effective date field from confirm dates :: getConfirmDatesEffectiveDate"+e.getMessage());
            throw (e);
        }
    }

    public WebElement getConfirmDatesExpirationDate(WebDriver driver){
        try{
            return driver.findElement(confirmDatesExpirationDate);
        }catch (Exception e){
            logger.error("failed get the expiration date field from confirm dates :: getConfirmDatesExpirationDate"+e.getMessage());
            throw (e);
        }
    }

    public String getEffectiveDate(WebDriver driver){
        try{
            return getConfirmDatesEffectiveDate(driver).getAttribute("value");
        }catch (Exception e){
            logger.info("Failed to get the eff date of confirm dates modal :: getEffectiveDate"+e.getMessage());
            throw e;
        }
    }

    public WebElement getConfirmDatesCancelButton(WebDriver driver){
        try{
            return driver.findElement(confirmDatesCancelButton);
        }catch (Exception e){
            logger.error("failed get the expiration date field from confirm dates :: getConfirmDatesExpirationDate"+e.getMessage());
            throw (e);
        }
    }

    public boolean validateConfirmDatesModalFields(WebDriver driver) {
        try{
            WaitHelper.waitForElementVisibility(driver, confirmDatesModal);
            String title = TextHelper.getText(driver, confirmDatesModalTitle, "text");
            assert Objects.equals(title, "Please confirm dates to place order");
            String description = TextHelper.getText(driver, confirmDatesModalDescription, "text");
            assert description.trim().startsWith("If you'd like an Effective Date or Expiration Date that is not selectable in QuoteIt");
            if (!getConfirmDatesExpirationDate(driver).isDisplayed()) throw new AssertionError();
            if (!getConfirmDatesExpirationDate(driver).isDisplayed()) throw new AssertionError();
            if (!getConfirmDatesCancelButton(driver).isDisplayed()) throw new AssertionError();
            return true;
        }catch (Exception e){
            logger.error("failed to validate Confirm Dates Modal :: validateConfirmDatesModalFields"+e.getMessage());
            return false;
        }
    }

    public long validateEffectiveDate(WebDriver driver) throws ParseException {
        try{
            String effDate = getEffectiveDate(driver);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date1=formatter.parse(effDate);
            String date = formatter.format(new Date());
            Date date2=formatter.parse(date);
            long differenceInTime = date2.getTime() - date1.getTime();
            return (differenceInTime
                    / (1000 * 60 * 60 * 24))
                    % 365;
        }catch (Exception e){
            logger.info("failed to validate the eff date :: validateEffectiveDate"+e.getMessage());
            throw e;
        }
    }

    public void enterEffectiveDate(WebDriver driver) throws ParseException {
        try{
            String effDate = getEffectiveDate(driver);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date1=formatter.parse(effDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            logger.info("increasing the date by 1 dy");
            c.add(Calendar.DATE, 1);
            date1 = c.getTime();
            String date = formatter.format(date1);
            TextHelper.enterText(driver, confirmDatesEffectiveDate, date);
        }catch (Exception e){
            logger.info("failed to validate the eff date :: validateEffectiveDate"+e.getMessage());
            throw e;
        }
    }

    public String getOpenQuoteId(WebDriver driver) throws InterruptedException {
        try{
            boolean quotePage = isQuoteListPageDisplayed(driver);
            assert quotePage;
            String quoteString = TextHelper.getText(driver, openQuoteIdLocator, "text");
            return quoteString.split("#")[1];
        }catch (Exception e){
            logger.info("this method returns quote id :: getOpenQuoteId"+e.getMessage());
            throw e;
        }
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

    public boolean verifyOptionCoverageGroupUnSelect(WebDriver driver) throws InterruptedException {
        boolean isFieldVisible = false;
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.waitForElementVisibilityCustom(driver, groupLimit, 15);
        if(ClickHelper.isElementExist(driver, groupLimit) && ClickHelper.isElementExist(driver, aggregateLimit) && ClickHelper.isElementExist(driver, deductible)){
            isFieldVisible = true;
        }
        return isFieldVisible;
    }

    public boolean verifyOptionCoverageGroupSelect(WebDriver driver) throws InterruptedException {
        boolean isFieldVisible = false;
        ClickHelper.clickElement(driver, coverageGroupCheckbox);
        WaitHelper.waitForElementVisibilityCustom(driver, groupLimit, 15);
        if(ClickHelper.isElementExist(driver, groupLimit) && ClickHelper.isElementExist(driver, aggregateLimit) && ClickHelper.isElementExist(driver, deductible)){
            isFieldVisible = true;
        }
        return isFieldVisible;
    }

    public boolean verifyWarningMsgWhenUncheckedOptionCoverageGroup(WebDriver driver) {
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
        return optionDropDown.size() == 3;
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
        WaitHelper.waitForElementVisibilityCustom(driver, quoteExpiry, 15);
        return ClickHelper.isElementExist(driver, quoteExpiry);
    }

    public void selectBRRPCoverageWithoutInvestigation(WebDriver driver) throws InterruptedException {
        try{
            String eMDCheckbox = "//div[@data-qa='option_card_2']//div//p//span[@data-qa='coverageGroup_isSelected']/span";
            if(driver.findElement(By.xpath(eMDCheckbox)).isDisplayed()){
                driver.findElement(By.xpath(eMDCheckbox)).click();
                selectPerClaim(driver, "2", "$ 250k");
                selectAggregateLimit(driver, 2, "$ 500k");
                WaitHelper.pause(5000);
            }
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
        }catch (Exception e){
            logger.error("Unable to select the coverage " +e.getMessage());
            throw(e);
        }
    }

    public void selectBRRPCoverageWithInvestigation(WebDriver driver) throws InterruptedException {
        try{
            String eMDCheckbox = "//div[@data-qa='option_card_3']//div//p//span[@data-qa='coverageGroup_isSelected']/span";
            if(driver.findElement(By.xpath(eMDCheckbox)).isDisplayed()){
                driver.findElement(By.xpath(eMDCheckbox)).click();
                selectPerClaim(driver, "3", "$ 250k");
                selectAggregateLimit(driver, 3, "$ 500k");
            }
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
        }catch (Exception e){
            logger.error("Unable to select the coverage " +e.getMessage());
            throw(e);
        }
    }

    public boolean verifyContactUnderwriter(WebDriver driver) {
        logger.info("this method verifies that contact UW button present left to the add quote button");
        try{
            WaitHelper.waitForElementVisibilityCustom(driver, addQuoteButton, 15);
            return driver.findElement(with(By.tagName("button")).toLeftOf(addQuoteButton)).isDisplayed();
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }catch (Exception e){
            logger.error("Failed click on the  Contact Underwriter button " +e.getMessage());
            throw(e);
        }
    }

    public void clickContactUnderwriter(WebDriver driver) {
        try{
            WaitHelper.waitForElementVisibilityCustom(driver, contactUnderwriterButton, 15);
            ClickHelper.clickElement(driver, contactUnderwriterButton);
            WaitHelper.pause(5000);
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
        }catch (Exception e){
            logger.error("Failed click on the  Contact Underwriter button  " +e.getMessage());
            throw(e);
        }
    }

    public void clickOnExitDashboard(WebDriver driver){
        try{
            WaitHelper.waitForElementVisibilityCustom(driver, exitToDashboard, 15);
            ClickHelper.clickElement(driver, exitToDashboard);
        }catch (InterruptedException ie){
            logger.error("Interrupted Exception "+ie.getMessage());
            Thread.currentThread().interrupt();
        }catch(Exception e){
            logger.error("Failed to click on exit button :: clickOnExitDashboard" +e.getMessage());
            throw(e);
        }
    }
}
