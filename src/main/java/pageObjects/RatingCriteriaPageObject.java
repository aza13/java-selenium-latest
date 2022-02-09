package pageObjects;

import org.openqa.selenium.By;

public class RatingCriteriaPageObject {

    public static final By ratingCriteriaButton = By.xpath("//button[@id='workflow-tab-1']");
    public static final By businessClassDropDown = By.xpath("//input[@id='rating-business-class-select']");
    public static final By ratingCriteriaTitle = By.xpath("//div[contains(text(),'Rating Criteria')]");
    public static final By ratingCriteriaInputBox1 = By.xpath("//input[@placeholder='Number of Residential Units']");
    public static final By ratingCriteriaInputBox2 = By.xpath("//input[@placeholder='Total Commercial Square Feet']");
    public static final By ratingCriteriaContinueButton = By.xpath("//button[@id='rating_criteria-continue']");

}
