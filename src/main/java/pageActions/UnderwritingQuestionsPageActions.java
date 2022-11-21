package pageActions;

import base.BaseTest;
import base.DriverManager;
import helper.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageObjects.UnderwritingQuestionsPageObjects.*;


public class UnderwritingQuestionsPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageActions.class);
    public static int dropdownsIndex = 0;

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
        ScrollHelper.scrollElementIntoView(DriverManager.getDriver(), itPersonnel);
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
        WaitHelper.pause(20000);

    }

    public boolean checkWhetherAllUWQuestionsAreAnswered(WebDriver driver) {
        String xpath = "//button[@id='underwriting-continue' and @disabled]";
        By disableLocator = By.xpath(xpath);
        if (ClickHelper.isElementExist(driver, disableLocator)) {
            logger.info("UW continue button is disabled, need to answer UW questions");
            return false;
        } else {
            logger.info("UW continue button is enabled, UW questions are answered");
            return true;
        }
    }


    public void clickQuotesTab(WebDriver driver) {
        ClickHelper.javaScriptExecutorClick(driver, quotesTab);
    }

    public void answerUWQuestionButtons(WebDriver driver, String uwQuestionAnswer) throws InterruptedException {
        String firstQuestionNoButtonXpath = "(//div[@id='underwriting-questions-header' and @role='region']//div//p/parent::div//div//button[text()='No'])[1]";
        driver.findElement(By.xpath(firstQuestionNoButtonXpath)).click();
        WaitHelper.pause(3000);
        String buttonXpath = "//div[@id='underwriting-questions-header']//button[text()='" + uwQuestionAnswer + "']";
        try {
            List<WebElement> allNoButtons = driver.findElements(By.xpath(buttonXpath));
            int count = allNoButtons.size();
            if (count > 0) {
                logger.info("UW question buttons exist :: answerUWQuestionButtons");
                for (WebElement allNoButton : allNoButtons) {
                    logger.info("clicking on each yes/no button in each UW section");
                    WaitHelper.pause(5000);
                    allNoButton.click();
                }
            } else {
                logger.info("UW question buttons doesn't exist :: answerUWQuestionButtons");
            }
        } catch (StaleElementReferenceException e) {
            logger.warn("StaleElementReference exception occurred :: answerUWQuestionButtons");
            WaitHelper.pause(3000);
            answerUWQuestionButtons(driver, uwQuestionAnswer);
        }
    }

    public void answerUWQuestionButtonsOMICProduct(WebDriver driver) throws InterruptedException {
        String firstQuestionNoButtonXpath = "(//div[@id='underwriting-questions-header' and @role='region']//div//p/parent::div//div//button[text()='No'])[1]";
        driver.findElement(By.xpath(firstQuestionNoButtonXpath)).click();
        WaitHelper.pause(3000);
        String questionDescriptionXpath = "//div[@id='underwriting-questions-header' and @role='region']/div/div";
        List<WebElement> allDescriptions = driver.findElements(By.xpath(questionDescriptionXpath));
        int count = allDescriptions.size();
        int n = 0;
        String questionDescriptionText = null;
        String noButtonXpath;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                try {
                    WebElement questionDescription = allDescriptions.get(i);
                    questionDescriptionText = questionDescription.getText();
                    n = i + 1;
                    if (questionDescriptionText.toLowerCase().contains("third")) {
                        noButtonXpath = "(//div//button[text()='Yes'])[" + n + "]";
                    } else {
                        noButtonXpath = "(//div//button[text()='No'])[" + n + "]";
                    }
                    driver.findElement(By.xpath(noButtonXpath)).click();
                    WaitHelper.pause(5000);
                } catch (StaleElementReferenceException e) {
                    logger.warn("StaleElementReference exception occurred :: answerUWQuestionButtons");
                    WaitHelper.pause(10000);
                    answerUWQuestionButtonsOMICProduct(driver);
                }
            }
        } else {
            logger.info("UW question buttons doesn't exist :: answerUWQuestionButtonsOMICProduct");
        }
    }

    public void answerFirstUWQuestion(WebDriver driver) throws InterruptedException {
        String secondQuestionYesBtnXpath = "(//h5[text()='e-MD']/parent::div/parent::div/following-sibling::div//button[text()='Yes'])[1]";
        driver.findElement(By.xpath(secondQuestionYesBtnXpath)).click();
        WaitHelper.pause(3000);
    }

    public void answerEachSectionUWQuestions(WebDriver driver, int dropdownCount, String sectionXpath) throws InterruptedException {
        List<WebElement> descriptions = driver.findElements(By.xpath(sectionXpath + "//p"));
        int count = descriptions.size()-dropdownCount;
        for (int i = 0; i < count; i++) {
            WebElement description = descriptions.get(i);
            String text = description.getText();
            int n = i + 1;
            if (text.contains("third")) {
                String yesXpath = "(" + sectionXpath + "//button[text()='Yes'])" + "[" + n + "]";
                driver.findElement(By.xpath(yesXpath)).click();
            } else {
                String noXpath = "(" + sectionXpath + "//button[text()='No'])" + "[" + n + "]";
                driver.findElement(By.xpath(noXpath)).click();
            }
        }
        WaitHelper.pause(5000);
    }

    public void answerEachSectionUWQuestionsRansome(WebDriver driver, int dropdownCount, String sectionXpath) throws InterruptedException {
        List<WebElement> descriptions = driver.findElements(By.xpath(sectionXpath + "//p"));
        int count = descriptions.size()-dropdownCount;
        for (int i = 0; i < count; i++) {
            WebElement description = descriptions.get(i);
            String text = description.getText();
            int n = i + 1;
            if (text.contains("Applicant")) {
                String yesXpath = "(" + sectionXpath + "//button[text()=\"Don't Know\"])" + "[" + n + "]";
                driver.findElement(By.xpath(yesXpath)).click();
            } else {
                String noXpath = "(" + sectionXpath + "//button[text()=\"Don't Know\"])" + "[" + n + "]";
                driver.findElement(By.xpath(noXpath)).click();
            }
        }
        WaitHelper.pause(5000);
    }

    public void answerUWQuestionButtonsOMICProduct2(WebDriver driver) throws InterruptedException {
        int count = 0;
        String generalInformationXpath = "//h5[text()='General Information']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestions(driver, 0, generalInformationXpath);

        String eMDXpath = "//h5[text()='e-MD']/parent::div/parent::div/following-sibling::div";
        List<WebElement> eMDDropdowns = driver.findElements(By.xpath(eMDXpath+"//input/preceding-sibling::div"));
        if(!eMDDropdowns.isEmpty()){
            for (WebElement dropdown : eMDDropdowns){
                DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, "None");
            }
        }
        List<WebElement> eMDDescriptions = driver.findElements(By.xpath(eMDXpath + "//p"));
        count = eMDDescriptions.size()-eMDDropdowns.size();
        for (int i = 0; i < count; i++) {
            WebElement description = eMDDescriptions.get(i);
            String text = description.getText();
            int n = i + 1;
            if (text.contains("e-MD")) {
                String yesXpath = "(" + eMDXpath + "//button[text()='Yes'])" + "[" + n + "]";
                driver.findElement(By.xpath(yesXpath)).click();
            } else {
                String noXpath = "(" + eMDXpath + "//button[text()='Yes'])" + "[" + n + "]";
                driver.findElement(By.xpath(noXpath)).click();
            }
        }

        String ransomwareXpath = "//h5[text()='Ransomware Controls']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestionsRansome(driver, 0, ransomwareXpath);

        String phishingXpath = "//h5[text()='Phishing Controls']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestionsRansome(driver, 0, phishingXpath);

        String cyberLossXpath = "//h5[text()='Cyber/Privacy Loss History']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestions(driver, 0,cyberLossXpath);

        String boardRegulatoryXpath = "//h5[text()='Broad Regulatory Risk Protection Plus']/parent::div/parent::div/following-sibling::div";
        List<WebElement> boardRegulatoryDropdowns = driver.findElements(By.xpath(boardRegulatoryXpath+"//input/preceding-sibling::div"));
        if(!boardRegulatoryDropdowns.isEmpty()){
            for (WebElement dropdown : boardRegulatoryDropdowns){
                DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, "None");
            }
        }
        answerEachSectionUWQuestions(driver, boardRegulatoryDropdowns.size(), boardRegulatoryXpath);

        String billingComplianceXpath = "//h5[text()='Billing and Compliance']/parent::div/parent::div/following-sibling::div";
        List<WebElement> billingComplianceDescriptions = driver.findElements(By.xpath(billingComplianceXpath + "//p"));
        count = billingComplianceDescriptions.size();
        for (int i = 0; i < count; i++) {
            WebElement description = billingComplianceDescriptions.get(i);
            String text = description.getText();
            int n = i + 1;
            String inputFieldXpath = "(" + billingComplianceXpath + "//input)" + "[" + n + "]";
            if(ClickHelper.isElementExist(driver, By.xpath(inputFieldXpath))){
                logger.info("do nothing");
            }else if (text.contains("third")) {
                String yesXpath = "(" + billingComplianceXpath + "//button[text()='Yes'])" + "[" + n + "]";
                driver.findElement(By.xpath(yesXpath)).click();
            } else {
                String noXpath = "(" + billingComplianceXpath + "//button[text()='No'])" + "[" + n + "]";
                driver.findElement(By.xpath(noXpath)).click();
            }
        }

        String regulatoryLossXpath = "//h5[text()='Regulatory Loss History']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestions(driver, 0, regulatoryLossXpath);
    }


    public void answerUWQuestionDropdowns(WebDriver driver, String answer, String dropdownOption) throws InterruptedException {

        String[] dropdownIds = {"General-select", "Required Questions-select", "IT Department-select"};
        try {
            logger.info("selecting UW questions dropdowns");
            for (String dropdownId : dropdownIds) {
                List<WebElement> dropdowns = driver.findElements(By.id(dropdownId));
                if (!dropdowns.isEmpty()) {
                    logger.info("UW questions dropdowns exists :: answerUWQuestionDropdowns");
                    for (WebElement dropdown : dropdowns) {
                        logger.info("selecting the value from UW dropdown");
                        DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, dropdownOption);
                        WaitHelper.pause(5000);
                        dropdownsIndex++;
                    }
                }
            }
        } catch (StaleElementReferenceException e) {
            logger.warn("StaleElementReference exception occurred in :: answerUWQuestionDropdowns");
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

    public boolean checkEditButtonIsVisible(WebDriver driver) {
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

    public boolean verifyQuestionIsVisible(WebDriver driver) {
        return WaitHelper.isElementEnabled(driver, underwritingQuestionsTab);
    }

    public boolean verifySoftDeclinePopupHeader(WebDriver driver) {
        return WaitHelper.isElementDisplayed(driver, softDeclineHeader);
    }

    public void enterSoftDeclineTextAndSubmit(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, softDeclineText);
        TextHelper.enterText(driver, softDeclineText, "softdeclinetest");
        ClickHelper.clickElement(driver, softDeclineSubmit);
        WaitHelper.pause(5000);
    }

    public void answerUWQuestionGeneralButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        String generalInformationXpath = "//h5[text()='General Information']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestions(driver, 0, generalInformationXpath);
    }
    public void answerUWQuestioneEMDButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        String eMDXpath = "//h5[text()='e-MD']/parent::div/parent::div/following-sibling::div";
        List<WebElement> eMDDropdowns = driver.findElements(By.xpath(eMDXpath+"//input/preceding-sibling::div"));
        if(!eMDDropdowns.isEmpty()){
            for (WebElement dropdown : eMDDropdowns){
                DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, "None");
            }
        }
        List<WebElement> eMDDescriptions = driver.findElements(By.xpath(eMDXpath + "//p"));
            WebElement description = eMDDescriptions.get(0);
            String text = description.getText();
            String yesXpath;

            for(int i=1; i<=4 ; i++){
                if (text.contains("e-MD")) {
                    yesXpath = "(" + eMDXpath + "//button[text()='Yes'])" + "[" + i + "]";
                    driver.findElement(By.xpath(yesXpath)).click();
                    WaitHelper.pause(3000);
                }
            }
        }
    public void answerUWQuestionRansomButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        String ransomwareXpath = "//h5[text()='Ransomware Controls']/parent::div/parent::div/following-sibling::div";
        answerEachSectionUWQuestionsRansome(driver, 0, ransomwareXpath);
    }
    public void answerUWQuestionPhisingButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        String phishingXpath = "//h5[text()='Phishing Controls']/parent::div/parent::div/following-sibling::div";
        List<WebElement> allPhisingXpath = driver.findElements(By.xpath(phishingXpath + "//p"));
        WebElement phisingDescription = allPhisingXpath.get(0);
        String text = phisingDescription.getText();
        String yesXpath;

        for(int i=1; i<=4 ; i++){
            if (text.contains("Applicant's")) {
                yesXpath = "(" + phishingXpath + "//button[text()='Yes'])" + "[" + i + "]";
                driver.findElement(By.xpath(yesXpath)).click();
                WaitHelper.pause(3000);
            }
        }
    }
    public void answerUWQuestionCyberButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        String cyberLossXpath = "//h5[text()='Cyber/Privacy Loss History']/parent::div/parent::div/following-sibling::div";

        List<WebElement> descriptions = driver.findElements(By.xpath(cyberLossXpath + "//p"));
        int count = descriptions.size();
        for (int i = 0; i < count; i++) {
            WebElement description = descriptions.get(i);
            String text = description.getText();
            int n = i + 1;
            if (text.contains("third")) {
                String yesXpath = "(" + cyberLossXpath + "//button[text()='No'])" + "[" + n + "]";
                driver.findElement(By.xpath(yesXpath)).click();
            }
        }
        WaitHelper.pause(5000);
    }
    public void answerUWQuestionRiskButtonOMICProduct2(WebDriver driver) throws InterruptedException {
        String boardRegulatoryXpath = "//h5[text()='Broad Regulatory Risk Protection Plus']/parent::div/parent::div/following-sibling::div";
        List<WebElement> boardRegulatoryDropdowns = driver.findElements(By.xpath(boardRegulatoryXpath+"//input/preceding-sibling::div"));
        if(!boardRegulatoryDropdowns.isEmpty()){
            for (WebElement dropdown : boardRegulatoryDropdowns){
                DropdownHelper.selectValueFromBootstrapDropdown(driver, dropdown, uwQuestionsDropdownsOption, "None");
            }
        }
        answerEachSectionUWQuestions(driver, boardRegulatoryDropdowns.size(), boardRegulatoryXpath);
    }

}
