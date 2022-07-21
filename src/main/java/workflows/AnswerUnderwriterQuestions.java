package workflows;

import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.UnderwritingQuestionsPageActions;

import java.util.Map;

public class AnswerUnderwriterQuestions {

    private static final Logger logger = Logger.getLogger(AnswerUnderwriterQuestions.class);
    
    private AnswerUnderwriterQuestions(){}
    
    public static void answerUnderwriterQuestions(WebDriver driver, Map<String, String> map) throws InterruptedException {
        
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(driver);
        if (uwQuestionsAnswered) {
            logger.info("UW continue button is enabled, means UW questions are answered");
        } else {
            logger.info("UW continue button is disabled, means UW questions are not answered");
            underwritingQuestionsPageActions.answerUWQuestionButtons(driver, map.get("uwQuestionsAnswer"));
            underwritingQuestionsPageActions.answerUWQuestionDropdowns(driver, map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
        }
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(driver);
        QuoteListPageActions quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        if (!quoteListPageActions.isQuoteListPageDisplayed(driver)) {
            quoteListPageActions.clickQuotesTab(driver);
        }
    }
}
