package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static pageObjects.BindingPageObjects.*;
import static pageObjects.QuoteListPageObjects.quoteExpiry;

public class BindingPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageActions.class);

    public void clickOnExitDashboard(WebDriver driver){

        ClickHelper.clickElement(driver, exitToDashboard);
    }

    public boolean isBindingTabSelected(WebDriver driver){

        return ClickHelper.isElementExist(driver, bindingTabSelected);
    }

    public boolean isPreSubjectivitiesDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, preSubjectivities);
    }

    public boolean isPostSubjectivitiesDisplayed(WebDriver driver) throws InterruptedException{
        WaitHelper.pause(10000);
        return ClickHelper.isElementExist(driver, postSubjectivities);
    }
}
