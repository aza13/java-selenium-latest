package helper;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Random;

public class FakeDataHelper {

    private static final Logger logger = Logger.getLogger(FakeDataHelper.class);

    Faker faker;

    public FakeDataHelper(){

        faker = new Faker(new Locale("en-IND"));
    }

    public String firstName(){

        return faker.name().firstName();
    }

    public String lastName(){

        return faker.name().lastName();
    }

    public String phoneNumber(){

        return faker.phoneNumber().phoneNumber();
    }

    public String email(){

        return faker.internet().emailAddress();
    }

    public int randomInt(int digits) {
        int minimum = (int) Math.pow(10, digits - 1); // minimum value with 2 digits is 10 (10^1)
        int maximum = (int) Math.pow(10, digits); // maximum value with 2 digits is 99 (10^2 - 1)
        Random random = new Random();
        return minimum + random.nextInt((maximum - minimum) + 1);
    }
}
