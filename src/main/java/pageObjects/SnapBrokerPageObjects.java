package pageObjects;

import org.openqa.selenium.By;

public class SnapBrokerPageObjects {

    private SnapBrokerPageObjects(){}

    // Create New Broker Dialog Objects

    public static final By brokerName = By.name("name");
    public static final By brokerEmail = By.name("email");
    public static final By infContactNumber = By.name("infinityContactNumber");
    public static final By infContactId = By.name("infinityContactId");
    public static final By phoneNumber = By.name("phone");
    public static final By agencyOffice = By.xpath("//input[contains(@class, 'agency-search-input')]");
    public static final By agencyFirstOption = By.xpath("//div[contains(@class, 'option-0 row')]");
    public static final By agencySearchButton = By.xpath("(//button[contains(@class, 'search-button') and text()='SEARCH'])[2]");
    public static final By submitButton = By.xpath("//button[text()='Submit']");


}
