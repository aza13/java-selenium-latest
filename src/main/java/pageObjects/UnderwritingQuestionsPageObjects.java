package pageObjects;

import org.openqa.selenium.By;

public class UnderwritingQuestionsPageObjects {

    private UnderwritingQuestionsPageObjects(){

    }

    public static final By underwritingQuestionsHeader =By.xpath("//h4[text()='Underwriting Questions']");
    public static final By uwQuestionsContinueButton = By.id("underwriting-continue");
    public static final By allQuestionsAnsweredText = By.xpath("//h3[text()='All questions have been answered!']");
    public static final By quotesTab = By.id("workflow-tab-3");
    public static final By underWriterQuestionTabs = By.xpath("//div[@id='underwriting-questions-header' and @role='button']");

}
