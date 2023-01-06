package utils.fileDownload;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class FileDownloadUtil {

    private static final Logger logger = Logger.getLogger(FileDownloadUtil.class);
    static File fileLocation;
    static boolean fileDownloadStatus = false;
    static File[] totalFiles;
    static FileInputStream fis;
    static PDDocument pdfDocument;
    static PDFTextStripper pdfTextStripper;
    static String pdfData;

    private FileDownloadUtil() {

    }

    public static boolean checkFileExistInDownloadFolder(String fileName) {
        String userDirectory = System.getProperty("user.home");
        System.out.println("Jenkins Home Path: "+userDirectory);
        String downloadsPath = userDirectory+"/Downloads/"+fileName;
        System.out.println("Download Path: "+downloadsPath);
        fileLocation = new File(downloadsPath);
        return fileLocation.exists();
        /*totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
            if (file.getName().contains("TMHCC_")||file.getName().contains("Binder_")|| file.getName().contains("NAS_Broker_Binder_Invoice_")||
                    file.getName().contains("NAS_Binder_Invoice_")) {
                file.delete();
            }
        }*/
    }

    public static boolean verifyPDFFileDownload(String filename){

        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for(File file : totalFiles) {
            if (file.getName().contains(filename)) {
                fileDownloadStatus = true;
                break;
            }
        }
        return fileDownloadStatus;
    }

    public static boolean verifyWORDFileDownload(String fileName){

        String userDirectory = System.getProperty("user.home");
        String downloadsPath = userDirectory+"\\Downloads\\"+fileName;
        fileLocation = new File(downloadsPath);
        if(fileLocation.exists()){
            fileDownloadStatus = true;
        }
        return fileDownloadStatus;
    }

    public static void checkFileExistInDownloadFolderPath() {
        String userDirectory = System.getProperty("user.home");
        String downloadsPath = userDirectory+"\\Downloads";
        fileLocation = new File(downloadsPath);
        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
            if (file.getName().contains("fileName")) {
                file.deleteOnExit();
            }
        }
    }

    public static String readPDFFileContent(String fileName) throws IOException {
        String userDirectory = System.getProperty("user.home");
        String downloadsPath = userDirectory+"\\Downloads\\"+fileName;
        fileLocation = new File(downloadsPath);
        if(fileLocation.exists()){
            fis = new FileInputStream(fileLocation);
            pdfDocument = PDDocument.load(fis);
            pdfTextStripper = new PDFTextStripper();
            pdfData = pdfTextStripper.getText(pdfDocument);
            pdfDocument.close();
            fis.close();
            fileLocation.deleteOnExit();
        }
        return pdfData;
    }
}
