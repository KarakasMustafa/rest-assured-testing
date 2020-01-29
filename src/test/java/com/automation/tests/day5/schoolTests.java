package com.automation.tests.day5;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class schoolTests {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("school.uri");
    }

    @Test
    @DisplayName("Delete a student by his/her id")
    public void test1(){
        Response response = given().
                                accept(ContentType.JSON).
                when().
                        delete("/student/delete/{id}",2559);
    }

    @Test
    public void test3(){
        given().
                accept(ContentType.JSON).
                when().
                get("/students").body().prettyPrint();
    }

    @Test
    @DisplayName("Create a new Student")
    public void test2(){
        String student = "{\n" +
                "  \"admissionNo\": \"4561\",\n" +
                "  \"batch\": 12,\n" +
                "  \"birthDate\": \"01/01/1990\",\n" +
                "  \"company\": {\n" +
                "    \"address\": {\n" +
                "      \"city\": \"Nashville\",\n" +
                "      \"state\": \"Tennessee\",\n" +
                "      \"street\": \"7925 Jones Branch Dr\",\n" +
                "      \"zipCode\": 22102\n" +
                "    },\n" +
                "    \"companyName\": \"Cybertek\",\n" +
                "    \"startDate\": \"02/02/2020\",\n" +
                "    \"title\": \"SDET\"\n" +
                "  },\n" +
                "  \"contact\": {\n" +
                "    \"emailAddress\": \"nelsonMandela@gmail.com\",\n" +
                "    \"phone\": \"5459872542\",\n" +
                "    \"premanentAddress\": \"7925 Jones Branch Dr\"\n" +
                "  },\n" +
                "  \"firstName\": \"Nelson\",\n" +
                "  \"gender\": \"Males\",\n" +
                "  \"joinDate\": \"01/01/3321\",\n" +
                "  \"lastName\": \"Mandela\",\n" +
                "  \"major\": \"CS\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"section\": \"101010\",\n" +
                "  \"subject\": \"Math\"\n" +
                "}";
                Response response = given().
                            contentType(ContentType.JSON).
                        body(student).
                        post("/student/create").prettyPeek();


    }
}
