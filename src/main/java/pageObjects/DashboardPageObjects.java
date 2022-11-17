package pageObjects;

import org.openqa.selenium.By;

public class DashboardPageObjects {

    private DashboardPageObjects(){}

    public static final By tmhccLogo = By.xpath("//header//div/img");

    public static final By profileSettings = By.id("basic-button");

    public static final By signOutLink = By.id("action_item_signout");

    public static final By supportLink = By.xpath("//ul//li[@id='action_item_support' and text()='Support']");

    public static final By supportRequestDetailTextArea = By.id("outlined-textarea");

    public static final By supportRequestSendButton = By.xpath("//button[@data-qa='submit_alert_modal']");

    public static final By supportTicketSuccessMessage = By.xpath("//div[@data-qa='alertTitle']");

    public static final By closeSuccessMessageButton = By.xpath("//button[@data-qa='closeAlertButton']");

    public static final By supportRequestCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");

    public static final By supportTypeRequired = By.id("demo-simple-select-error-label");

    public static final By supportRequestDetailsRequired = By.id("outlined-error-helper-text-helper-text");

    public static final By brokerIdField = By.id("txt-brokerid");

    public static final By myQuotesTab = By.id("simple-tab-0");

    public static final By myPoliciesTab = By.id("simple-tab-1");

    public static final By quoteCard = By.xpath("//div[@data-qa='quote_card']");

    public static final By noQuoteFoundText = By.xpath("//p[text()='Adjusting filters may return results.']");

    public static final By quotesListLabels = By.xpath("(//div[@data-qa='quote_card'])[1]//p/preceding-sibling::div");

    public static final By policyListLabels = By.xpath("(//div[contains(@class,'policy_card')])[1]//p/preceding-sibling::div");

    public static final By policyCard = By.xpath("//div[contains(@class, 'policy_card')]");

    public static final By noPolicyFoundText = By.xpath("//p[text()='Adjusting filters may return results.']");

    public static final By quoteStatus = By.xpath("//div[@data-qa='quote_card']//p[@data-qa='status']");

    public static final By quoteCoverageName = By.xpath("//p[@data-qa='name']");

    public static final By newQuoteButton = By.xpath("//button[text()='New Quote']/span");

    public static final By selectCoverageDropdown = By.id("demo-simple-select");

    public static final By applicantNameField = By.id("applicant-name");

    public static final By websiteField = By.id("website-name");

    public static final By cancelButton = By.xpath("//button[text()='CANCEL']/span");

    public static final By continueButton = By.xpath("//button[text()='CONTINUE']/span");

    public static final By genericCoverageOption = By.xpath("//li[@role='option']");

    public static final By coverageRequiredText = By.xpath("//p[text()='Product is required']");

    public static final By nameRequiredText = By.xpath("//p[text()='Name is required']");

    public static final By policyStatus = By.xpath("//div[@data-qa='status']");

    public static final By quotesFilterListButton = By.xpath("//div[@id='simple-tabpanel-0']//button[@id='listGridFilter']");

    public static final By policiesFilterListButton = By.xpath("//div[@id='simple-tabpanel-1']//button[@id='listGridFilter']");

    public static final By filterByCoverageName = By.id("panel-header-1");

    public static final By submissionFilterByStatus = By.id("panel-header-2");

    public static final By submissionFilterByDateRange = By.xpath("//p[text()='Date Range']");

    public static final By allCoveragesDropdown = By.xpath("//label[text()='All Coverages']/following-sibling::div/div[@id='demo-simple-select']");

    public static final By coverageOptions = By.xpath("//ul/li[contains(@data-qa, 'menuItem')]");

    public static final By searchInputFiled = By.id("global_search");

    public static final By clearSearchInputFiled = By.xpath("//*[@data-testid='ClearIcon']");

    public static final By getFirstAvailableReferenceId = By.xpath("(//p[@data-qa='id'])[1]");

    public static final By noSearchResultsText = By.xpath("//div[@id='simple-tabpanel-1']//p[contains(text(),'Your search has returned no results.')]");

    public static final By getFirstAvailableLegalName = By.xpath("(//div[@data-qa='quote_card']//div[text()='Applicant']/following-sibling::div)[1]");

    public static final  By firstPolicyCardLegalName = By.xpath("(//div[contains(@class, 'policy_card')]//div[@data-qa='legalname'])[1]");

    public static final By policyLegalNames = By.xpath("//div[contains(@class, 'policy_card')]//div[@data-qa='legalname']");

    public static final By applyFiltersButton = By.id("applyFilters");

    public static final By clearFilterButton = By.xpath("//button[@id='filterClose' and text()='Clear Filters']");

    public static final By allStatusDropdown = By.xpath("//label[text()='All Statuses']/following-sibling::div/div[@id='demo-simple-select']");

    public static final By statusOptions = By.xpath("//ul/li[contains(@data-qa, 'menuItem')]");

    public static final By quoteCardGenericContinueButton = By.xpath("//div[@data-qa='quote_card']/div/div[last()]//button");

    public static final By policyAllStatusDropdown = By.xpath("//div[@data-qa='policyStatusSection']//div[@id='demo-simple-select']");

    public static final By policyFilterByStatus = By.id("panel-header-1");

    public static final By createdStartDateField = By.id("startdate");

    public static final By createdEndDateField = By.id("enddate");

    public static final By quoteCreatedDateGeneric = By.xpath("//p[@data-qa='created']");

    public static final By policyExpirationDateGeneric = By.xpath("//p[@data-qa='expDate']");

    public static final By firstAvailableRenewButton = By.xpath("//p[@class='sc-ezbkAF fLaTpy MuiTypography-root MuiTypography-body1']/div[1]/div/div[7]/div/button");

    public static final By quoteReferenceIdGenericLocator = By.xpath("//p[@data-qa='id']");

    public static final By firstAvailableStatus = By.xpath("//*[@id='simple-tabpanel-0']/div/p/div[1]/div[1]/div[7]/div/p");

    public static final By submitSubmissionRenewal = By.xpath("//button[normalize-space()='SUBMIT']");

    public static final By firstAvailableCreatedDate = By.xpath("(//p[@data-qa='created'])[1]");

    public static final By quoteSortBy = By.xpath("//div[@id='simple-tabpanel-0']//button[@id='sortByButton']//*[name()='svg']");

    public static final By policySortBy = By.xpath("//div[@id='simple-tabpanel-1']//button[@id='sortByButton']//*[name()='svg']");

    public static final By sortByNewest = By.xpath("//li[normalize-space()='Newest']");

    public static final By sortByOldest = By.cssSelector("li:nth-child(1)");

    public static final By sortByExpiringLater = By.xpath("//li[normalize-space()='Expiring Later']");

    public static final By sortByExpiringSoon = By.xpath("//li[normalize-space()='Expiring Soon']");

    public static final By exitRatingCriteria = By.xpath("//button[@id='rating_criteria-cancel']");

    public static final By statusInDashboard = By.xpath("//div[@data-qa='status']");

    public static final By myPolicyCardGenericContinueButton = By.xpath("//button[@type='button'][normalize-space()='Continue']");

    public static final By fistAvailableContinueButton = By.xpath("(//button[normalize-space()='Continue'])[1]");

    public static final By policyHeader = By.xpath("(//div[contains(text(),'Policy#')])[1]");

    public static final By clickFilterByStatus = By.id("panel-header-1");

    public static final By renewButton = By.xpath("(//button[normalize-space()='Renew'])[1]");

    public static final By genericRenewButtonLocator = By.xpath("//button[text()='Renew']");

    public static final By clearSearch = By.xpath("//*[@data-testid='ClearIcon']");

    public static final By getStatusText = By.xpath("//*[@data-qa='status']");

    public static final By clearanceDialogPolicyDashboard = By.xpath("//h2[@id='alert-dialog-title' and text()='Clearance']");

    public static final By clearanceDialogTextArea = By.id("outlined-textarea");

    public static final By clearanceDialogSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal' and text()='SUBMIT']/span");

    public static final By clearanceDialogCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal' and text()='CANCEL QUOTE']/span");

    public static final By quotesFilterByType = By.id("panel-header-3");

    public static final By allTypesDropdown = By.xpath("//label[text()='All Types']/following-sibling::div/div[@id='demo-simple-select']");

    public static final By quoteTypeOption = By.xpath("//ul//li[contains(@data-qa, 'quoteType_menuItem_')]");

    public static final By quoteBusinessType = By.xpath("//div[@data-qa='quote_card']/div/div//div[3]");

    public static final By quoteitLogo = By.xpath("//img[@alt='hello']");

    public static final By policySearchResults = By.xpath("//p[text()='Search results']/following-sibling::div[contains(@class, 'policy_card')]");

    /*** Submit for Review ***/
    public static final By contactUnderwriter = By.xpath("//button[normalize-space()='Contact Underwriter']");
    public static final By submitForReviewDesc = By.xpath("//p[@id='alert-dialog-description']//h1");
    public static final By submitForReviewCancel = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By submitForReviewSubmit = By.xpath("//button[@data-qa='submit_alert_modal']");

    public static final By underwriterReviewingButton = By.xpath("//button[text()='Underwriter Reviewing']");


}
