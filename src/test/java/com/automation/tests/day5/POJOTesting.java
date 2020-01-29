package com.automation.tests.day5;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class POJOTesting {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }


    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        // this is deserialization
        // from JSON to POJO (java object)
        Job job = jsonPath.getObject("items[0]",Job.class); // Job.class type of POJO that you are going to create from JSON
        System.out.println(job);
    }

    @Test
    @DisplayName("Convert from POJO to JSON")
    public void test2(){
        Job sdet = new Job("SDET", " Software Development Engineer in Test", 5000, 20000);
        Gson gson = new Gson();
        gson.toJson(sdet);  // to covert POJO to JSON --> serialization

        String json = gson.toJson(sdet);
        System.out.println(json);
    }

    @Test
    @DisplayName("Convert all POJO ")
    public void test3(){
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/jobs");

        List<Job> list = response.jsonPath().getList("items",Job.class);

        for (Job job : list){
            System.out.println(job);
        }
    }

    @Test
    @DisplayName("Test3 in a little different way")
    public void test4(){
        Response response = given().
                        accept(ContentType.JSON).
                when().
                        get("/jobs");

        JsonPath jsonPath = response.jsonPath();

        List<Job> jobs = jsonPath.getList("items",Job.class);
        for (Job job : jobs){
            System.out.println(job.getJob_title());
        }
    }


    @Test
    @DisplayName("Convert from JSON to Location POJO")
    public void test5(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations/{location_id}",2500);
        Location location = response.jsonPath().getObject("",Location.class);

        System.out.println(location);

        Response response2 = given().
                accept(ContentType.JSON).
                when().
                get("/locations");

        List<Location> locations = response2.jsonPath().getList("items",Location.class);
        for(Location each : locations){
            System.out.println(each);
        }
    }
}
