package api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;



public class ServiceValidationTests {

    @BeforeMethod
    public void beforeMethod() {

        baseURI = "url";

        RestAssured.registerParser("text/plain", Parser.JSON);

    }

    @Test
    public void getLoginUserDetails() {

        given().header("userName", "corp").header("password", "Pass@123")
                .when().post("/loginUserDetails")
                .then().statusCode(200);
    }

    @Test
    public void getAuthorities() {

        Response response = given().header("userName", "corp").header("password", "Pass@123")
                .when().post("/loginUserDetails")
                .then().assertThat().statusCode(200)
                .and().extract().response();

        JsonPath jsonPath = response.jsonPath();

        System.out.println("Response: "+response.getBody().asString());

        //System.out.println(jsonPath.get("visibleBranches.XX").toString());

        //System.out.println(jsonPath.get("authorites[0].role.role").toString());

        List<String> roles = jsonPath.get("authorites[0].role.role");

        assertThat(roles, hasItem("ROLE_DIGITAL_SIGNATURE"));

        assertThat(roles, hasItems("ROLE_DIGITAL_SIGNATURE", "ROLE_TPPG_NEWUI"));


    }
}
