package utils.fileReader;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextFileReader {

    private static final Logger logger = Logger.getLogger(TextFileReader.class);

    private TextFileReader() {

    }

    public static void writeDataToTextFile(String textFilePath, String data) {
        try {
            FileWriter fileWriter = new FileWriter(textFilePath);
            fileWriter.write(data);
            fileWriter.close();
            logger.info("Successfully wrote to the file.");
        } catch (IOException e) {
            logger.info("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String readDataFromTextFile(String textFilePath) {
        String data = null;
        try {
            File myObj = new File(textFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.info("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
}

