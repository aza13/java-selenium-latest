package pageObjects;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RatingCriteriaPageObjects {

    static SimpleDateFormat enterDateFormat = new SimpleDateFormat("d");
    static Date d = new Date(System.currentTimeMillis());
    static String enterDate = enterDateFormat.format(d);
    static SimpleDateFormat expectDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static String expectDate = expectDateFormat.format(DateUtils.addYears(d,1));
    public static final By ratingCriteriaHeader =By.xpath("(//button[normalize-space()='Details'])[1]");
    public static final By ratingCriteriaHeader2 =By.xpath("//h5[text()='Effective Date and Business Class']");
    public static final By ratingCriteriaExitButton = By.id("rating_criteria-cancel");
    public static final By ratingCriteriaButton = By.id("workflow-tab-1");
    public static final By businessClassDropDown = By.id("rating-business-class-select");
    public static final By ratingCriteriaTitle = By.xpath("//div[contains(text(),'Rating Criteria')]");
    public static final By ratingCriteriaRevenueField = By.name("Revenue");
    public static final By ratingCriteriaRecordsField = By.name("Records");
    public static final By ratingCriteriaContinueButton = By.id("rating_criteria-continue");
    public static final By hardDeclineText = By.xpath("//div[contains(text(),'We’re sorry, we are not able to offer terms based ')]");
    public static final By ratingCriteriaOkButton = By.xpath("//button[normalize-space()='OK']");
    public static final By ratingCriteriaDropDownClearButton = By.xpath("//button[@title='Clear']");
    public static final By clearBusinessClassButton = By.xpath("//button[@title='Clear']");
    public static final By businessClassOption = By.xpath("//div[@role='presentation']");
    public static final By ratingCriteriaInputBox1 = By.xpath("//input[@placeholder='Number of Residential Units']");
    public static final By ratingCriteriaInputBox2 = By.xpath("//input[@placeholder='Total Commercial Square Feet']");
    public static final By ratingCriteriaEffectiveDateSelectionButton = By.xpath("//*[@id='panel1a-content']/div/div[1]/div[2]/div[1]/div[1]/div/div/button");
    public static final By ratingCriteriaEffectiveActualDateChoose = By.xpath("//button[normalize-space()='"+enterDate+"']");
    public static final By ratingCriteriaExpirationDateField= By.xpath("//*[@id='panel1a-content']/div/div[1]/div[2]/div[1]/div[2]/div/div/button");
    public static final By dropDownOnExpirationDatePicker = By.xpath("//button[@aria-label='calendar view is open, switch to year view']//*[name()='svg']");
    public static final By nextYearInExpirationDatePicker = By.xpath("//body/div[@role='dialog']/div/div/div/div[1]/div[2]/div/div/div[2]/button");
    public static final By ratingCriteriaCancelButton = By.xpath("//button[@id='rating_criteria-cancel']");
    public static final By detailsPageUnSelected = By.xpath("//button[@id='workflow-tab-1' and @aria-selected='false']");
    public static final By detailsPageSelected = By.xpath("//button[@id='workflow-tab-1' and @aria-selected='true']");
    public static final By ratingCriteriaNetWorthField = By.id("formatted_number");
    public static final By clickEditOnDetails = By.xpath("(//button[normalize-space()='Edit'])[1]");
    public static final By confirmMsgDetails = By.xpath("//div[contains(text(),'Updates to the data on these views will invalidate')]");
    public static final By confirmMsgOKDetails = By.xpath("(//button[normalize-space()='Ok'])[1]");
    public static final By confirmMsgCancelDetails = By.xpath("(//button[normalize-space()='Cancel'])[1]");
    public static final By ratingCriteriaNoPhysiciansField = By.name("Number of Physicians");
    public static final By hardDeclineMsg = By.cssSelector("p[id='alert-dialog-description'] div div p");
    public static final By hardDeclineOKButton = By.xpath("//button[normalize-space()='OK']");
    public static final By noOfPhysicians = By.name("Number of Physicians");
    public static final By clickNextMonth = By.xpath("//button[@title='Next month']");


}
