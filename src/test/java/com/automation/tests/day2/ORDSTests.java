package com.automation.tests.day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class ORDSTests {

    // address of ORDS web service, that is running on AWS EC2
    // data is coming from SQL Oracle data base to this web service
    //during back-end testing with SQL developer and JDBC API
    // we were accessing data base directly
    // now, we are gonna access web service
    // according to OOP conventions, all instance variable should be private
    // but if we make it public, it will not make a difference

    private String baseURI = "http://ec2-52-90-241-197.compute-1.amazonaws.com:1000/ords/hr";

    // we start from given(), it works without RestAssured.given() cause we imported static RestAssured
    // the we can specify type of request like : get(), put(), delete(), post()
    // and as parameter, we enter resource location(URI)
    // then we are getting response back. That response we can put into Response object
    // from response object, we cab retrieve: body, header, status code

    // verify that status code is 200
    @Test
    public void test1(){
        Response response = given().get("http://ec2-52-90-241-197.compute-1.amazonaws.com:1000/ords/hr/employees");
 //       System.out.println(response.getBody().asString());    // print all in one line
        assertEquals(200,response.getStatusCode());

        System.out.println(response.getBody().prettyPrint()); // print as json on console (more organized )

    }

    // TASK:
    // get employee with id 100 and verify that response status code is 200
    @Test
    public void test2(){
        int id = 100;
        // header stands for metadata
        // usually it's used to include cookies
        // in this example, we are specifying what kind f response type we need
        // because web service can return json or xml
        // when we put header info "Accept", "application/json", we're saying that we need only json as response
        Response response = given().header("Accept","application/json") // we want to get result as json file.
                .get(baseURI+"/employees/"+id);
        System.out.println(response.getBody().prettyPrint());
        assertEquals(200,response.getStatusCode());

    }

// TASK: perform GET request to / regions, print body and all headers
    @Test
    public void test3(){
        Response response = given().get(baseURI + "/regions");
        System.out.println(response.getBody().prettyPrint());

        // to get specific header
        Header header = response.getHeaders().get("Content-Type");

        // to print all headers one by one
        for(Header h : response.getHeaders()){
            System.out.println(h);
        }
        assertEquals(200,response.getStatusCode());
    }








}
