package com.automation.tests.homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class UINamesTests {

    @BeforeAll
    public static void setup(){
        baseURI = "https://uinames.com/api/";
    }


    @Test
    @DisplayName("Verify that following information are not null ")
    public void noParamTest(){
        Response response = given().accept(ContentType.JSON).when().get();
        response.then().
                assertThat().statusCode(200).
                contentType("application/json; charset=utf-8").
        body("name",is(notNullValue())).
        body("surname",is(notNullValue())).
                body("gender",is(notNullValue())).
                body("region",is(notNullValue()));
       response.prettyPrint();

    }

    @Test
    @DisplayName("Verify that under query gender = male the following info are correct ")
    public void genderTest(){
        given().
                accept(ContentType.JSON).
                queryParam("gender","male").
        when().
                get().
        then().
                assertThat().contentType("application/json; charset=utf-8").
                statusCode(200).
                body("gender",is("male")).log().all(true);
    }

    @Test
    public void genderTest2(){
        given().
                accept(ContentType.JSON).
                queryParam("gender","female"). // pass
                when().
                get().
                then().
                assertThat().contentType("application/json; charset=utf-8").
                statusCode(200).
                body("gender",is("female")).log().all(true);
    }

    @Test
    @DisplayName("Verify that by region and gender query parameter requests meet the expectations ")
    public void paramTest(){
        given().
                accept(ContentType.JSON).
                queryParam("region","Romania").
                queryParam("gender","female").
        when().
                get().
        then().
                assertThat().
                statusCode(200).
                contentType("application/json; charset=utf-8 ").
                body("gender",is("female"),"region",is("Romania")).
                log().all(true);
    }

    @Test
    @DisplayName("Verify that with invalid gender parameter will meet the following expectations")
    public void InvalidGenderTest(){
        given().
                accept(ContentType.JSON).
                queryParam("gender","No gender").
        when().
                get().
        then().
                assertThat().
                statusCode(400).
                statusLine(containsString("Bad Request")).
                body("error",is("Invalid gender")).
                log().all();
    }

    @Test
    @DisplayName("Verify that invalid query has following info")
    public void InvalidRegionTest(){
        given().
                accept(ContentType.JSON).
                queryParam("region","Kurdistan").  // breaks heart though. There is a region of Kurdistan...
        when().
                get().
        then().
                assertThat().
                statusCode(400).
                statusLine(containsString("Bad Request")).  // Not a bad request
                body("error",is("Region or language not found")). // have to look deep
                log().all(true);
    }

    @Test
    @DisplayName("Verify that all contents full names are different ")
    public void AmountAndRegion(){
        Response response = given().
                            accept(ContentType.JSON).
                            queryParam("region","United States").
                            queryParam("amount",4).
                when().
                            get();

        response.
                then().
                            assertThat().
                            statusCode(200).
                            contentType("application/json; charset=utf-8").
                            log().all(true);

        List<Map<String,String>> list = response.jsonPath().get();  // get all the contents in List of Map

        for (int i=0; i<list.size(); i++){      // we get each element from list and put in nested loop
            for (int j=1; j<list.size(); j++){  // to compare with rest of the elements but
                if(i==j)                        // we skip same index to prevent assertion error.
                    continue;
                assertNotEquals(list.get(i).get("name")+list.get(i).get("surname"),
                        list.get(j).get("name")+list.get(j).get("surname"));
            }
        }

    }

    @Test
    @DisplayName("Verify that request returns us correct parameters")
    public void parameterTest3(){
        Response response = given().
                            accept(ContentType.JSON).
                            queryParam("region","Hungary").
                            queryParam("gender","female").
                            queryParam("amount",5).
                when().
                            get();
        response.then().
                statusCode(200).
                contentType("application/json; charset=utf-8").
                log().all();

        List<Map<String, String>> list = response.jsonPath().get();

        for(int i=0; i<list.size(); i++){
            assertEquals("female",list.get(i).get("gender"));
            assertEquals("Hungary",list.get(i).get("region"));
        }
    }

    @Test
    @DisplayName("Verify that response contains the amount of objects specified in query parameter ")
    public void amountCountTest(){
        Response response = given().
                queryParam("amount",120).
        when().get();

        response.
                then().
                assertThat().statusCode(200).contentType("application/json; charset=utf-8").log().all();

        List<?> list = response.jsonPath().get();

        assertEquals(120,list.size());
    }

}
