package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class MetaWeatherTests {

    /**
     * /api/location/search/?query=san
     * /api/location/search/?query=london
     * /api/location/search/?lattlong=36.96,-122.02
     * /api/location/search/?lattlong=50.068,-5.316
     */
// Examples above

    private String baseURI = "https://www.metaweather.com/api/";

    @Test
    public void test1(){
        Response response = given().baseUri(baseURI + "location/search/").queryParam("query","san").get();
        assertEquals(200,response.getStatusCode());
        System.out.println(response.prettyPrint());
    }

    /**
     * /api/location/{woeid}
     * /api/location/44418/ - London
     * /api/location/2487956/ - San Francisco
     */

    @Test
    public void test2(){
        Response response = given().pathParam("woeid","44418").get(baseURI+ "location/{woeid}");
        System.out.println(response.prettyPrint());
    }
// path parameter stands for fetching specific resource, like : some city info, user, etc..
// query parameters stands for filtering purpose : out of all users, we need user with last name Bond,
    @Test
    public void test3(){
        Response response = given().pathParam("woeid","2487956").get(baseURI+ "location/{woeid}");
        System.out.println(response.prettyPrint());
    }


}
