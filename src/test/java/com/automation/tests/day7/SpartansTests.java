package com.automation.tests.day7;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class SpartansTests {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("spartans.uri");
    }


    // add new spartan

    @Test
    @DisplayName("Add new user by using external JSON file")
    public void test1(){
        File file = new File(System.getProperty("user.dir")+"/spartan.json");

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(file).
                when().
                post("/spartans").prettyPeek().
                then().assertThat().
                statusCode(201).
                body("success", is("A Spartan is Born!"));
    }

    @Test
    @DisplayName("Add new user by usin map")
    public void test2(){
        Map<String, Object> spartan = new HashMap<>();

        spartan.put("phone",415756522156L);
        spartan.put("gender","Male");
        spartan.put("name","John Domn");

// you must specify content type, whenever you POST
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan).
        when().
                post("/spartans").prettyPeek().
        then().
                assertThat().
                statusCode(201).
                body("success",is("A Spartan is Born!")).
                body("data.name",is(spartan.get("name"))).
                body("data.phone",is(spartan.get("phone")));
    // in the response , we have spartan object inside data variable
    // to get properties we need to specify name of that object data
    // put . and parameter that we want to read
    // data.id , data.gender, data.name
    // success - property , string variable
    // data - object that represents spartan

    }

    @Test
    @DisplayName("")
    public void test3(){
        Map<String, Object> update = new HashMap<>();

        update.put("name","John Dept");

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(update).
                pathParam("id",135).
        when().
                patch("/spartans/{id}").prettyPeek().
        then().
                assertThat().
                statusCode(204);
        // since response doesn't contain body, after PATCH request,
        // we don't need accept(ContentType.JSON).
        // PUT - all parameters
        // PATCH - 1+ parameter(s)
    }
}
