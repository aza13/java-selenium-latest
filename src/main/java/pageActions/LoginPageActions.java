package pageActions;

import base.BaseTest;
import base.PageObjectManager;
import helper.ClickHelper;
import helper.TextHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pageObjects.LoginPageObjects.*;

public class LoginPageActions extends BaseTest {

    private static final Logger logger = Logger.getLogger(LoginPageActions.class);

    public String getWelcomeText(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return TextHelper.getText(driver, welcomeText, "text");
    }

    public DashboardPageActions loginApp(WebDriver driver, String email_id, String password) {

        TextHelper.enterText(driver, emailTextField, email_id);
        TextHelper.enterText(driver, passwordTextField, password);
        ClickHelper.clickElement(driver, logInButton);
        return PageObjectManager.getDashboardPageActions();
    }

    public WebElement pleaseProvideEmailPasswordText(WebDriver driver) {

        return driver.findElement(provideEmailPasswordText);
    }

    public WebElement invalidUserNamePasswordText(WebDriver driver) throws InterruptedException {
        Thread.sleep(3000);
        return driver.findElement(invalidUsernamePassword);
    }

    public boolean  resetPassword (WebDriver driver, String email_id) {

        ClickHelper.clickElement(driver, forgetPasswordLink);
        TextHelper.enterText(driver, forgotEmailTextField, email_id);
        ClickHelper.clickElement(driver, sendForgotPasswordLink);
        return ClickHelper.isElementExist(driver, confirmNewPassword);
    }

}
