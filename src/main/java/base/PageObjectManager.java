package base;

import pageActions.DashboardPageActions;
import pageActions.InsuredPageActions;
import pageActions.LoginPageActions;
import pageActions.RatingCriteriaPageActions;

public class PageObjectManager {

    private static DashboardPageActions dashboardPageActions;

    private static InsuredPageActions insuredPageActions;

    private static LoginPageActions loginPageActions;

    private static RatingCriteriaPageActions ratingCriteriaPageActions;

    private PageObjectManager(){

    }

    public static DashboardPageActions getDashboardPageActions(){

        return (dashboardPageActions == null) ? dashboardPageActions = new DashboardPageActions() : dashboardPageActions;
    }
    public static InsuredPageActions getInsuredPageActions(){

        return (insuredPageActions == null) ? insuredPageActions = new InsuredPageActions() : insuredPageActions;
    }

    public static LoginPageActions getLoginPageActions(){

        return (loginPageActions == null) ? loginPageActions = new LoginPageActions() : loginPageActions;
    }

    public static RatingCriteriaPageActions getRatingCriteriaActions() {

        return (ratingCriteriaPageActions == null) ? ratingCriteriaPageActions = new RatingCriteriaPageActions() : ratingCriteriaPageActions;
    }

}
