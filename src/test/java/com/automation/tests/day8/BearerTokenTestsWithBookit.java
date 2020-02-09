package com.automation.tests.day8;

import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class BearerTokenTestsWithBookit {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("bookit.qa1");
    }

    // Let's get list of all rooms and verify that status code is 200
    // /api/rooms

    @Test
    @DisplayName("")
    public void test1(){
        Response response = given().
                                header("Authorization",getToken()).
                            when().get("/api/rooms").prettyPeek();
    }

    /**
     * Method that generates access token
     * @return
     */
    public String getToken(){
        Response response = given().
                queryParam("email",ConfigurationReader.getProperty("team.leader.email")).
                queryParam("password",ConfigurationReader.getProperty("team.leader.password")).
                when().
                get("/sign").prettyPeek();
        return response.jsonPath().getString("accessToken");
    }
}
