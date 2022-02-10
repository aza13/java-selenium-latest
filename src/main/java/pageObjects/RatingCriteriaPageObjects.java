package pageObjects;

import org.openqa.selenium.By;

public class RatingCriteriaPageObjects {

    private RatingCriteriaPageObjects(){

    }

    public static final By ratingCriteriaHeader =By.xpath("//h4[text()=' Rating Criteria']");
    public static final By ratingCriteriaExitButton = By.id("rating_criteria-cancel");
    public static final By ratingCriteriaButton = By.xpath("//button[@id='workflow-tab-1']");
    public static final By businessClassDropDown = By.xpath("//input[@id='rating-business-class-select']");
    public static final By ratingCriteriaTitle = By.xpath("//div[contains(text(),'Rating Criteria')]");
    public static final By ratingCriteriaRevenueField = By.name("Revenue");
    public static final By ratingCriteriaRecordsField = By.name("Records");
    public static final By ratingCriteriaContinueButton = By.id("rating_criteria-continue");
}
