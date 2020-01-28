package com.automation.tests.day3;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    // path parameter --> to point on specific resource ==> /employees/:id/ -- /id is path parameter
    // query paramater  --> to filter results, or describe new resource :
    // POST  /users?name=Jamal get all employees with name Jamal
    @Test
    public void test2(){
        given().
                accept("application/json").pathParam("id",100).
        when().get("employees/{id}").then().
                assertThat().statusCode(200).
        and().assertThat().body("employee_id",is(100)).
                assertThat().body("department_id",is(90)).
                assertThat().body("last_name",is("King")).
                log().all();
        // body("phone_number") --> 515.123.4567
        // is() is coming from org.hamcrest.Matcher
    }


    // test3 is a different version of test2, cause we put all the body() in one.(combined)
    @Test
    public void test3(){
        given().
                accept("application/json").pathParam("id",100).
                when().get("employees/{id}").then().
                assertThat().statusCode(200).
                and().assertThat().body("employee_id",is(100),
                "department_id",is(90),
                "last_name",is("King")).
                log().all();
    }

    /**  TASK:
     * given path parameter is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     */

    @Test
    public void test4(){
        given().
                accept("application/json").pathParam("id",1).
        when().
                get("/regions/{id}").
        then().
                assertThat().statusCode(200).
                assertThat().body("region_name",is("Europe")).
                time(lessThan(1L), TimeUnit.SECONDS).   // verify that response time is less than 1 second.
                log().body(true);  // log body in pretty format (true)
        // log(). all() --> all = header + body + status code.
    }

    // we are trying to get the name or other parameters of an employee, we can use jsonPath too.
    // instead of specifying the id, we call the info of the employee and then able to use all parameters of it.

    @Test
    public void test5(){
        JsonPath jsonPath = given().
                accept("application/json").
        when().
                get("/employees").
        thenReturn().jsonPath();

        String nameOfFirstEmployee = jsonPath.getString("items[0].first_name"); // 0 means first
        String nameOfLastEmployee = jsonPath.getString("items[-1].first_name"); // -1 means last .

        System.out.println("First employee name: " + nameOfFirstEmployee);
        System.out.println("Last Employee name: " + nameOfLastEmployee);

        // since first employee is a map( key=value) pair, we can iterate through it by using Entry, entry represent one
        // key=value pair.
        // put ? as value, because there are values of different types : String, int , etc..
        // if we put String as Value, we might get some casting exception that cannot convert from integer(or something)
        Map<String , ?> firstEmployee = jsonPath.get("items[0]");
        System.out.println(firstEmployee);

        for(Map.Entry entry : firstEmployee.entrySet()){
            System.out.println("Key : " + entry.getKey() + ", Value :" + entry.getValue());
        }

        // get and print all last names,
        // items is an object, whenever we need to read some property from thr object, we can put object.property
        // but, if response has multiple objects, we can get property from every object
        List<String> lastNames = jsonPath.get("items.last_name");
        for(String str : lastNames){
            System.out.println("last Name : " + str);
        }
    }

    // write a code to
    // get info from countries as List<Map<String, ?>>
    // prettyPrint() --> print json/xml/html in nice format and returns String , thus we can not retrieve json path.
    // prettyPeek() --> does same job, but returns Response object, and from tht we can get json path.
    @Test
    public void test6(){
        JsonPath jsonPath = given().
                accept("application/json").
        when().
              get("/countries").prettyPeek().jsonPath();  // prettypeek returns response..

        List<Map<String, ?>> listOfCountries = jsonPath.get("items");

        System.out.println(listOfCountries);

        // when we read data from json response, values are not only strings
        // so if we are not sure that all values have same data type,
        // we can put ?
        for(Map<String, ?> map : listOfCountries){
            System.out.println(map);
        }
    }

    /*
    get collection of employee's salaries
    then sort it.
    and print it.
     */

    @Test
    public void test7(){
        List<Integer> salaries = given().
                accept("application/json").
           when().
                get("/employees").thenReturn().jsonPath().get("items.salary");

        Collections.sort(salaries);
        System.out.println(salaries);

        Collections.reverse(salaries); // reverse to get them in descending order
        System.out.println(salaries);
    }

    /*
    Get collection of phone numbers, from employees
    and replace all dots "." in every phone number with dash "-"
     */

    @Test
    public void test8(){
        List<String> phoneNumbers = given().accept("application/json").
                when().get("/employees").
                    thenReturn().jsonPath().get("items.phone_number");

        // replaces each element of this list with result of applying the operator to that element.
        // replace "." with "-" in every value
        phoneNumbers.replaceAll(p -> p.replace(".","-"));
        System.out.println(phoneNumbers);
    }

    /** ####TASK#####
     *  Given accept type as JSON
     *  And path parameter is id with value 1700
     *  When user sends get request to /locations
     *  Then user verifies that status code is 200
     *  And user verifies following json path information:
     *      |location_id|postal_code|city   |state_province|
     *      |1700       |98199      |Seattle|Washington    |
     *
     */

    @Test
    public void test9(){
        Response response = given().
                accept(ContentType.JSON).
                pathParam("id", 1700).
                when().
                get("/locations/{id}");

        response.
                then().
                assertThat().body("location_id", is(1700)).
                assertThat().body("postal_code", is("98199")).
                assertThat().body("city", is("Seattle")).
                assertThat().body("state_province", is("Washington")).
                log().body();

    }


    // this is my way
    @Test
    public void test10(){
        given().
                accept("application/json").pathParam("id",1700).
        when().get("/locations/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("location_id",is(1700),
                "postal_code",is("98199"),
                "city",is("Seattle"),
                "state_province",is("Washington"));
    }

}
