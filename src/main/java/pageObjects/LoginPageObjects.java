package pageObjects;

import org.openqa.selenium.By;

public class LoginPageObjects {

    private LoginPageObjects(){}

    public static final By welcomeText = By.tagName("h1");
    public static final By signUpButton = By.xpath("//button[text()='Sign Up']/span");
    public static final By emailTextField = By.id("sign-in-email-text-field");
    public static final By passwordTextField = By.id("sign-in-password-text-field");
    public static final By logInButton = By.xpath("//button[text()='Log in']");
    public static final By provideEmailPasswordText = By.xpath("//p[text()='Please provide an email and password.']");
    public static final By invalidUsernamePassword = By.xpath("//p[text()='Invalid email.']");
}
