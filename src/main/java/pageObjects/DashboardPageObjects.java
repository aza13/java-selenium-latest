package pageObjects;

import org.openqa.selenium.By;

public class DashboardPageObjects {

    private DashboardPageObjects(){}

    public static final By tmhccLogo = By.xpath("//header//div/img");

    public static final By profileSettings = By.id("basic-button");

    public static final By profileLink = By.id("action_item_profile");

    public static final By signOutLink = By.id("action_item_signout");

    public static final By supportLink = By.id("action_item_support");

    public static final By supportDialogModal = By.xpath("//h2[text()='Support Request']");

    public static final By supportRequestDetailTextArea = By.id("outlined-textarea");

    public static final By supportRequestSendButton = By.xpath("//button[@data-qa='submit_alert_modal']");

    public static final By supportTicketSuccessMessage = By.xpath("//div[@data-qa='alertTitle']");

    public static final By closeSuccessMessageButton = By.xpath("//button[@data-qa='closeAlertButton']");

    public static final By supportRequestCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");

    public static final By supportTypeRequired = By.id("demo-simple-select-error-label");

    public static final By supportRequestDetailsRequired = By.id("outlined-error-helper-text-helper-text");

    public static final By brokerIdField = By.id("txt-brokerid");

    public static final By agencyOfficeIdField = By.id("txt-AgencyOfficeId");

    public static final By agencyIdField = By.id("txt-AgencyId");

    public static final By myQuotesTab = By.id("simple-tab-0");

    public static final By myPoliciesTab = By.id("simple-tab-1");

    public static final By quotesList = By.xpath("//div[@id='listGridHeader']/following-sibling::div");

    public static final By quoteCard = By.xpath("//div[@data-qa='quote_card']");

    public static final By noQuoteFoundText = By.xpath("//p[text()='Adjusting filters may return results.']");

    public static final By quotesListLabels = By.xpath("(//div[@data-qa='quote_card'])[1]//p/preceding-sibling::div");

    public static final By policyListLabels = By.xpath("(//div[contains(@class,'policy_card')])[1]//p/preceding-sibling::div");

    public static final By policyCard = By.className("policy_card");

    public static final By noPolicyFoundText = By.xpath("//p[text()='Adjusting filters may return results.']");

    public static final By quotesRefLabel = By.xpath("//div[text()='Reference']");

    public static final By quoteStatus = By.xpath("//div[@data-qa='quote_card']//p[@data-qa='status']");

    public static final By quoteProductName = By.xpath("//p[@data-qa='name']");

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

    public static final By submissionFilterByStatus = By.id("panel-header-2");

    public static final By submissionFilterByDateRange = By.xpath("//p[text()='Date Range']");

    public static final By allProductsDropdown = By.id("demo-simple-select");

    public static final By productOptions = By.xpath("//ul/li[contains(@data-qa, 'menuItem')]");

    public static final By searchInputFiled = By.id("global_search");

    public static final By clearSearchInputFiled = By.xpath("//header/div[1]/button[1]/*[1]");

    public static final By getFirstAvailableReferenceId = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[2]/div/p");

    public static final By searchForNoResult = By.xpath("//p[contains(text(),'Your search has no results')]");

    public static final By getFirstAvailableLegalName = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[1]/div[1]");

    public static final By applyFiltersButton = By.id("applyFilters");

    public static final By allStatusDropdown = By.xpath("//div[@data-qa='quoteStatusSection']//div[@id='demo-simple-select']");

    public static final By statusOptions = By.xpath("//ul/li[contains(@data-qa, 'menuItem')]");

    public static final By quoteCardGenericContinueButton = By.xpath("//div[@data-qa='quote_card']/div/div[last()]//button");

    public static final By policyAllStatusDropdown = By.xpath("//div[@data-qa='policyStatusSection']//div[@id='demo-simple-select']");

    public static final By policyFilterByStatus = By.id("panel-header-1");

    public static final By policyFilterByEffective = By.id("panel-header-2");

    public static final By createdStartDateField = By.id("startdate");

    public static final By createdEndDateField = By.id("enddate");

    public static final By quoteCreatedDateGeneric = By.xpath("//p[@data-qa='created']");

    public static final By policyExpirationDateGeneric = By.xpath("//p[@data-qa='expDate']");

    public static final By firstAvailableRenewButton = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[7]/div/button");

    public static final By quoteReferenceIdGenericLocator = By.xpath("//p[@data-qa='id']");

    public static final By firstAvailableStatus = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[6]/div/div[2]");

    public static final By submitSubmissionRenewal = By.xpath("//button[normalize-space()='SUBMIT']");

    public static final By firstAvailableCreatedDate = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[3]/div/p");

    public static final By sortBy = By.xpath("//button[@id='sortByButton']//*[name()='svg']");

    public static final By sortByNewest = By.xpath("//li[normalize-space()='Newest']");

    public static final By sortByOldest = By.cssSelector("li:nth-child(1)");

    public static final By sortByExpiringLater = By.xpath("//li[normalize-space()='Expiring Later']");

    public static final By sortByExpiringSoon = By.xpath("//li[normalize-space()='Expiring Soon']");

    public static final By exitRatingCriteria = By.xpath("//button[@id='rating_criteria-cancel']");

    public static final By statusInDashboard = By.xpath("//div[@data-qa='status']");

    public static final By myPolicyCardGenericContinueButton = By.xpath("//button[@type='button'][normalize-space()='Continue']");

    public static final By fistAvailableContinueButton = By.xpath("(//button[text()='Continue'])[1]");



}
