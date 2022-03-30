package pageObjects;

import org.openqa.selenium.By;

public class QuoteListPageObjects {

    private QuoteListPageObjects(){}

    public static final By quoteListPageHeader = By.xpath("//h4[text()='Quote List']");
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
    public static final By clickOnQuotesTab = By.xpath("(//button[normalize-space()='Quotes'])[1]");
    public static final By statusQuoteInProgress = By.xpath("//div[contains(text(),'In Progress')]");
    public static final By statusQuoteReadyToPlaceOrder = By.xpath("//div[contains(text(),'Ready to Place Order')]");
    public static final By confirmAndLockButton = By.xpath("//button[normalize-space()='Confirm and Lock']");
    public static final By quoteLockSuccessMessage = By.xpath("//div[@data-qa='alertTitle' and text()='Success']");
    public static final By quotePreviewButton = By.xpath("(//*[name()='svg'][@aria-label='Quote Preview”'])[1]");
    public static final By quotesPageUnSelected = By.xpath("//button[@id='workflow-tab-3' and @aria-selected='false']");
    public static final By quotesPageSelected = By.xpath("//button[@id='workflow-tab-3' and @aria-selected='true']");
    public static final By submitReviewDialog = By.xpath("//h2[text()='Submit for review']");
    public static final By submitReviewTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By submitReviewCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By submitReviewReviseButton = By.xpath("//button[@data-qa='close_modal']");
    public static final By submitReviewSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");


}
