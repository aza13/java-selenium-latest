package utils.fileDownload;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.io.File;


public class FileDownloadUtil {

    private static Logger logger = Logger.getLogger(FileDownloadUtil.class);
    static File fileLocation;
    static boolean fileDownloadStatus = false;
    static String home = System.getProperty("user.home");
    static File[] totalFiles;

    private FileDownloadUtil() {

    }

    public static void checkFileExistInDownloadFolder() throws InterruptedException {
        String userDirectory = System.getProperty("user.home");
        System.out.println("Jenkins Home Path: "+userDirectory);
        String downloadsPath = userDirectory+"\\Downloads";
        System.out.println("Download Path: "+downloadsPath);
        fileLocation = new File(downloadsPath);
        totalFiles = fileLocation.listFiles();
        assert totalFiles != null;
        for (File file : totalFiles) {
            if (file.getName().contains("TMHCC_")) {
                file.delete();
            }
        }
    }

    public static boolean verifyPDFFileDownload(String filename){

        totalFiles = fileLocation.listFiles();
        System.out.println("Filename in Jenkins : "+filename);

        for(File file : totalFiles) {
            if (file.getName().startsWith(filename)) {
                file.delete();
                fileDownloadStatus = true;
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

}
