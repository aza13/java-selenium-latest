package pageObjects;

import org.openqa.selenium.By;

public class QuoteListPageObjects {

    private QuoteListPageObjects(){}

    public static final By quoteListPageHeader = By.xpath("(//button[normalize-space()='Quotes'])[1]");
    public static final By addOptionButton = By.xpath("//button[text()='Add Option']/span");
    public static final By quoteOptionsGenericLocator = By.xpath("//div[starts-with(@data-qa, 'option_card_')]");
    public static final By perClaimLocator = By.xpath("//div[@data-qa='groupLimit']//input");
    public static final By aggregateLimitLocator = By.xpath("//div[@data-qa='aggregateLimit']//input");
    public static final By deductibleLocator = By.xpath("//div[@data-qa='retentionGroup']//input");
    public static final By perClaimOptionGenericLocator = By.xpath("//ul/li[starts-with(@data-qa, 'menuItem_')]");
    public static final By lockIconOpenLocator = By.xpath("//*[@data-testid='LockOpenIcon']");
    public static final By lockIconLocator = By.xpath("//*[@data-testid='LockIcon']");
    public static final By deleteIconLocator = By.xpath("//*[@data-testid='DeleteOutlineIcon']");
    public static final By addQuoteButton = By.id("add_quote_button");
    public static final By confirmAndLockQuoteButton = By.xpath("//button[text()='Confirm and Lock']");
    public static final By quoteListContainer = By.xpath("//div[@data-qa='quote_list_container']");
    public static final By clickAsPDFDownloadButton = By.xpath("//*[@data-testid='PictureAsPdfIcon']");
    public static final By clickAsApplicationButton = By.xpath("//div[@id='panel-header-1']//div[5]//*[name()='svg']");
    public static final By clickAsWordDownloadButton = By.xpath("//div[@aria-label='Download Word']//img");
    public static final By quotesTab = By.xpath("//button[text()='Quotes']");
    public static final By quotesTabDisabled = By.xpath("//button[text()='Quotes' and @disabled]");
    public static final By statusQuoteInProgress = By.xpath("//div[contains(text(),'In Progress')]");
    public static final By statusQuoteReadyToPlaceOrder = By.xpath("//div[contains(text(),'Ready to Place Order')]");
    public static final By confirmAndLockButton = By.xpath("//button[normalize-space()='Confirm and Lock']");
    public static final By confirmAndLockDisabledButton = By.xpath("//button[text()='Confirm and Lock' and @disabled]");
    public static final By quoteLockSuccessMessage = By.xpath("//div[@data-qa='alertTitle' and text()='Success']");
    public static final By quotesPageUnSelected = By.xpath("//button[@id='workflow-tab-3' and @aria-selected='false']");
    public static final By quotesPageSelected = By.xpath("//button[@id='workflow-tab-3' and @aria-selected='true']");
    public static final By prePlaceOrderFirstPremium = By.xpath("//div[contains(text(),'Option 1')]/span");


    /** submit for review modal**/
    public static final String submitReviewDialogCss = "document.querySelector('body > div.sc-bjUoiL.erxvLZ.sc-gSAPjG.gMBRMP.MuiDialog-root.MuiModal-root > div.sc-lbxAil.hPeWbF.MuiDialog-container.MuiDialog-scrollPaper > div > div.sc-eKszNL.geooGQ.MuiDialogContent-root').scrollTop=-200";
    public static final By submitReviewDialog = By.xpath("//h2[text()='Submit for Review']");
    public static final By submitReviewDialogText = By.xpath("//p[@id='alert-dialog-description']//div/h1");
    public static final By submitReviewTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By submitReviewCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By submitReviewSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By quoteSuccessMessage = By.xpath("//div[@class='sc-lcepkR gjhMuD MuiAlert-message']");
    public static final By quotePreviewButton = By.xpath("//*[@data-testid='PreviewIcon']");
    public static final By inactiveQuote = By.xpath("//div[contains(text(),'Inactive')]");
    public static final By quoteTemplateOption = By.xpath("//ul[@role='menu']/li");
    public static final By quoteExpandMoreIcon = By.xpath("//div[@data-qa='quote_list_container']//*[@data-testid='ExpandMoreIcon']");

    /*** confirm dates modal ***/
    public static final By confirmDatesAndPlaceOrderButton = By.xpath("//button[text()='Confirm Dates & Place Order']");
    public static final By orderConfirmationDialog = By.xpath("//h2[@id='alert-dialog-title' and text()='Order Confirmation']");
    public static final By confirmDatesModal = By.xpath("//h2[@id='alert-dialog-title' and text()='Confirm Dates']");
    public static final By confirmDatesModalTitle = By.xpath("//p[@id='alert-dialog-description']//h5");
    public static final By confirmDatesModalDescription = By.xpath("//p[@id='alert-dialog-description']//p");
    public static final By confirmDatesEffectiveDate = By.xpath("//label[text()='Effective Date']/following-sibling::div/input");
    public static final By confirmDatesExpirationDate = By.xpath("//label[text()='Expiration Date']/following-sibling::div/input");
    public static final By confirmDatesEffectiveDateCalender = By.xpath("//label[text()='Effective Date']/..//button");
    public static final By confirmDatesConfirmButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By confirmDatesCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");

    public static final By orderConfirmationTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By orderConfirmationSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By openQuoteIdLocator = By.xpath("//*[@data-testid='LockOpenIcon']/parent::div");
    public static final By lockedQuoteIdLocator = By.xpath("//*[@data-testid='LockIcon']/parent::div");
    public static final By SoftDeclineHeader = By.xpath("//h2[@id='alert-dialog-title']");
    public static final By softDeclineText = By.xpath("//*[@id='alert-dialog-description']/div[2]/div/h1");
    public static final By cancelSoftDecline = By.xpath("//button[normalize-space()='Cancel Everything']");
    public static final By coverageGroupCheckbox = By.xpath("//div[@data-qa='option_card_1']//span[@data-qa='coverageGroup_isSelected']/span");
    public static final By firstQuoteOptionPremium = By.xpath("(//div[starts-with(@data-qa, 'option_card_')]//div[text()='Max. Policy Aggregate Limit']/preceding-sibling::div//span)[1]");
    public static final By groupLimit = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[2]/div/div/div");
    public static final By aggregateLimit = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[3]/div/div/div");
    public static final By deductible = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[4]/div/div/div");
    public static final By warningMsg = By.xpath("//div[contains(text(),'An option should have at least 1 selected primary ')]");
    public static final By selectDropDown = By.xpath("//*[@role='button' and em[text()='Select']]");
    public static final By quoteOptionPremiumGenericLocator = By.xpath("//div[starts-with(@data-qa, 'option_card_')]//div[text()='Max. Policy Aggregate Limit']/preceding-sibling::div//span");
    public static final By quoteOptionMaxPolicyAggLimit = By.xpath("//div[starts-with(@data-qa, 'option_card_')]//div[text()='Max. Policy Aggregate Limit']/following-sibling::div//span");
    public static final By firstQuoteOptionMaxPolicyAggLimit = By.xpath("//div[starts-with(@data-qa, 'option_card_1')]//div[text()='Max. Policy Aggregate Limit']/following-sibling::div//span");
    public static final By quoteExpiry = By.xpath("//div[contains(text(),'Expiration')]");
    public static final By fetchingOptionCoverages = By.xpath("//div[@data-qa='option_card_4']//div[contains(text(),'Fetching Option Coverages')]");
    public static final By valueOutsideBrokerPortalGuidelines = By.xpath("//div[contains(text(),'The highlighted selected value(s) are outside our ')]");
    public static final By contactUnderwriterButton = By.xpath("//button[text()='Contact Underwriter']/span");
    public static final By submitForReviewModal = By.xpath("//h2[text()='Submit For Review']");
    public static final By exitToDashboard =By.id("quote_builder_exit");
}
