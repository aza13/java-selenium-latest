package pageObjects;

import org.openqa.selenium.By;

public class SnapPolicyPageObjects {

    private SnapPolicyPageObjects(){}

    public static final By policySearch = By.xpath("//input[@placeholder='Search Policies']");

    public static final By policySearchButton = By.xpath("//span[text()='Search']");


}
