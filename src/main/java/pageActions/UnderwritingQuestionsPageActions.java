package pageActions;

import base.BaseTest;
import base.DriverManager;
import helper.ClickHelper;
import helper.DropdownHelper;
import helper.ScrollHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageObjects.DashboardPageObjects.exitRatingCriteria;
import static pageObjects.UnderwritingQuestionsPageObjects.*;
import static pageObjects.UnderwritingQuestionsPageObjects.clickEditOnQuestions;


public class UnderwritingQuestionsPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageActions.class);
    public static int globalIndex=0;
    public static int dropdownsIndex =0;

    public boolean isUnderwritingQuestionsPageDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, questionsPageSelected);
    }

    public boolean isGeneralHeaderDisplayed(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, headerGeneral);
        return ClickHelper.isElementExist(driver, headerGeneral);
    }

    public void clickGeneralHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerGeneral);
        ClickHelper.clickElement(driver, headerGeneral);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerGeneralQuestion);
        ClickHelper.clickElement(driver, headerGeneralQuestion);
        WaitHelper.pause(6000);

    }

    public boolean isEnhancementsHeaderDisplayed(WebDriver driver) {
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

    public boolean isRequiredHeaderDisplayed(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions);
        return ClickHelper.isElementExist(driver, headerRequiredQuestions);
    }

    public void clickRequiredHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions);
        ClickHelper.clickElement(driver, headerRequiredQuestions);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions1);
        ClickHelper.clickElement(driver, headerRequiredQuestions1);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions2);
        ClickHelper.clickElement(driver, headerRequiredQuestions2);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions3);
        ClickHelper.clickElement(driver, headerRequiredQuestions3);
        WaitHelper.pause(6000);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), headerRequiredQuestions3);
        WaitHelper.pause(2000);
        WaitHelper.waitForElementVisibility(driver, severityPatches);
        ClickHelper.clickElement(driver, severityPatches);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, severityPatchesValue);
        ClickHelper.clickElement(driver, severityPatchesValue);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions4);
        ClickHelper.clickElement(driver, headerRequiredQuestions4);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, headerRequiredQuestions5);
        ClickHelper.clickElement(driver, headerRequiredQuestions5);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, postureVulnerability);
        ClickHelper.clickElement(driver, postureVulnerability);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, postureVulnerabilityValue);
        ClickHelper.clickElement(driver, postureVulnerabilityValue);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, connectedDB);
        ClickHelper.clickElement(driver, connectedDB);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, connectedDBValue);
        ClickHelper.clickElement(driver, connectedDBValue);
        WaitHelper.pause(6000);
    }

    public boolean isITDepartmentHeaderDisplayed(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, headerITDept);
        return ClickHelper.isElementExist(driver, headerITDept);
    }

    public void clickITDepartmentHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerITDept);
        ClickHelper.clickElement(driver, headerITDept);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, secDesgination);
        ClickHelper.clickElement(driver, secDesgination);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, networkSec);
        WaitHelper.pause(6000);
        ClickHelper.clickElement(driver, networkSec);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, networkSecValue);
        ClickHelper.clickElement(driver, networkSecValue);
        WaitHelper.pause(6000);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(),itPersonnel);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), itPersonnel);
        WaitHelper.waitForElementVisibility(driver, itPersonnel);
        ClickHelper.clickElement(driver, itPersonnel);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, secPersonnel);
        ClickHelper.clickElement(driver, secPersonnel);
        WaitHelper.pause(6000);

    }

    public boolean isInternalSecurityHeaderDisplayed(WebDriver driver) {
        WaitHelper.waitForElementVisibility(driver, headerInternalSec);
        return ClickHelper.isElementExist(driver, headerInternalSec);
    }

    public void clickInternalSecurityHeader(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, headerInternalSec);
        ClickHelper.clickElement(driver, headerInternalSec);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, mgmtSoftware);
        ClickHelper.clickElement(driver, mgmtSoftware);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, monitorAdmin);
        ClickHelper.clickElement(driver, monitorAdmin);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, mobileDevice);
        ClickHelper.clickElement(driver, mobileDevice);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, assetDeployed);
        ClickHelper.clickElement(driver, assetDeployed);
        WaitHelper.pause(6000);
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), assetDeployed);
        WaitHelper.waitForElementVisibility(driver, nonUTUsers);
        ClickHelper.clickElement(driver, nonUTUsers);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, restNetwork);
        ClickHelper.clickElement(driver, restNetwork);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, dnsService);
        ClickHelper.clickElement(driver, dnsService);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, technology);
        ClickHelper.clickElement(driver, technology);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, systemDefault);
        ClickHelper.clickElement(driver, systemDefault);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, bestPractise);
        ClickHelper.clickElement(driver, bestPractise);
        WaitHelper.pause(6000);
        WaitHelper.waitForElementVisibility(driver, siem);
        ClickHelper.clickElement(driver, siem);
        WaitHelper.pause(6000);
    }

    public void clickUWQuestionsContinueButton(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, uwQuestionsContinueButton);
        ScrollHelper.scrollElementIntoView(driver, uwQuestionsContinueButton);
        ClickHelper.clickElement(driver, uwQuestionsContinueButton);
        WaitHelper.waitForElementVisibility(driver, uwMSGWhileContinue);
        ClickHelper.clickElement(driver, uwMSGWhileContinue);
        WaitHelper.pause(20000);

    }

    public boolean checkWhetherAllUWQuestionsAreAnswered(WebDriver driver) {
        String xpath = "//button[@id='underwriting-continue' and @disabled]";
        By disableLocator = By.xpath(xpath);
        if(ClickHelper.isElementExist(driver, disableLocator)){
            logger.info("UW continue button is disabled, need to answer UW questions");
            return false;
        }else{
            logger.info("UW continue button is enabled, UW questions are answered");
            return true;
        }
    }


    public void clickQuotesTab(WebDriver driver) {
        ClickHelper.javaScriptExecutorClick(driver, quotesTab);
    }

    public void answerUWQuestionButtons(WebDriver driver, String uwQuestionAnswer) throws InterruptedException {
        String buttonXpath = "//div[@id='underwriting-questions-header']//button[text()='" + uwQuestionAnswer + "']";
        try {
            List<WebElement> allNoButtons = driver.findElements(By.xpath(buttonXpath));
            int count = allNoButtons.size();
            for (int i=globalIndex; i< count; i++) {
                logger.info("clicking on each yes/no button in each UW section");
                WaitHelper.pause(5000);
                allNoButtons.get(i).click();
                globalIndex++;
            }
        }catch (StaleElementReferenceException e) {
            WaitHelper.pause(3000);
            answerUWQuestionButtons(driver, uwQuestionAnswer);
        }

    }

    public void answerUWQuestionDropdowns(WebDriver driver, String answer, String dropdownOption) throws InterruptedException {

        String[] dropdownIds = {"General-select", "Required Questions-select", "IT Department-select"};
        try{
            logger.info("clicking on UW question tab");
            for (String dropdownId : dropdownIds) {
                List<WebElement> dropdowns = driver.findElements(By.id(dropdownId));
                if (dropdowns.size() > 0) {
                    for (WebElement dropdown : dropdowns) {
                        logger.info("selecting the value from dropdown");
                        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, dropdownOption);
                        WaitHelper.pause(5000);
                        dropdownsIndex++;
                    }
                }
            }
        }catch (StaleElementReferenceException e) {
            WaitHelper.pause(3000);
            answerUWQuestionDropdowns(driver, answer, dropdownOption);
        }
    }

    public void selectQuoteTemplateOption(WebDriver driver, String option) {
        logger.info("selecting the given quote template option");
        List<WebElement> options = driver.findElements(quoteTemplateOptions);
        for (WebElement opt : options) {
            if (opt.getText().contains(option)) {
                opt.click();
                break;
            }
        }
    }

    public void clickExitQuestion(WebDriver driver) {
        WaitHelper.waitForElementClickable(driver, exitQuestionButton);
        ClickHelper.clickElement(driver, exitQuestionButton);
    }

    public void clickUnderwritingQuestionsPageTab(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, underwritingQuestionsTab);
        WaitHelper.pause(10000);
    }

    public boolean checkEditButtonIsVisible(WebDriver driver){
        return ClickHelper.isElementExist(driver, clickEditOnQuestions);
    }

    public void clickEditButtonIsVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, clickEditOnQuestions);
        ClickHelper.clickElement(driver, clickEditOnQuestions);
        WaitHelper.pause(3000);
    }

    public void checkEditConfirmMsgIsVisible(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementClickable(driver, confirmMsg);
        ClickHelper.clickElement(driver, confirmMsgOK);
        WaitHelper.pause(3000);
    }

    public void clickUnderwritingQuestionsTab(WebDriver driver) throws InterruptedException {
        ClickHelper.clickElement(driver, underwritingQuestionsTab);
        WaitHelper.pause(3000);
    }

}
