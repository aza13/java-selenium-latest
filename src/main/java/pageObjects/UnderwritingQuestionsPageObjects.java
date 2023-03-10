package pageObjects;

import org.openqa.selenium.By;

public class UnderwritingQuestionsPageObjects {

    private UnderwritingQuestionsPageObjects(){

    }

    public static final By underwritingQuestionsHeader =By.xpath("(//button[normalize-space()='Underwriter Questions'])[1]");
    public static final By uwQuestionsContinueButton = By.xpath("//button[@id='underwriting-continue']/span");
    public static final By allQuestionsAnsweredText = By.xpath("//h3[text()='All questions have been answered!']");
    public static final By quotesTab = By.id("workflow-tab-3");
    public static final By underWriterQuestionTabs = By.xpath("//div[@id='underwriting-questions-header' and @role='button']");
    public static final By uwQuestionsDropdownsOption = By.xpath("//ul/li[@role='option']");
    public static final By uwQuestionsAnswerRequiredIcon = By.xpath("//*[@data-testid='GppMaybeIcon']");
    public static final By quoteTemplateOptions = By.xpath("//ul//li[@role='menuitem']");
    public static final By headerGeneral = By.xpath("//*[contains(text(),'General')]");
    public static final By headerGeneralQuestion = By.xpath("(//button[@type='button'][normalize-space()='No'])[1]");
    public static final By headerEnhancements = By.xpath("//h5[normalize-space()='Enhancements']");
    public static final By headerEnhancementsQuestion = By.xpath("(//button[@type='button'][normalize-space()='No'])[2]");
    public static final By headerRequiredQuestions = By.xpath("//h5[normalize-space()='Required Questions']");
    public static final By headerRequiredQuestions1 = By.xpath("(//button[@type='button'][normalize-space()='No'])[3]");
    public static final By headerRequiredQuestions2 = By.xpath("(//button[@type='button'][normalize-space()='No'])[4]");
    public static final By headerRequiredQuestions3 = By.xpath("(//button[@type='button'][normalize-space()='No'])[5]");
    public static final By headerRequiredQuestions4 = By.xpath("(//button[@type='button'][normalize-space()='No'])[6]");
    public static final By headerRequiredQuestions5 = By.xpath("(//button[@type='button'][normalize-space()='No'])[7]");
    public static final By severityPatches = By.xpath("(//div[@id='Required Questions-select'])[1]");
    public static final By severityPatchesValue = By.xpath("(//li[normalize-space()='4-7 Days'])[1]");
    public static final By postureVulnerability = By.xpath("(//div[@id='Required Questions-select'])[2]");
    public static final By postureVulnerabilityValue = By.xpath("(//li[normalize-space()='1'])[1]");
    public static final By connectedDB = By.xpath("(//div[@id='Required Questions-select'])[3]");
    public static final By connectedDBValue = By.xpath("(//li[normalize-space()='Not Detected'])[1]");

    public static final By headerITDept = By.xpath("//h5[normalize-space()='IT Department']");
    public static final By secDesgination = By.xpath("(//button[@type='button'][normalize-space()='No'])[8]");
    public static final By itPersonnel = By.xpath("(//button[@type='button'][normalize-space()='No'])[9]");
    public static final By secPersonnel = By.xpath("(//button[@type='button'][normalize-space()='No'])[10]");
    public static final By networkSec = By.xpath("(//div[@id='IT Department-select'])[1]");
    public static final By networkSecValue = By.xpath("(//li[normalize-space()='Outsourced'])[1]");

    public static final By headerInternalSec = By.xpath("//h5[normalize-space()='Internal Security']");
    public static final By mgmtSoftware = By.xpath("(//button[@type='button'][normalize-space()='No'])[11]");
    public static final By monitorAdmin = By.xpath("(//button[@type='button'][normalize-space()='No'])[12]");
    public static final By mobileDevice = By.xpath("(//button[@type='button'][normalize-space()='No'])[13]");
    public static final By assetDeployed = By.xpath("(//button[@type='button'][normalize-space()='No'])[14]");
    public static final By nonUTUsers = By.xpath("(//button[@type='button'][normalize-space()='No'])[15]");
    public static final By restNetwork = By.xpath("(//button[@type='button'][normalize-space()='No'])[16]");
    public static final By dnsService = By.xpath("(//button[@type='button'][normalize-space()='No'])[17]");
    public static final By technology = By.xpath("(//button[@type='button'][normalize-space()='No'])[18]");
    public static final By systemDefault = By.xpath("(//button[@type='button'][normalize-space()='No'])[19]");
    public static final By bestPractise = By.xpath("(//button[@type='button'][normalize-space()='No'])[20]");
    public static final By siem = By.xpath("(//button[@type='button'][normalize-space()='No'])[21]");

    public static final By clickContinueMsg = By.xpath("//li[@value='$1MM/$1MM']");
    public static final By questionsPageUnSelected = By.xpath("//button[@id='workflow-tab-2' and @aria-selected='false']");
    public static final By questionsPageSelected = By.xpath("//button[@id='workflow-tab-2' and @aria-selected='true']");



    public static final By exitQuestionButton = By.id("underwriting-cancel");
    public static final By clickEditOnQuestions = By.xpath("(//button[normalize-space()='Edit'])[1]");
    public static final By confirmMsg = By.xpath("//div[contains(text(),'Updates to the data on these views will invalidate')]");
    public static final By confirmMsgOK = By.xpath("(//button[normalize-space()='Ok'])[1]");
    public static final By underwritingQuestionsTab = By.xpath("(//button[normalize-space()='Questions'])[1]");
    public static final By uwMSGWhileContinue = By.xpath("//li[@value='NetGuard Select Celebrity']");

    public static final By softDeclineHeader = By.xpath("(//h2[normalize-space()='Submit for Review'])[1]");
    public static final By softDeclineText = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By softDeclineSubmit = By.xpath("//button[normalize-space()='SUBMIT']");

    /** submit for review modal**/
    public static final By submitReviewDialog = By.xpath("//h2[text()='Submit for Review']");
    public static final By submitReviewDialogText = By.xpath("//p[@id='alert-dialog-description']//div/h1");
    public static final By submitReviewTextArea = By.xpath("//textarea[@id='outlined-textarea']");
    public static final By submitReviewCancelButton = By.xpath("//button[@data-qa='cancel_alert_modal']");
    public static final By submitReviewSubmitButton = By.xpath("//button[@data-qa='submit_alert_modal']");
    public static final By clickAndDragLink = By.xpath("//p[contains(text(), 'Click or Drag')]");
    public static final By singleFileMaximumSizeText = By.xpath("//p[starts-with(text(), 'A single file cannot be larger than 2MB ')]");
    public static final By invalidFileTypeWarning = By.xpath("//p[contains(text(), 'File can only be of the file types')]");

    public static final By deleteIconLocator = By.xpath("//*[@data-testid='DeleteOutlineIcon']");
}
