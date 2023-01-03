package workflows;

import base.BaseTest;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import pageActions.QuoteListPageActions;
import pageActions.RatingCriteriaPageActions;
import pageActions.UnderwritingQuestionsPageActions;


public class CreateSubmission  extends BaseTest {

    private static final Logger logger = Logger.getLogger(CreateSubmission.class);

    private CreateSubmission(){}
    

    public static QuoteListPageActions createSubmissionTillQuotePage(WebDriver driver, JSONObject jsonObject, String coverage) throws InterruptedException {
        logger.info("creating the submission till quote list page :: createSubmissionTillQuotePage");
        CreateApplicant.createApplicant(driver, coverage);
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (!ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) throw new AssertionError();
        FillApplicantDetails.fillApplicantDetails(driver, jsonObject, coverage);
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = ratingCriteriaPageActions.clickRatingCriteriaContinueButton(driver);
        if (!underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(driver)) throw new AssertionError();
        QuoteListPageActions quoteListPageActions = AnswerUnderwriterQuestions.answerUnderwriterQuestions(driver, jsonObject, coverage);
        if (!quoteListPageActions.isQuoteListPageDisplayed(driver)) throw new AssertionError();
        return quoteListPageActions;
    }

    public static UnderwritingQuestionsPageActions createSubmissionTillUWQuestionPage(WebDriver driver, JSONObject jsonObject , String coverage) throws InterruptedException {
        logger.info("creating the submission till UW Question list page :: createSubmissionTillUWQuestionPage");
        CreateApplicant.createApplicant(driver, coverage);
        RatingCriteriaPageActions ratingCriteriaPageActions = PageObjectManager.getRatingCriteriaPageActions();
        if (!ratingCriteriaPageActions.isRatingCriteriaPageDisplayed(driver)) throw new AssertionError();
        FillApplicantDetails.fillApplicantDetails(driver, jsonObject, coverage);
        UnderwritingQuestionsPageActions underwritingQuestionsPageActions = ratingCriteriaPageActions.clickRatingCriteriaContinueButton(driver);
        if (!underwritingQuestionsPageActions.isUnderwritingQuestionsPageDisplayed(driver)) throw new AssertionError();
       return underwritingQuestionsPageActions;
    }
}
