package com.automation.tests.homeworks;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class Homework2 {

    @BeforeAll
    public static void setup(){
        basePath = "https://api.github.com";

    }

    @Test
    @DisplayName("Verify organization information")
    public void test1(){
        given().
                accept(ContentType.JSON).
                pathParam("org","cucumber").
        when().
                get("/orgs/:{org}").prettyPeek().
        then().assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8");

    }



}
