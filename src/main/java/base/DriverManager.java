package base;

import constants.ConstantVariable;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import utils.fileReader.ConfigDataReader;

import static base.BaseTest.operatingSystem;


public class DriverManager {

    private static final Logger logger = Logger.getLogger(DriverManager.class);

    private static String browserType;

    private DriverManager(){

    }

    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    static synchronized void setBrowserType(String browser) {

        logger.info("Setting the browserType " + browser + " in :: setBrowserType");
        browserType = browser;
    }

    public static synchronized WebDriver getDriver()  {

        WebDriver driver = DriverManager.threadDriver.get();

        String browser = ConfigDataReader.configPropInit(ConstantVariable.CONFIG_PROP_FILEPATH).getProperty("browserType");

        if (driver == null) {

            switch (browser) {
                case "CHROME":
                    logger.info("Initialising the chrome browser");
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    if (!operatingSystem.contains("Windows")) {
                        options.addArguments("--headless");
                    }
                    options.addArguments("--incognito");
                    driver = new ChromeDriver(options);
                    threadDriver.set(driver);
                    break;
                case "FIREFOX":
                    logger.info("Initialising the firefox browser");
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    threadDriver.set(driver);
                    break;
                case "IE":
                    logger.info("Initialising the ie browser");
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    threadDriver.set(driver);
                    break;
                case "OPERA":
                    logger.info("Initialising the opera browser");
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    threadDriver.set(driver);
                    break;
            }
        }
        return driver;
    }

    static void quitDriver() {
        getDriver().quit();
        DriverManager.threadDriver.set(null);
    }
}
