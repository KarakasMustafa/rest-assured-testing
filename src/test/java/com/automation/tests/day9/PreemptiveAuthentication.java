package com.automation.tests.day9;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class PreemptiveAuthentication {

    @BeforeAll
    public static void setup(){
        baseURI = "http://practice.cybertekschool.com";
    }

    @Test
    @DisplayName("Normal basic authentication")
    public void test1(){
        given().
                auth().basic("admin","admin").
        when().
                get("/basic_auth").prettyPeek().
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    @DisplayName("Preemptive authentication")
    public void test2(){
        given().
                auth().preemptive().basic("admin","admin").
        when().
                get("/basic_auth").prettyPeek().
        then().
                assertThat().
                statusCode(200);
    }



}
