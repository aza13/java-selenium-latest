package base;

import pageActions.*;

public class PageObjectManager {

    private static DashboardPageActions dashboardPageActions;

    private static InsuredPageActions insuredPageActions;

    private static LoginPageActions loginPageActions;

    private static RatingCriteriaPageActions ratingCriteriaPageActions;

    private static UnderwritingQuestionsPageActions underwritingQuestionsPageActions;

    private static QuoteListPageActions quoteListPageActions;

    private static BindingPageActions bindingPageActions;


    private PageObjectManager(){

    }

    public static DashboardPageActions getDashboardPageActions(){

        return (dashboardPageActions == null) ? dashboardPageActions = new DashboardPageActions() : dashboardPageActions;
    }
    public static InsuredPageActions getInsuredPageActions(){

        return (insuredPageActions == null) ? insuredPageActions = new InsuredPageActions() : insuredPageActions;
    }

    public static RatingCriteriaPageActions getRatingCriteriaPageActions(){

        return (ratingCriteriaPageActions == null) ? ratingCriteriaPageActions = new RatingCriteriaPageActions() : ratingCriteriaPageActions;
    }

    public static LoginPageActions getLoginPageActions(){

        return (loginPageActions == null) ? loginPageActions = new LoginPageActions() : loginPageActions;
    }

    public static RatingCriteriaPageActions getRatingCriteriaActions() {

        return (ratingCriteriaPageActions == null) ? ratingCriteriaPageActions = new RatingCriteriaPageActions() : ratingCriteriaPageActions;
    }

    public static UnderwritingQuestionsPageActions getUnderwritingQuestionsPageActions() {

        return (underwritingQuestionsPageActions == null) ? underwritingQuestionsPageActions = new UnderwritingQuestionsPageActions() : underwritingQuestionsPageActions;
    }

    public static QuoteListPageActions getQuoteListPageActions() {

        return (quoteListPageActions == null) ? quoteListPageActions = new QuoteListPageActions() : quoteListPageActions;
    }

    public static BindingPageActions getBindingPageActions(){

        return (bindingPageActions == null) ? bindingPageActions = new BindingPageActions() : bindingPageActions;
    }


}
