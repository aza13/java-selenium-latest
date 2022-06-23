package pageObjects;
import org.openqa.selenium.By;
public class InsuredPageObjects {

    private InsuredPageObjects(){

    }

    public static final By newInsuredButton = By.id("btn-new-insured");
    public static final By searchResults = By.xpath("//p[contains(text(), 'Search Results')]");
    public static final By modifySearchNameField = By.id("txt-modify-search-name");
    public static final By modifySearchWebsiteField = By.id("txt-modify-search-website");
    public static final By searchAgainButton = By.id("btn-search-again");
    public static final By continueInsuredButton = By.xpath("//button[@data-qa='insured_select']");
    public static final By cancelInsuredSearchButton = By.id("btn-search-cancel");
    public static final By continueInsuredSearchButton = By.id("btn-insured-continue");
    public static final By modifySearchButton = By.xpath("//button[text()='Modify Search']");
    public static final By createNewInsuredInfoText = By.xpath("//p[contains(text(),'Please create a new applicant to continue')]");
    public static final By insuredNameField = By.id("insured_name");
    public static final By insuredWebsiteField = By.id("insured_website");
    public static final By insuredPhoneNumberField = By.id("insured_form_phNumber");
    public static final By insuredFormEmailField = By.id("insured_form_email");
    public static final By insuredPhysicalAddField = By.id("insured_p_address");
    public static final By insuredPhysicalCityField = By.id("insured_p_city");
    public static final By insuredPhysicalZipCode = By.id("insured_p_code");
    public static final By emailRequiredText = By.id("insured_form_email-helper-text");
    public static final By physicalAddressReqText = By.id("insured_p_address-helper-text");
    public static final By physicalCityReqText = By.id("insured_p_city-helper-text");
    public static final By physicalStateReText = By.id("insured_p_state-helper-text");
    public static final By physicalZipcodeReqText = By.id("insured_p_code-helper-text");
    public static final By mailingAddressReqText = By.id("insured_m_address-helper-text");
    public static final By mailingCityReqText = By.id("insured_m_city-helper-text");
    public static final By mailingStateReText = By.id("insured_p_state-helper-text");
    public static final By mailingZipcodeReqText = By.id("insured_m_code-helper-text");
    public static final By physicalStateDropdown = By.id("insured_p_state");
    public static final By physicalStateOptions = By.xpath("//ul/li[contains(@id, 'insured_p_state-option')]");
    public static final By sameAsPhyAddressCheckbox = By.xpath("//input[@type='checkbox']");
    public static final By cancelInsuredFormButton = By.id("btn-insured-form-cancel");
    public static final By continueInsuredFormButton = By.id("btn-insured-form-create");
    public static final By clearInsuredNameButton = By.xpath("//button[@aria-label='Insured Name']");
    public static final By clearInsuredWebsiteButton = By.xpath("//button[@aria-label='Website']");
    public static final By duplicateSubmissionDialog = By.id("alert-dialog-title");
    public static final By duplicateSubDialogDescription = By.id("alert-dialog-description");
    public static final By duplicateDialogCancelButton = By.xpath("//button[text()='CANCEL']/span");
    public static final By insuredNameInCard = By.xpath("//div[@data-qa='insured_name']");
    public static final By loadingSpinnerIcon = By.xpath("//p[text()='loading..']");
    public static final By clearanceDialogModal = By.xpath("//h2[@id='alert-dialog-title' and text()='Clearance']");
    public static final By clearanceSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By clearanceCancelQuoteButton = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By clearanceDialogTextArea = By.id("outlined-textarea");
    public static final By insuranceCardGenericLocator = By.xpath("//div[contains(@class, 'MuiCard')]");
    public static final By clickAndDragLink = By.xpath("//p[text()='Click or Drag Files to Upload']");

}
