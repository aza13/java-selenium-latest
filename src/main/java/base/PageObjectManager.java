package base;

import helper.FakeDataHelper;
import pageActions.*;

public class PageObjectManager {

    private static SnapLoginPageActions snapLoginPageActions;

    private static SnapDashboardPageActions snapDashboardPageActions;

    private static SnapBrokerPageActions snapBrokerPageActions;

    private static SnapPolicyPageActions snapPolicyPageActions;

    private static FakeDataHelper fakeDataHelper;

    private PageObjectManager(){

    }


    public static SnapLoginPageActions getSnapLoginPageActions(){

        return (snapLoginPageActions == null) ? snapLoginPageActions = new SnapLoginPageActions() : snapLoginPageActions;
    }

    public static SnapDashboardPageActions getSnapDashboardPageActions(){

        return (snapDashboardPageActions == null) ? snapDashboardPageActions = new SnapDashboardPageActions() : snapDashboardPageActions;
    }

    public static SnapBrokerPageActions getSnapBrokerPageActions(){

        return (snapBrokerPageActions == null) ? snapBrokerPageActions = new SnapBrokerPageActions() : snapBrokerPageActions;
    }

    public static SnapPolicyPageActions getSnapPolicyPageActions(){

        return (snapPolicyPageActions == null) ? snapPolicyPageActions = new SnapPolicyPageActions() : snapPolicyPageActions;
    }

    public static FakeDataHelper getFakeDataHelper(){

        return (fakeDataHelper == null) ? fakeDataHelper = new FakeDataHelper() : fakeDataHelper;
    }


}
