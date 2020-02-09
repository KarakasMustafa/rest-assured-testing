package com.automation.tests.day9;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class warmUp {

    /**
     *  ####### WARM-UP TASK ########
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that total number of holidays in United Kingdom is equals to 95
     *
     * website: https://calendarific.com/
     */

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("calendarific.uri");
    }

    @Test
    @DisplayName("Verify that total number of holidays in United Kingdom is equal to 95")
    public void test1(){
        Response response = given().
                                accept(ContentType.JSON).
                                queryParam("api_key","aaf312cd592b0c7b22d90dd82e805dd4cf182dd5").
                when().
                        get("/countries").prettyPeek();

        response.then().assertThat().statusCode(200);
        // Gpath - it's like Xpath, stands for searching values in JSON file.
        // Gpath : response.countries.find {it.country_name == 'United Kingdom'}.total_holidays
        // find--> method to find some parameter
        //{it.parameter_name == value} find JSON object that is matching criteria
        //.parameter_that_you_want = return this parameter after filtering
        int numberHolidays = response.jsonPath().get("response.countries.find {it.country_name == 'United Kingdom'}.total_holidays");

        List<String> countries = response.jsonPath().get("response.countries.findAll {it.supported_languages == 4}.country_name");
        System.out.println(countries);
        assertEquals(95,numberHolidays);
    }

}
