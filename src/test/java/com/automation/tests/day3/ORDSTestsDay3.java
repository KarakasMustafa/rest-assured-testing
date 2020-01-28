package com.automation.tests.day3;
import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");

    }

    // accept("application/json") shortcut for header("Accept","application/json"))
    // we are asking for json as a response
    @Test
    public void test1(){
        given().accept("application/json").
                get("/employees").
        then().
                assertThat().statusCode(200).
                and().assertThat().contentType("application/json").
                log().all(true); // log all is gonna print as prettyprint
    }


}
