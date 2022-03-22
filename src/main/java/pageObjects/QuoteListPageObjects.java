package pageObjects;

import org.openqa.selenium.By;

public class QuoteListPageObjects {

    private QuoteListPageObjects(){}

    public static final By quoteListPageHeader = By.xpath("(//button[normalize-space()='Quotes'])[1]");
    public static final By addOptionButton = By.xpath("//button[text()='Add Option']/span");
    public static final By quoteOptionsGenericLocator = By.xpath("//div[starts-with(@data-qa, 'option_card_')]");
    public static final By perClaimOptionGenericLocator = By.xpath("//ul/li[starts-with(@data-qa, 'menuItem_')]");
    public static final By lockIconOpenLocator = By.xpath("//*[@data-testid='LockOpenIcon']");
    public static final By deleteIconLocator = By.xpath("//*[@data-testid='DeleteOutlineIcon']");
    public static final By clickAsPDFDownloadButton = By.xpath("//*[@data-testid='PictureAsPdfIcon']");
    public static final By clickAsWordDownloadButton = By.xpath("//div[@aria-label='Download Word']//img");
    public static final By clickOnQuotesTab = By.xpath("(//button[normalize-space()='Quotes'])[1]");
    public static final By statusQuoteInProgress = By.xpath("//div[contains(text(),'In Progress')]");
    public static final By statusQuoteReadyToPlaceOrder = By.xpath("//div[contains(text(),'Ready to Place Order')]");
    public static final By confirmAndLockButton = By.xpath("//button[normalize-space()='Confirm and Lock']");
    public static final By quoteSuccessMessage = By.xpath("//div[@class='sc-lcepkR gjhMuD MuiAlert-message']");
    public static final By quotePreviewButton = By.xpath("(//*[name()='svg'][@aria-label='Quote Preview”'])[1]");


}