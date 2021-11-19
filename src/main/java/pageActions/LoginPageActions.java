package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.TextHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static pageObjects.LoginPageObjects.*;

public class LoginPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(LoginPageActions.class);

    public String getWelcomeText(WebDriver driver){

        return TextHelper.getText(driver, welcomeText, "text");
    }

    public void login(WebDriver driver, String email_id, String password){

        TextHelper.enterText(driver, emailTextField, email_id);
        TextHelper.enterText(driver, passwordTextField, password);
        ClickHelper.clickElement(driver, signInButton);
        PageObjectManager.getDashboardPageActions();
    }
}
