package com.automation.tests.day8;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class OMDBTestsAPIKey {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("omdb.uri");
    }

    @Test
    @DisplayName("Verify that unauthorized user can not get info about movies from OMDB api")
    public void test1(){
        given().
                contentType(ContentType.JSON).
                queryParam("t","Home Alone").
        when().
                get().prettyPeek().
        then().
                assertThat().
                statusCode(401). // 401 Unauthorized
                body("Error",is("No API key provided."));
    }

    @Test
    @DisplayName("Verify that Macaulay Culkin appears in actors list for Home Alone movie")
    public void test2(){
        given().
                contentType(ContentType.JSON).
                queryParam("apikey","9f94d4d0").
                queryParam("t","Home Alone").
        when().
                get().prettyPeek().
        then().
                assertThat().
                statusCode(200).
                body("Actors",containsString("Macaulay Culkin"));
    }

    @Test
    @DisplayName("Test 2 ")
    public void test3(){
        Response response = given().
                contentType(ContentType.JSON).
                queryParam("apikey","9f94d4d0").
                queryParam("t","Home Alone").
                when().
                get();

                response.then().
                assertThat().
                statusCode(200).
                body("Actors",containsString("Macaulay Culkin"));

        Map<String,Object> payload = response.getBody().as(Map.class);
        System.out.println(payload);

        for (Map.Entry<String,Object> entry : payload.entrySet()){
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

    }






}
