package pageObjects;

import org.openqa.selenium.By;

public class DashboardPageObjects {

    private DashboardPageObjects(){}

    public static final By tmhccLogo = By.xpath("//header//div/img");

    public static final By profileSettings = By.id("basic-button");

    public static final By profileLink = By.id("action_item_profile");

    public static final By signOutLink = By.id("action_item_signout");

    public static final By brokerIdField = By.id("filled-basic");

    public static final By agencyOfficeIdField = By.id("txt-AgencyOfficeId");

    public static final By agencyIdField = By.id("txt-AgencyId");

    public static final By myQuotesTab = By.id("simple-tab-0");

    public static final By myPoliciesTab = By.id("simple-tab-1");

    public static final By quotesList = By.xpath("//div[@id='listGridHeader']/following-sibling::div");

    public static final By quoteCard = By.xpath("//div[@data-qa='quote_card']");

    public static final By noQuoteFoundText = By.xpath("//p[text()='No quotes found.']");

    public static final By quotesListLabels = By.xpath("(//div[@id='listGridHeader']/following-sibling::div/div)[1]/div/div/div");

    public static final By policyListLabels = By.xpath("(//div[@class='policy_card'])[1]//div[@data-qa]/preceding-sibling::div");

    public static final By policyCard = By.className("policy_card");

    public static final By noPolicyFoundText = By.xpath("//p[text()='No Policy Found']");

    public static final By quotesRefLabel = By.xpath("//div[text()='Reference']");

    public static final By quoteStatus = By.xpath("//div[text()='Status']/following-sibling::p");

    public static final By newQuoteButton = By.xpath("//button[text()='New Quote']/span");

    public static final By selectProductDropdown = By.id("demo-simple-select");

    public static final By applicantNameField = By.id("applicant-name");

    public static final By websiteField = By.id("website-name");

    public static final By cancelButton = By.xpath("//button[text()='CANCEL']/span");

    public static final By continueButton = By.xpath("//button[text()='CONTINUE']/span");

    public static final By genericProductOption = By.xpath("//li[@role='option']");

    public static final By productRequiredText = By.xpath("//p[text()='Product is required']");

    public static final By nameRequiredText = By.xpath("//p[text()='Name is required']");

    public static final By websiteRequiredText = By.xpath("//p[text()='Name is required']");

    public static final By policyStatus = By.xpath("//div[@data-qa='status']");

    public static final By filterList = By.id("listGridFilter");

    public static final By filterByProductName = By.id("panel-header-1");

    public static final By filterByStatus = By.id("panel-header-2");

    public static final By filterByEffective = By.id("panel-header-3");

    public static final By allProductsDropdown = By.id("demo-simple-select");

    public static final By productOptions = By.xpath("//ul/li[contains(@data-qa, 'menuItem')]");






}
