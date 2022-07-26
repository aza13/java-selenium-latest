package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import utils.fileReader.ConfigDataReader;

import java.util.HashMap;
import java.util.Map;


public class DriverManager {

    private static final Logger logger = Logger.getLogger(DriverManager.class);

    private DriverManager(){

    }

    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver()  {

        WebDriver driver = DriverManager.threadDriver.get();

        String browser = ConfigDataReader.getInstance().getProperty("browserType");

        if (driver == null) {
            String operatingSystem = System.getProperty("os.name");
            switch (browser) {
                case "CHROME":
                    logger.info("Initialising the chrome browser");
                    WebDriverManager.chromedriver().setup();

                    Map<String, Object> prefs = new HashMap<>();
                    //to turns off multiple download warning
                    prefs.put("profile.default_content_settings.popups", 0);
                    prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
                    prefs.put("download.prompt_for_download", false);

                    ChromeOptions options = new ChromeOptions();
                    if (!operatingSystem.contains("Windows")) {
                        options.addArguments("--headless");
                    }
                    options.addArguments("--incognito");
                    options.setExperimentalOption("prefs", prefs);
                    driver = new ChromeDriver(options);
                    threadDriver.set(driver);
                    break;
                case "FIREFOX":
                    logger.info("Initialising the firefox browser");
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    threadDriver.set(driver);
                    break;
                case "EDGE":
                    logger.info("Initialising the ie browser");
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    threadDriver.set(driver);
                    break;
                case "OPERA":
                    logger.info("Initialising the opera browser");
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    threadDriver.set(driver);
                    break;
                default:
                    logger.info("No browser is matching");
            }
        }
        return driver;
    }

    static void quitDriver() {
        getDriver().quit();
        DriverManager.threadDriver.remove();
    }
}
