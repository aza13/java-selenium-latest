package utils.dataProvider;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class JsonDataProvider {

    private JsonDataProvider() {
    }
    @DataProvider(name = "jsonDataReader")
    public static Object[][] getData(Method method) throws IOException, ParseException {

        String testCaseName = method.getName();

        Test testAnnotation = method.getAnnotation(Test.class);

        String testClassName = testAnnotation.description();

        String testDataFileName = "./src/main/java/utils/dataProvider/"+testClassName+".json";

        FileReader fileReader = new FileReader(testDataFileName);

        JSONParser jsonParser = new JSONParser();

        Object javaObject = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) javaObject;

        JSONArray jsonArray = (JSONArray) jsonObject.get(testCaseName);

        int enabledDataSetsCount = 0;

        for (Object o : jsonArray) {
            JSONObject testDataSetObject = (JSONObject) o;
            if (testDataSetObject.get("runMode").equals("Y")) {
                enabledDataSetsCount += 1;
            }
        }
        Object[][] excelData = new Object[enabledDataSetsCount][1];
        if(enabledDataSetsCount!=0){
            for (int i = 0; i < enabledDataSetsCount; i++) {
                JSONObject testDataSetObject = (JSONObject) jsonArray.get(i);
                if (testDataSetObject.get("runMode").equals("Y")) {
                    excelData[i][0]=testDataSetObject;
                }
            }
        }
        return excelData;
    }
}
