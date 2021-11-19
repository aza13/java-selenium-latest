package base;

import helper.FakeDataHelper;
import pageActions.*;

public class PageObjectManager {

    private static DashboardPageActions dashboardPageActions;

    private PageObjectManager(){

    }


    public static DashboardPageActions getDashboardPageActions(){

        return (dashboardPageActions == null) ? dashboardPageActions = new DashboardPageActions() : dashboardPageActions;
    }

}
