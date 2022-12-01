package utils.fileDownload;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;


public class FileDownloadUtil {

    private static Logger logger = Logger.getLogger(FileDownloadUtil.class);
    static File fileLocation;
    static boolean fileDownloadStatus = false;
    static String home = System.getProperty("user.home");
    static File[] totalFiles;
    static FileInputStream fis;
    static PDDocument pdfDocument;
    static PDFTextStripper pdfTextStripper;
    static String pdfData;

    private FileDownloadUtil() {

    }

    public static void checkFileExistInDownloadFolder() {
        String userDirectory = System.getProperty("user.home");
        System.out.println("Jenkins Home Path: "+userDirectory);
        String downloadsPath = userDirectory+"/Downloads";
        System.out.println("Download Path: "+downloadsPath);
        fileLocation = new File(downloadsPath);
        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
            if (file.getName().contains("TMHCC_")||file.getName().contains("Binder_")) {
                file.delete();
            }
        }
    }

    public static boolean verifyPDFFileDownload(String filename){

        totalFiles = fileLocation.listFiles();
        System.out.println("Filename in Jenkins : "+filename);

        for(File file : totalFiles) {
            if (file.getName().contains(filename)) {
                fileDownloadStatus = true;
                break;
            }
        }
        return fileDownloadStatus;
    }

    public static boolean verifyWORDFileDownload(String filename1, String filename2){

        totalFiles = fileLocation.listFiles();
        fileDownloadStatus = false;

        for(File file : totalFiles) {
            if (file.getName().startsWith(filename1)) {
                file.delete();
                fileDownloadStatus = true;
            }else if(file.getName().equals(filename2)) {
                file.delete();
                fileDownloadStatus = true;
            }
        }
        return fileDownloadStatus;
    }

    public static void checkFileExistInDownloadFolderpath() throws InterruptedException {
        String userDirectory = System.getProperty("user.home");
        System.out.println("Jenkins Home Path: "+userDirectory);
        String downloadsPath = userDirectory+"\\Downloads";
        System.out.println("Download Path: "+downloadsPath);
        fileLocation = new File(downloadsPath);
        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
            if (file.getName().contains("fileName")) {

                file.delete();
            }
        }
    }

    public static String readPDFFileContent() throws Exception {
        String userDirectory = System.getProperty("user.home");
        System.out.println("Jenkins Home Path: "+userDirectory);
        String downloadsPath = userDirectory+"\\Downloads";
        System.out.println("Download Path: "+downloadsPath);
        fileLocation = new File(downloadsPath);

        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
                if(file.getPath().contains("fileName")){
                    fis = new FileInputStream(file.getPath());
                    pdfDocument = PDDocument.load(fis);
                    pdfTextStripper = new PDFTextStripper();
                    pdfData = pdfTextStripper.getText(pdfDocument);
                }
        }
        pdfDocument.close();
        fis.close();

        return pdfData;
    }
}
