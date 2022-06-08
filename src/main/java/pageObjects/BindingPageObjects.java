package pageObjects;

import org.openqa.selenium.By;

public class BindingPageObjects {

    private BindingPageObjects(){}

    public static final By exitToDashboard =By.id("quote_builder_exit");
    public static final By bindingTabSelected = By.xpath("//button[text()='Binding' and @aria-selected='true']");
    public static final By proposedPolicyPeriod = By.xpath("//h4[contains(text(), 'Proposed Policy Period:')]");
    public static final By quoteHeaderInformation = By.xpath("//div[@id='simple-tabpanel-4']//h4");
    public static final By policyExpandMoreIcon = By.xpath("(//*[@data-testid='ExpandMoreIcon'])[1]");

    public static final By preSubjectivities =By.xpath("//h5[contains(text(),'Subjectivities due prior to binding')]");
    public static final By postSubjectivities =By.xpath("//h5[contains(text(),'Subjectivities due within 7 days of effective date')]");
    public static final By firstMessageToUWTextArea = By.xpath("(//div[@id='panel1a-content']//textarea)[1]");
    public static final By disabledSubmitButton = By.xpath("//button[@id='underwriter_message_submit' and @disabled]");
    public static final By enabledSubmitButton = By.xpath("//button[@id='underwriter_message_submit']");
    public static final By confirmationDialog = By.xpath("//h2[@id='alert-dialog-title' and text()='Confirmation']");
    public static final By submitConfirmationButton = By.xpath("//button[@data-qa='submit_alert_modal']");
}
