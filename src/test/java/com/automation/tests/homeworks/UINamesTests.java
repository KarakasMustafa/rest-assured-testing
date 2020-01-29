package com.automation.tests.homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class UINamesTests {

    String baseURI = "https://uinames.com/";

    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON).when().get(baseURI);
        response.then().
                assertThat().body("name",is(not(null)),
                "surname",is(not(null)),
                "gender",is(not(null)),
                "region",is(not(null))).log().all();
       // response.prettyPrint();

    }
}
