package pageObjects;

import org.openqa.selenium.By;

public class QuoteListPageObjects {

    private QuoteListPageObjects(){}

    public static final By quoteListPageHeader = By.xpath("(//button[normalize-space()='Quotes'])[1]");
    public static final By addOptionButton = By.xpath("//button[text()='Add Option']/span");
    public static final By quoteOptionsGenericLocator = By.xpath("//div[starts-with(@data-qa, 'option_card_')]");
    public static final By perClaimOptionGenericLocator = By.xpath("//ul/li[starts-with(@data-qa, 'menuItem_')]");
    public static final By lockIconOpenLocator = By.xpath("//*[@data-testid='LockOpenIcon']");
    public static final By lockIconLocator = By.xpath("//*[@data-testid='LockIcon']");
    public static final By deleteIconLocator = By.xpath("//*[@data-testid='DeleteOutlineIcon']");
    public static final By addQuoteButton = By.id("add_quote_button");
    public static final By confirmAndLockQuoteButton = By.xpath("//button[text()='Confirm and Lock']");
    public static final By quoteListContainer = By.xpath("//div[@data-qa='quote_list_container']");
    public static final By clickAsPDFDownloadButton = By.xpath("//*[@data-testid='PictureAsPdfIcon']");
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
    public static final By submitReviewDialog = By.xpath("//h2[text()='Submit for review']");
    public static final By submitReviewTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By submitReviewCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By submitReviewReviseButton = By.xpath("//button[@data-qa='close_modal']");
    public static final By submitReviewSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By quoteSuccessMessage = By.xpath("//div[@class='sc-lcepkR gjhMuD MuiAlert-message']");
    public static final By quotePreviewButton = By.xpath("//*[@data-testid='PreviewIcon']");
    public static final By inactiveQuote = By.xpath("//div[contains(text(),'Inactive')]");
    public static final By quoteTemplateOption = By.xpath("//ul[@role='menu']/li");
    public static final By quoteExpandMoreIcon = By.xpath("//div[@data-qa='quote_list_container']//*[@data-testid='ExpandMoreIcon']");
    public static final By quotePlaceOrderButton = By.xpath("//button[text()='Place Order']");
    public static final By orderConfirmationDialog = By.xpath("//h2[@id='alert-dialog-title' and text()='Order Confirmation']");
    public static final By orderConfirmationTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By orderConfirmationSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By perClaimSelection = By.xpath("//div[@data-qa='groupLimit']//div[@aria-label='Without label' and contains(text(),'$ 500k')]");
    public static final By perClaimSelectionValue = By.xpath("//span[normalize-space()='$ 1MM']");
    public static final By SoftDeclineHeader = By.xpath("//h2[@id='alert-dialog-title']");
    public static final By softDeclineText = By.xpath("//*[@id='alert-dialog-description']/div[2]/div/h1");
    public static final By cancelSoftDecline = By.xpath("//button[normalize-space()='Cancel Everything']");
    public static final By coverageGroupCheckbox = By.xpath("(//input[@type='checkbox'])[1]");
    public static final By coverageGroupAmount = By.xpath("(//span[normalize-space()='$1,650'])[1]");
    public static final By groupLimit = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[2]/div/div/div");
    public static final By aggregateLimit = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[3]/div/div/div");
    public static final By deductible = By.xpath("//*[@id='panel-content-1']/div/div[1]/div/div/p[2]/div/div[4]/div/div/div");
    public static final By warningMsg = By.xpath("//div[contains(text(),'An option should have at least 1 selected primary ')]");
    public static final By selectDropDown = By.xpath("//*[@role='button' and em[text()='Select']]");
}
