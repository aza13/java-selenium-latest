package pageObjects;

import org.openqa.selenium.By;

public class RatingCriteriaPageObjects {

    private RatingCriteriaPageObjects(){

    }

    public static final By ratingCriteriaHeader =By.xpath("//h4[text()=' Rating Criteria']");
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


}
