package pageActions;

import base.BaseTest;
import base.DriverManager;
import helper.ClickHelper;
import helper.ScrollHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static pageObjects.UnderwritingQuestionsPageObjects.*;


public class UnderwritingQuestionsPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageActions.class);

    public boolean isUnderwritingQuestionsPageDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, underwritingQuestionsHeader);
       return ClickHelper.isElementExist(driver, underwritingQuestionsHeader);
    }

    public boolean isGeneralHeaderDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, headerGeneral);
        return ClickHelper.isElementExist(driver, headerGeneral);
    }

    public void clickGeneralHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerGeneral);
        ClickHelper.clickElement(driver, headerGeneral);
        WaitHelper.pause(3000);
        WaitHelper.waitForElementVisibility(driver, headerGeneralQuestion);
        ClickHelper.clickElement(driver, headerGeneralQuestion);

    }

    public boolean isEnhancementsHeaderDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, headerEnhancements);
        return ClickHelper.isElementExist(driver, headerEnhancements);
    }

    public void clickEnhancementsHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerEnhancements);
        ClickHelper.clickElement(driver, headerEnhancements);
        WaitHelper.pause(3000);
        WaitHelper.waitForElementVisibility(driver, headerEnhancementsQuestion);
        ClickHelper.clickElement(driver, headerEnhancementsQuestion);
    }

    public boolean isRequiredHeaderDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions);
        return ClickHelper.isElementExist(driver, headerRequiredQuestions);
    }

    public void clickRequiredHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions);
        ClickHelper.clickElement(driver, headerRequiredQuestions);
        WaitHelper.pause(3000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions1);
        ClickHelper.clickElement(driver, headerRequiredQuestions1);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions2);
        ClickHelper.clickElement(driver, headerRequiredQuestions2);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions3);
        ClickHelper.clickElement(driver, headerRequiredQuestions3);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), headerRequiredQuestions3);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibility(driver, severityPatches);
        ClickHelper.clickElement(driver, severityPatches);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibility(driver, severityPatchesValue);
        ClickHelper.clickElement(driver, severityPatchesValue);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions4);
        ClickHelper.clickElement(driver, headerRequiredQuestions4);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions5);
        ClickHelper.clickElement(driver, headerRequiredQuestions5);
        WaitHelper.waitForElementVisibility(driver, postureVulnerability);
        ClickHelper.clickElement(driver, postureVulnerability);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibility(driver, postureVulnerabilityValue);
        ClickHelper.clickElement(driver, postureVulnerabilityValue);
        WaitHelper.waitForElementVisibility(driver, connectedDB);
        ClickHelper.clickElement(driver, connectedDB);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibility(driver, connectedDBValue);
        ClickHelper.clickElement(driver, connectedDBValue);
    }

    public boolean isITDepartmentHeaderDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, headerITDept);
        return ClickHelper.isElementExist(driver, headerITDept);
    }

    public void clickITDepartmentHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerITDept);
        ClickHelper.clickElement(driver, headerITDept);
        WaitHelper.pause(3000);
        WaitHelper.waitForElementVisibility(driver, secDesgination);
        ClickHelper.clickElement(driver, secDesgination);
        WaitHelper.waitForElementVisibility(driver, networkSec);
        WaitHelper.pause(5000);
        ClickHelper.clickElement(driver, networkSec);
        WaitHelper.waitForElementVisibility(driver, networkSecValue);
        ClickHelper.clickElement(driver, networkSecValue);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(),itPersonnel);
        WaitHelper.waitForElementVisibility(driver, itPersonnel);
        ClickHelper.clickElement(driver, itPersonnel);
        WaitHelper.waitForElementVisibility(driver, secPersonnel);
        ClickHelper.clickElement(driver, secPersonnel);

    }

    public boolean isInternalSecurityHeaderDisplayed(WebDriver driver){
        WaitHelper.waitForElementVisibility(driver, headerInternalSec);
        return ClickHelper.isElementExist(driver, headerInternalSec);
    }

    public void clickInternalSecurityHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerInternalSec);
        ClickHelper.clickElement(driver, headerInternalSec);
        WaitHelper.pause(3000);
        WaitHelper.waitForElementVisibility(driver, mgmtSoftware);
        ClickHelper.clickElement(driver, mgmtSoftware);
        WaitHelper.waitForElementVisibility(driver, monitorAdmin);
        ClickHelper.clickElement(driver, monitorAdmin);
        WaitHelper.waitForElementVisibility(driver, mobileDevice);
        ClickHelper.clickElement(driver, mobileDevice);
        WaitHelper.waitForElementVisibility(driver, assetDeployed);
        ClickHelper.clickElement(driver, assetDeployed);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), assetDeployed);
        WaitHelper.waitForElementVisibility(driver, nonUTUsers);
        ClickHelper.clickElement(driver, nonUTUsers);
        WaitHelper.waitForElementVisibility(driver, restNetwork);
        ClickHelper.clickElement(driver, restNetwork);
        WaitHelper.waitForElementVisibility(driver, dnsService);
        ClickHelper.clickElement(driver, dnsService);
        WaitHelper.waitForElementVisibility(driver, technology);
        ClickHelper.clickElement(driver, technology);
        WaitHelper.waitForElementVisibility(driver, systemDefault);
        ClickHelper.clickElement(driver, systemDefault);
        WaitHelper.waitForElementVisibility(driver, bestPractise);
        ClickHelper.clickElement(driver, bestPractise);
        WaitHelper.waitForElementVisibility(driver, siem);
        ClickHelper.clickElement(driver, siem);

    }

    public void clickUWQuestionsContinueButton(WebDriver driver){
        WaitHelper.waitForElementClickable(driver, clickContinueUWContinueButton);
        ClickHelper.clickElement(driver, clickContinueUWContinueButton);
    }


}
