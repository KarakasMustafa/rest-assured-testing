package com.automation.tests.day8;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class CalendarificTestsAPIKey {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("calendarific.uri");
    }

    /**
     * API key is a secret that the API generates and gives to the developer.
     * API key looks like long string: ebe88078e981c84bedeb9e8a34ad927560e545f2
     * API key can go as query parameter or inside a header,
     * it depends on web service how you must pass API key (as header parameter or query parameter)
     * How it get's created? You go to web site, register, and service gives you API key
     * Then you have to pass API key alongside with every request
     * API key is easy to implement for developer and client
     * But, non-technical people have no idea about this
     * So it's mostly used by developers only
     */

    /**
     * Given accept content type as JSON
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 401
     * And user verifies that status line contains "Unauthorized" message
     * And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */


    @Test
    @DisplayName("verify that user can not access web service without valid API key")
    public void test1(){
        given().
                accept(ContentType.JSON).
        when().
                get("/countries").prettyPeek().
        then().
                assertThat().
                statusCode(401).
                statusLine(containsString("Unauthorized")).  //status line: HTTP/1.1 401 Unauthorized
                body("meta.error_detail",containsString("Missing or invalid api credentials."));
    }
    /*
    {
    "meta": {
        "code": 401,
        "error_type": "auth failed",
        "error_detail": "Missing or invalid api credentials. See https://calendarific.com/api for details."
    },
    "response": [                            // here we called error_detail through "meta" by meta.error_detail
    ]
}
     */

    /**
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 200
     * And user verifies that status line contains "OK" message
     * And user verifies that countries array is not empty
     */

    @Test
    @DisplayName("Verify that user gets 200 status code with query param 'api_key' ")
    public void test2(){
        given().
                accept(ContentType.JSON).
                queryParam("api_key","316c797fe159817869de1394bbc940de2508107a").
        when().
                get("/countries").prettyPeek().
        then().
                assertThat().
                statusCode(200).
                statusLine(containsString("OK")).
                body("response.countries",not(empty()));
    }

    /**
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * And query parameter country is equals to US
     * And query parameter type is equals to national
     * And query parameter year is equals to 2019
     * When user sends GET request to "/holidays"
     * Then user verifies that number of national holidays in US is equals to 11
     */

    @Test
    @DisplayName("Verify that US have 11 national holidays")
    public void test3(){
        given().
                accept(ContentType.JSON).
                queryParam("api_key","316c797fe159817869de1394bbc940de2508107a").
                queryParam("country","US").
                queryParam("type","national").
                queryParam("year","2019").
        when().
                get("/holidays").
        then().
                assertThat().
                statusCode(200).
                body("response.holidays.size",is(11));
            //  body("response.holidays", hasSize(11));

        /* ---------Vasyl's------------
        Response response = given().
                accept(ContentType.JSON).
                queryParam("api_key","316c797fe159817869de1394bbc940de2508107a").
                queryParam("country","US").
                queryParam("type","national").
                queryParam("year","2019").
        when().
                get("/holidays");

        //List<Map<String, ?>> - list of objects, since there are nested objects, we cannot specify some value type
        List<Map<String, ?>> holidays = response.jsonPath().get("response.holidays");
        /regular way
        assertEquals(11, holidays.size(), "Wrong number of holidays!");
        assertEquals(200, response.getStatusCode());
         */
    }



}
