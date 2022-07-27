package pageObjects;

import org.openqa.selenium.By;

public class BindingPageObjects {

    private BindingPageObjects(){}

    public static final By exitToDashboard =By.id("quote_builder_exit");
    public static final By bindingTabSelected = By.xpath("//button[text()='Binding' and @aria-selected='true']");
    public static final By proposedPolicyPeriod = By.xpath("//h4[contains(text(), 'Proposed Policy Period:')]");
    public static final By quoteHeaderInformation = By.xpath("//div[@id='simple-tabpanel-4']//h4");
    public static final By policyExpandMoreIcon = By.xpath("(//*[@data-testid='ExpandMoreIcon'])[1]");

    public static final By preSubjectivities =By.xpath("//p[contains(text(),'Subjectivities due prior to binding')]");
    public static final By postSubjectivities =By.xpath("//p[contains(text(),'Subjectivities due within 7 days of effective date')]");
    public static final By firstMessageToUWTextArea = By.xpath("(//div[@id='panel1a-content']//textarea)[1]");
    public static final By disabledSubmitButton = By.xpath("//button[@id='underwriter_message_submit' and @disabled]");
    public static final By enabledSubmitButton = By.xpath("//button[@id='underwriter_message_submit']");
    public static final By confirmationDialog = By.xpath("//h2[@id='alert-dialog-title' and text()='Confirmation']");
    public static final By submitConfirmationButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By messageToUnderWriter =By.xpath("//p[contains(text(),'Message to Underwriter')]");
    public static final By messageToPreSubjectivitiesUnderWriterTextBox =By.xpath("(//div[@id='panel1a-content']//textarea)[1]");
    public static final By postSubjectivitiesExpandButton =By.xpath("//p[contains(text(),'Subjectivities due within 7 days of effective date')]/../../..//*[@id='panel1a-header']/div[2]");
    public static final By messageToPostSubjectivitiesUnderWriterTextBox =By.xpath("//p[contains(text(),'Subjectivities due within 7 days of effective date')]/../../..//textarea[1]");
    public static final By preSubjSelectFilesButton = By.xpath("(//button[@id='btn-search-again'])[1]");
    public static final By postSubjSelectFilesButton = By.xpath("(//button[@id='btn-search-again'])[2]");

    public static final By clickAndDragLink = By.xpath("//p[contains(text(), 'Click or Drag')]");
    public static final By addFilesButton = By.xpath("//button[text()='Add File(s)']");
    public static final By fileDeleteIcon = By.xpath("//*[@data-testid='DeleteOutlineIcon']");
    public static final By filePresentIcon = By.xpath("//*[@data-testid='FilePresentIcon']");
    public static final By rejectedStatus = By.xpath("//div[contains(text(),'Rejected')]");
    public static final By WaivedStatus = By.xpath("//div[contains(text(),'Waived')]");
    public static final By AcceptedStatus = By.xpath("//div[contains(text(),'Accepted')]");
    public static final By invalidFileTypeWarning = By.xpath("//p[contains(text(), 'Files can only be of the file types')]");
    public static final By BinderText = By.xpath("//p[contains(text(),'Binder has been issued')]");
    public static final By PreBinderText = By.xpath("//p[contains(text(),'Binder will be issued shortly')]");
    public static final By fileSizeExceededText = By.xpath("//p[starts-with(text(), 'You have exceeded the maximum 10MB')]");

}
