package com.automation.tests.day7;

import com.automation.pojos.Student;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class PreSchoolTests {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("school.uri");
    }


    @Test
    @DisplayName("Get student with id 2633 and convert payload into POJO")
    public void test1(){
        Response response = given().
                                accept(ContentType.JSON).
                                pathParam("id",2633).
                            when().
                                get("/student/{id}");

        Student student = response.jsonPath().getObject("students[0]",Student.class);
        System.out.println(student);

        assertEquals(2633,student.getStudentId());
        assertEquals(11,student.getBatch());
        assertEquals("123456",student.getAdmissionNo());
        assertEquals("7925 Jones Branch Dr #3300",student.getContact().getPermanentAddress());
    }


}
