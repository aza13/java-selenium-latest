package utils.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReport {

    private static final Logger logger = Logger.getLogger(ExtentReport.class);

    private ExtentReport(){

    }

    public static ExtentReports ExtentReportInit(){

        logger.info("Initialising the ExtentReport in:: ExtentReport");

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        Date date = new Date();

        String reportPath = System.getProperty("user.dir")+"\\reports\\"+dateFormat.format(date)+"_bp_ui.html";

        logger.info("Report path is - "+reportPath);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        ExtentReports extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        //Report Config
        sparkReporter.config().setDocumentTitle("Broker Portal UI Automation Results Report");
        sparkReporter.config().setReportName("Regression Test Results Report");
        sparkReporter.config().setTheme(Theme.DARK);


        //System Info
        extent.setSystemInfo("OS", "Windows 10, 64-bit");
        extent.setSystemInfo("browserType", "Chrome");
        extent.setSystemInfo("Tester", "VAM Tester");
        logger.info("Returning the ExtentReports Object in:: ExtentReport");
        return extent;
    }
}
