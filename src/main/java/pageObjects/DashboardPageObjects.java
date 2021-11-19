package pageObjects;

import org.openqa.selenium.By;

public class DashboardPageObjects {

    private DashboardPageObjects(){}

    public static final By tmhccLogo = By.xpath("//header//div/img");

    public static final By profileSettings = By.id("basic-button");

    public static final By profileLink = By.id("action_item_profile");

    public static final By signOutLink = By.id("action_item_signout");

    public static final By brokerIdField = By.xpath("(//input[@id='filled-basic'])[1]");

    public static final By agencyIdField = By.xpath("(//input[@id='filled-basic'])[2]");

    public static final By myQuotesTab = By.id("simple-tab-0");

    public static final By myPoliciesTab = By.id("simple-tab-1");

    public static final By submissionsList = By.xpath("//div[@id='listGridHeader']/following-sibling::div");

    public static final By submissionListLabels = By.xpath("(//div[@id='listGridHeader']/following-sibling::div/div)[1]/div");

    public static final By submission_ref_label = By.xpath("//div[text()='Reference']");

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




}
