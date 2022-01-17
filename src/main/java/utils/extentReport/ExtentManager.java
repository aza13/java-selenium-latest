package utils.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class ExtentManager {

    private static ExtentReports extent;

    private static final Logger logger = Logger.getLogger(ExtentManager.class);

    //singleton pattern design
    public static ExtentReports getInstance() {
        if (extent == null){
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {

        logger.info("Initialising the ExtentReport in:: ExtentReport");

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");

        Date date = new Date();

//        String reportPath = System.getProperty("user.dir") + "\\test-output\\extent-report\\" + dateFormat.format(date) + "_bp_ui.html";

        String reportPath = System.getProperty("user.dir") + "\\extent-report\\BP_UI_Extent_Report.html";

        ExtentSparkReporter sparkReporter  = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        //Report Config
        sparkReporter.config().setDocumentTitle("BP UI Automation Results Report");
        sparkReporter.config().setReportName("Regression Results");
        sparkReporter.config().setTheme(Theme.DARK);

        //System Info
        extent.setSystemInfo("OS", "Windows 10, 64-bit");
        extent.setSystemInfo("browserType", "Chrome");
        extent.setSystemInfo("Tester", "VAM Tester");

    }


}
