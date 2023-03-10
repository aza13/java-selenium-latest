package utils.dataProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.fileReader.ConfigDataReader;
import utils.fileReader.ExcelDataReader;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class TestDataProvider {

    private static final Logger logger = Logger.getLogger(TestDataProvider.class);

    private static final List<String> testDescription = new ArrayList<>();

    private static String testDataFilePath;

    private TestDataProvider() {
    }

    @DataProvider(name = "ask-me")
    public static Object[][] dataProvider(Method method) throws IOException {

        logger.info("Executing dataProvider method");

        testDataFilePath = ConfigDataReader.getInstance().getProperty("testDataFilePath");

        logger.info("Test Data Path : " + testDataFilePath);

        String testCaseName = method.getName();

        logger.info("Test script to be executed is::" + testCaseName);

        Test testAnnotation = method.getAnnotation(Test.class);

        String dataSheetName = testAnnotation.description();

        logger.info("TestDataProvider Invoked for test-" + testCaseName + " and sheet-" + dataSheetName);

        Object[][] data;
        try {
            data = getTestData(dataSheetName, testCaseName);

        } catch (Exception e) {
            logger.error("Failed to get the test data for the test " + testCaseName + " due to :: " + e.getMessage());
            throw (e);
        }

        return data;
    }


    private static Object[][] getTestData(String sheetName, String testName) throws IOException {

        logger.info("In getTestData(), reading the test data from sheet");

        XSSFSheet currentDataSheet;

        currentDataSheet = ExcelDataReader.getExcelSheet(testDataFilePath, sheetName);

        logger.info("Got the excel sheet " + currentDataSheet);

        int totalRowCount = ExcelDataReader.getRowCount(currentDataSheet);

        logger.info("Total no of rows = " + totalRowCount + " :: getTestData");

        assert currentDataSheet != null;

        int currentTestRowNum = ExcelDataReader.getCurrentTestCaseRow(currentDataSheet, testName);

        if (currentTestRowNum != -1) {
            logger.info("firstDataSet of test case will be after rows from test case name");
            int firstDataSetRowNum = currentTestRowNum + 2;

            logger.info("counting number of data sets the test case has");
            int[] dataSetsCounts = ExcelDataReader.getTestDataSetsCount(currentDataSheet, currentTestRowNum);

            int lastDataSetRowNum = currentTestRowNum + dataSetsCounts[0] + 1;

            int parametersCount;
            logger.info("getting total number of parameters that test case has");
            parametersCount = currentDataSheet.getRow(currentTestRowNum + 1).getLastCellNum();

            logger.info("creating two dimension array with number of rows equal to number of enabled data sets");
            Object[][] excelData = new Object[dataSetsCounts[1]][1];

            int set = 0;

            for (int r = firstDataSetRowNum, k = 0; r <= lastDataSetRowNum && k < dataSetsCounts[0]; k++, r++) {

                Map<String, String> dataMap = new HashMap<>();

                if (Objects.equals(ExcelDataReader.getCellData(currentDataSheet, r, 0), "Y")) {

                    for (int c = 0; c < parametersCount; c++) {

                        dataMap.put(ExcelDataReader.getCellData(currentDataSheet, firstDataSetRowNum - 1, c), ExcelDataReader.getCellData(currentDataSheet, r, c));

                        if (Objects.requireNonNull(ExcelDataReader.getCellData(currentDataSheet, firstDataSetRowNum - 1, c)).equalsIgnoreCase("testDescription")) {
                            String var = ExcelDataReader.getCellData(currentDataSheet, r, c);
                            testDescription.add(var);
                        }
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(dataMap);
                    FileWriter fileWriter;
                        System.out.println(jsonString);
                        fileWriter = new FileWriter("src/main/resources/testData/"+testName+"_"+new Date().getTime()+".json");
                        fileWriter.write(jsonString);
                        fileWriter.flush();
                        fileWriter.close();
                    excelData[set][0] = dataMap;
                    set++;
                }
            }
            logger.info("The total number of test data sets enabled for run are " + excelData.length + " ::getTestDataNew");

            return excelData;
        } else {
            logger.info("Test data is not present for " + testName + " in the test data sheet :: getTestDataNew");
            return new Object[0][];
        }
    }
}
