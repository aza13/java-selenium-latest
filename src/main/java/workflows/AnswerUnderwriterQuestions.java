package workflows;

import base.DriverManager;
import base.PageObjectManager;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.UnderwritingQuestionsPageActions;

public class AnswerUnderwriterQuestions {

    private static final Logger logger = Logger.getLogger(AnswerUnderwriterQuestions.class);
    
    private AnswerUnderwriterQuestions(){}
    
    public static QuoteListPageActions answerUnderwriterQuestions(WebDriver driver, JSONObject jsonObject, String product) throws InterruptedException {
        
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = PageObjectManager.getUnderwritingQuestionsPageActions();
        boolean uwQuestionsAnswered = underwritingQuestionsPageActions.checkWhetherAllUWQuestionsAreAnswered(driver);
        if (uwQuestionsAnswered) {
            logger.info("UW continue button is enabled, means UW questions are answered");
        } else {
            logger.info("UW continue button is disabled, means UW questions are not answered");
            if(product.contains("NetGuard® SELECT")){
                underwritingQuestionsPageActions.answerUWQuestionButtons(driver, jsonObject.get("uwQuestionsAnswer").toString());
                underwritingQuestionsPageActions.answerUWQuestionDropdowns(driver, jsonObject.get("uwQuestionsAnswer").toString(), jsonObject.get("uwQuestionsOption").toString());
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
            }
        }
        underwritingQuestionsPageActions.clickUWQuestionsContinueButton(driver);
        WaitHelper.waitForProgressbarInvisibility(driver);
        QuoteListPageActions quoteListPageActions = PageObjectManager.getQuoteListPageActions();

        /*if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(driver)) {
            quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
            quoteListPageActions.clickSubmitForReview(driver);
        }*/
        return quoteListPageActions;
    }
}
