package pageActions;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageObjects.InsuredPageObjects.*;

public class InsuredPageActions extends BaseTest {

    public WebElement newInsuredButton(WebDriver driver){

        return driver.findElement(newInsuredButton);
    }

    public WebElement modifySearchButton(WebDriver driver){

        return driver.findElement(modifySearchButton);
    }

    public WebElement cancelButton(WebDriver driver){

        return driver.findElement(cancelButton);
    }

    public WebElement continueButton(WebDriver driver){

        return driver.findElement(continueButton);
    }

}
