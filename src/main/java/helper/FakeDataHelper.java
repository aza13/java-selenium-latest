package helper;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Random;

public class FakeDataHelper {

    private static final Logger logger = Logger.getLogger(FakeDataHelper.class);

    static Faker faker = new Faker(new Locale("en-US"));


    public static String firstName() {

        return faker.name().firstName();
    }

    public static String fullName() {

        return faker.name().fullName();
    }

    public static String phoneNumber() {

        return faker.phoneNumber().phoneNumber();
    }

    public static String email() {

        return faker.internet().emailAddress();
    }

    public static String address() {

        return faker.address().fullAddress();
    }

    public static String zipcode() {

        return faker.address().zipCodeByState("CA");
    }

    public static String city() {

        return faker.address().city();
    }

    public static String website() {
        String website = faker.company().url();
        return "https://"+website;
    }


    public int randomInt(int digits) {
        int minimum = (int) Math.pow(10, digits - 1); // minimum value with 2 digits is 10 (10^1)
        int maximum = (int) Math.pow(10, digits); // maximum value with 2 digits is 99 (10^2 - 1)
        Random random = new Random();
        return minimum + random.nextInt((maximum - minimum) + 1);
    }
}
