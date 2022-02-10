package utils.fileReader;


import constants.ConstantVariable;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigDataReader {

    private static final Logger logger = Logger.getLogger(ConfigDataReader.class);

    private static Properties prop;


    private ConfigDataReader(){

    }


    public static Properties configPropInit(String filePath) {

        logger.info("Loading the properties file in :: configPropInit ");

        File file = new File(filePath);

        if(prop == null){

            prop = new Properties();

            try (FileInputStream fis = new FileInputStream(file)) {

                prop.load(fis);

            } catch (IOException e) {

                logger.error("Failed to load the properties file in :: ConfigPropInit ");
            }
        }

        return prop;
    }

    public static void writeValuesPropFile(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        FileOutputStream fileOut = new FileOutputStream(filePath);


    }
}
