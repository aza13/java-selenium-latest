package pageObjects;

import org.openqa.selenium.By;

public class RatingCriteriaPageObjects {

    private RatingCriteriaPageObjects(){

    }

    public static final By ratingCriteriaHeader =By.xpath("//h4[text()=' Rating Criteria']");
    public static final By ratingCriteriaExitButton = By.id("rating_criteria-cancel");
}
