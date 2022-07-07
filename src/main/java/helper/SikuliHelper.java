package helper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class SikuliHelper {

    private static final Logger logger = Logger.getLogger(SikuliHelper.class.getSimpleName());

    private SikuliHelper(){

    }

    public static void uploadFile(String imagePathToBeEntered, String filePath, String imagePathToBeSubmitted) throws FindFailed {
        logger.info("uploading give file using Sikuli :: uploadFileUsingSikuli");
        try{
            Screen s = new Screen();
            Pattern pToEnter = new Pattern(imagePathToBeEntered);
            Pattern pToSubmit = new Pattern(imagePathToBeSubmitted);
            s.wait(pToEnter, 20);
            s.type(pToEnter, filePath);
            s.click(pToSubmit);
        }catch (Exception e){
            logger.error("failed to upload the file :: uploadFileUsingSikuli");
            throw (e);
        }
    }
}
