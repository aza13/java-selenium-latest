package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static pageObjects.BindingPageObjects.*;

public class BindingPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(BindingPageActions.class);

    public void clickOnExitDashboard(WebDriver driver){

        ClickHelper.clickElement(driver, exitToDashboard);
    }

    public boolean isBindingTabSelected(WebDriver driver){

        return ClickHelper.isElementExist(driver, bindingTabSelected);
    }


}
