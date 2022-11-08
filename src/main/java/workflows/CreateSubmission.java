package workflows;

import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;

import java.util.Map;

public class CreateSubmission {

    private static final Logger logger = Logger.getLogger(CreateSubmission.class);

    private CreateSubmission(){}
    
    public static void createSubmissionTillQuotePage(WebDriver driver, Map<String, String> map) throws InterruptedException {

        CreateApplicant.createApplicant(driver);
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (!ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) throw new AssertionError();
        FillApplicantDetails.fillApplicantDetails(driver, map);
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = ratingCriteriaPageActions.clickRatingCriteriaContinueButton(driver);
        if (!underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(driver)) throw new AssertionError();
        QuoteListPageActions quoteListPageActions = AnswerUnderwriterQuestions.answerUnderwriterQuestions(driver, map);
        if (!quoteListPageActions.isQuoteListPageDisplayed(driver)) throw new AssertionError();
    }
}
