package helper;


import constants.ConstantVariable;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileOperationsHelper {

    private static final Logger logger = Logger.getLogger(FileOperationsHelper.class);

    private FileOperationsHelper(){
        
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

    public static void deleteGivenFileIfExistsInDownloads(String fileName) {
        logger.info("Deleting the file if exists :: deleteGivenFileIfExistsInDownloads");
        String downloadsPath = ConstantVariable.DOWNLOADS_FOLDER_PATH +fileName;
        File fileLocation = new File(downloadsPath);
        if(fileLocation.isFile()){
            fileLocation.deleteOnExit();
        }
    }

    public static boolean verifyIfGivenFileExistsInDownloads(String fileName){
        String filePath = ConstantVariable.DOWNLOADS_FOLDER_PATH+fileName;
        File file = new File(filePath);
        return file.isFile();
    }

    public static String readPDFFileContent(String fileName) throws IOException {
        String downloadsPath = ConstantVariable.DOWNLOADS_FOLDER_PATH+fileName;
        File fileLocation = new File(downloadsPath);
        String pdfData = null;
        if(fileLocation.exists()){
            FileInputStream fis = new FileInputStream(fileLocation);
            PDDocument pdfDocument = PDDocument.load(fis);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfData = pdfTextStripper.getText(pdfDocument);
            pdfDocument.close();
            fis.close();
            fileLocation.deleteOnExit();
        }
        return pdfData;
    }
}
