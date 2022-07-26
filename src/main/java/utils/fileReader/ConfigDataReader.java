package utils.fileReader;


import constants.ConstantVariable;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigDataReader {

    private static final Logger logger = Logger.getLogger(ConfigDataReader.class);
    private static FileInputStream inputStream;
    private static ConfigDataReader configDataReader;
    private static final Properties prop = new Properties();

    private ConfigDataReader() throws IOException {
        logger.info("Loading the properties file in :: getPropInstance ");
        String filePath = ConstantVariable.CONFIG_PROP_FILEPATH;
        try{
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            prop.load(inputStream);
        } catch (Exception e) {
            logger.error("failed to load the config properties file");
        }finally {
            inputStream.close();
        }
    }

    public static ConfigDataReader getInstance(){
        if(configDataReader == null){
            synchronized (ConfigDataReader.class){
                try{
                    configDataReader = new ConfigDataReader();
                }catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Failed to get the object of the config file reader "+e.getMessage());
                }
            }
        }
        return configDataReader;
    }

    public String getProperty(String key){
        return prop.getProperty(key);
    }


}
