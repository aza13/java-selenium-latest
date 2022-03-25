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
    public static final By ratingCriteriaHeader =By.xpath("//h4[text()='Rating Criteria']");
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
    public static final By ratingCriteriaEffectiveDateSelectionButton = By.xpath("//button[@class='sc-hiCibw hLXiPf MuiButtonBase-root sc-dPiLbb bhziHj MuiIconButton-root MuiIconButton-edgeEnd MuiIconButton-sizeMedium']");
    public static final By ratingCriteriaEffectiveActualDateChoose = By.xpath("//button[normalize-space()='"+enterDate+"']");
    public static final By ratingCriteriaExpirationExpectedDateShow = By.xpath("//input[@value='"+expectDate+"']");
    public static final By ratingCriteriaCancelButton = By.xpath("//button[@id='rating_criteria-cancel']");



}
