package workflows;

import base.BaseTest;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;

import java.util.Map;


public class CreateSubmission  extends BaseTest {

    private static final Logger logger = Logger.getLogger(CreateSubmission.class);

    private CreateSubmission(){}
    
    public static void createSubmissionTillQuotePage(WebDriver driver, Map<String, String> map, String product) throws InterruptedException {

        CreateApplicant.createApplicant(driver, product);
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (!ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) throw new AssertionError();
        FillApplicantDetails.fillApplicantDetails(driver, map, Netguard);
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = ratingCriteriaPageActions.clickRatingCriteriaContinueButton(driver);
        if (!underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(driver)) throw new AssertionError();
        QuoteListPageActions quoteListPageActions = AnswerUnderwriterQuestions.answerUnderwriterQuestions(driver, map, product);
        if (!quoteListPageActions.isQuoteListPageDisplayed(driver)) throw new AssertionError();
    }
}
