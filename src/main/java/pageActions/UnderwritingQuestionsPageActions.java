package pageActions;

import base.BaseTest;
import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static pageObjects.UnderwritingQuestionsPageObjects.*;


public class UnderwritingQuestionsPageActions extends BaseTest {


    private static final Logger logger = Logger.getLogger(UnderwritingQuestionsPageActions.class);

    public boolean isUnderwritingQuestionsPageDisplayed(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(5000);
       return ClickHelper.isElementExist(driver, underwritingQuestionsHeader);
    }

    public void clickUWQuestionsContinueButton(WebDriver driver) throws InterruptedException {
        WaitHelper.waitForElementVisibility(driver, uwQuestionsContinueButton);
        ClickHelper.clickElement(driver, uwQuestionsContinueButton);
        WaitHelper.pause(5000);
    }

    public boolean checkWhetherAllUWQuestionsAreAnswered(WebDriver driver) throws InterruptedException {
        WaitHelper.pause(3000);
        return ClickHelper.isElementExist(driver, allQuestionsAnsweredText);
    }

    public void clickQuotesTab(WebDriver driver){
        ClickHelper.clickElement(driver, quotesTab);
    }

    public void answerUnderWriterQuestions(WebDriver driver, String answer) throws InterruptedException {
        String uwQuestionsTab = "//div[@id='underwriting-questions-header' and @role='button']";
        List<WebElement> uwQuestions = driver.findElements(By.xpath(uwQuestionsTab));
        int count = uwQuestions.size();
        for(int i=0; i<count; i++){
            uwQuestions.get(i).click();
            int j = i+1;
            String buttonXpath = "(//div[@id='underwriting-questions-header' and @role='button'])["+j+"]/following-sibling::div//button[text()='No']";
            List<WebElement> allNoButtons = driver.findElements(By.xpath(buttonXpath));
            for (WebElement button:allNoButtons) {
                WaitHelper.pause(6000);
                button.click();
            }
            WaitHelper.pause(6000);
//            driver.findElement(By.xpath(buttonXpath)).click();
        }

    }


}
