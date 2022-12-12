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
            if(product.contains("NetGuard® SELECT")){
                underwritingQuestionsPageActions.answerUWQuestionButtons(driver, map.get("uwQuestionsAnswer"));
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(driver, map.get("uwQuestionsAnswer"), map.get("uwQuestionsOption"));
            }else if(product.contains("Ophthalmic")){
                underwritingQuestionsPageActions.answerUWQuestionGeneralSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionEMDSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionRansomSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionPhishingSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionCyberSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionRiskSectionOMICProduct(driver);
            }else if(product.contains("American Academy")){
                underwritingQuestionsPageActions.answerUWQuestionGeneralSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionEMDSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionRansomSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionPhishingSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionCyberSectionOMICProduct(driver);
                underwritingQuestionsPageActions.answerUWQuestionRiskSectionOMICProduct(driver);
            }else{
                System.out.println("Place holder for Other Product");
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
