package workflows;

import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.UnderwritingQuestionsPageActions;
import utils.fileReader.ConfigDataReader;

import java.util.Map;

public class AnswerUnderwriterQuestions {

    private static final Logger logger = Logger.getLogger(AnswerUnderwriterQuestions.class);
    
    private AnswerUnderwriterQuestions(){}
    
    public static QuoteListPageActions answerUnderwriterQuestions(WebDriver driver, Map<String, String> map, String product) throws InterruptedException {
        
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(driver);
        if (uwQuestionsAnswered) {
            logger.info("UW continue button is enabled, means UW questions are answered");
        } else {
            logger.info("UW continue button is disabled, means UW questions are not answered");
            if(ConfigDataReader.getInstance().getProperty(product).contains("NetGuard")){
                underwritingQuestionsPageActions.answerUWQuestionButtons(driver, map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(driver, map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            }else if(ConfigDataReader.getInstance().getProperty(product).contains("Ophthalmic")){
                underwritingQuestionsPageActions.answerUWQuestionGeneralButtonOMICProduct2(driver);
                underwritingQuestionsPageActions.answerUWQuestioneEMDButtonOMICProduct2(driver);
                underwritingQuestionsPageActions.answerUWQuestionRansomButtonOMICProduct2(driver);
                underwritingQuestionsPageActions.answerUWQuestionPhisingButtonOMICProduct2(driver);
                underwritingQuestionsPageActions.answerUWQuestionCyberButtonOMICProduct2(driver);
                underwritingQuestionsPageActions.answerUWQuestionRiskButtonOMICProduct2(driver);
            }else{
                underwritingQuestionsPageActions.answerFirstUWQuestion(driver);
                underwritingQuestionsPageActions.answerUWQuestionButtonsOMICProduct2(driver);
            }

        }
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(driver);
        WaitHelper.waitForProgressbarInvisibility(driver);
        QuoteListPageActions quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(driver)) {
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            quoteListPageActions.clickSubmitForReview(driver);
        }
        return quoteListPageActions;
    }
}
