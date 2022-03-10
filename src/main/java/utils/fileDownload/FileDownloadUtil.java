package utils.fileDownload;

import helper.ClickHelper;
import helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static pageObjects.QuoteListPageObjects.*;

public class FileDownloadUtil {

    private static Logger logger = Logger.getLogger(FileDownloadUtil.class);
    static File folder;

    private FileDownloadUtil(){

    }

    public static void fileDownload(WebDriver driver, By elementLocator) throws InterruptedException {

        folder = new File(UUID.randomUUID().toString());
        folder.mkdir();

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", folder.getAbsolutePath());

        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(cap);

        WaitHelper.waitForElementVisibility(driver, clickAsPDFDownloadButton);
        ClickHelper.clickElement(driver, clickAsPDFDownloadButton);

        WaitHelper.pause(5000);

        File listOfFiles[] = folder.listFiles();
        Assert.assertTrue(listOfFiles.length > 0);

        for(File file : listOfFiles){
            Assert.assertTrue(file.length() > 0);
        }

    }

    public static void afterFileDownload(){
        for(File file : folder.listFiles()){
            file.delete();
        }
        folder.delete();
    }
}
