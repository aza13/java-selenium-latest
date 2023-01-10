package utils.fileDownload;
import constants.ConstantVariable;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileDownloadUtil {

    private static final Logger logger = Logger.getLogger(FileDownloadUtil.class);

    private FileDownloadUtil() {

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