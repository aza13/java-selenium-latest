package helper;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class FileHelper {

    private static final Logger logger = Logger.getLogger(FileHelper.class);

    private FileHelper(){
        
    }

    public static void uploadFile(String relativeFilePath) throws InterruptedException, AWTException {
        logger.info("creating object of Robot class");
        Robot rb = new Robot();
        String filePath = System.getProperty("user.dir") + relativeFilePath;

        logger.info("copying File path to Clipboard");
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        logger.info("press Contol+V for pasting");
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        logger.info("release Contol+V for pasting");
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        logger.info("for pressing and releasing Enter");
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WaitHelper.pause(3000);
    }

    public static void uploadFileUsingJavaScript(WebDriver driver, String relativeFilePath) {
        WebElement element = driver.findElement(By.tagName("input"));
        String styleAttribute = element.getAttribute("style");
        if(!styleAttribute.contains("inline")){
            JavaScriptHelper.executeJavaScriptOnWebElement(driver, "arguments[0].style.display='inline';", element);
        }
        driver.findElement(By.tagName("input")).sendKeys(System.getProperty("user.dir") + relativeFilePath);
    }
}
