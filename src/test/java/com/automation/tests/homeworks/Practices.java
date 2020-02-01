package com.automation.tests.homeworks;

import com.automation.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class Practices {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("spartans.uri");
    }

    @Test
    @DisplayName("Create a new spartan")
    public void test1(){
        String spartan = "\"name\":\"BootSpartan\",\n" +
                "\"gender\": \"Male\",\n" +
                "\"phone\": 1311814806";

        given().contentType(ContentType.JSON).
                body(spartan).post("/200").prettyPrint();
    }
}
