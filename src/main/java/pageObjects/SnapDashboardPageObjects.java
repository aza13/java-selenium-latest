package pageObjects;

import org.openqa.selenium.By;

public class SnapDashboardPageObjects {

    private SnapDashboardPageObjects(){}

    public static final By brokerMenu = By.xpath("//button[contains(@class, 'brokers-button')]");

    public static final By createBroker = By.xpath("//div[text()='Create a Broker']");

    public static final By policyMenu = By.xpath("//button[text()='Policies']/i");

    public static final By policyDashboard = By.xpath("//div[text()='Policies Dashboard']");


}
