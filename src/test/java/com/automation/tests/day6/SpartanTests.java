package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class SpartanTests {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("spartans.uri");
    }

    /**
     * given accept content type as JSON
     * When user sends GET request to /spartans
     * then user verifies that status code is 200
     * and user verifies that content type is JSON
     */

    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as JSON")
    public void test1(){
        // Web service may return different content type
        // and to request JSON, you can just put in the given part ContentType.JSON
        // if you want to ask for XML, you can put contentType.XML
        // but if web service configured only for JSON
        //it will not give you anything else
        //GET, PUT, POST, DELETE, etc. - HTTP verbs or methods
        //GET - to get the data from web service
        //PUT - update existing record
        //DELETE - delete something, like delete spartan
        //PATCH - partial update of existing record
        given().
                accept(ContentType.JSON).
        when().
                get("/spartans").prettyPeek().
        then().
                assertThat().statusCode(200).
                contentType(ContentType.JSON);
    }


    /**
     * given accept content type as XML
     * When user sends GET request to /spartans
     * then user verifies that status code is 200
     * and user verifies that content type is XML
     */

    @Test
    @DisplayName("Verify that /spartans end-point returns 200 and content type as XML")
    public void test2(){
       given().
               accept(ContentType.XML).
       when().
               get("/spartans").prettyPeek().
       then().
               assertThat().
               statusCode(200).
               contentType(ContentType.XML);
    }

    /**
     * Given accept content type as JSON
     * when user sends GET request to /spartans
     * then user saves payload in collection
     *
     * We can convert payload (JSON body for example) into collection
     * if it's a single variable: "name" : "James" , we can store in String or List<String>
     * If there are multiple names in the payload, we cannot use single String as a storage
     * Instead, use List<String>
     */

    @Test
    @DisplayName("Save payload into java collection")
    public void test3(){
        Response response = given().
                                    contentType(ContentType.JSON).
                            when().
                                    get("/spartans");

        List<Map<String , ?>> collection = response.jsonPath().get();

        for (Map<String, ?> map : collection){
            System.out.println(map);
        }

    }

    /**
     * Given accept content type as JSON
     * when user sends GET request to /spartans
     * then user saves payload in collection
     */

    @Test
    @DisplayName("Save payload into java collection")
    public void test4(){
        Response response = given().
                                contentType(ContentType.JSON).
                            when().
                                get("/spartans");

        List<Spartan> collection = response.jsonPath().getList("",Spartan.class);

        for (Spartan each : collection){
            System.out.println(each);
        }
    }

    /**
     * TASK
     * Given accept type as JSON
     * when user sends POST request to /spartans
     * and user should be able to create new spartan
     * |gender  |name   |phone      |
     * |male    |Mustafa|571549635  |
     * then user verifies that status code is 201
     *
     * 201 - means that whenever you POST something, you should get back 201 status code
     */

    @Test
    @DisplayName("Create a new Spartan and verify that status code is 201")
    public void test5(){
        Spartan spartan = new Spartan();
        spartan.setGender("Male");
        spartan.setName("some user");
        spartan.setPhone(57154963675L);

        Spartan spartan1 = new Spartan().withGender("Male").
                                            withName("Mustafa").
                                            withPhone(5715345349635L);
        Response response = given().
                                contentType(ContentType.JSON).
                                body(spartan).
                            when().
                                post("/spartans");

        assertEquals(201,response.getStatusCode(),"Status code is wrong!");
        assertEquals("application/json",response.getContentType(),"Content type is invalid!");

        response.prettyPeek();

        Spartan spartanFromResponse = response.jsonPath().getObject("data",Spartan.class);
        System.out.println("Spartan id : "+ spartanFromResponse.getSpartanId());

        // delete the new spartan

        when().delete("/spartans/{id}",spartanFromResponse.getSpartanId());

    }

    @Test
    @DisplayName("Delete user")
    public void test6(){
        int idOfTheUser = 123;

        Response response = when().delete("/spartans/{id}",idOfTheUser);
        response.prettyPeek();
    }

    @Test
    @DisplayName("Delete half of the records")
    public void test7(){
        int idOfTheUser = 123;
        Response response = given().accept(ContentType.JSON).
                            when().
                                    get("/spartans");
        // I collected all user id's
        List<Integer> userIDs = response.jsonPath().getList("id");

        // sorted user id's in descending order
        Collections.sort(userIDs,Collections.reverseOrder());
        System.out.println("Before :" + userIDs);

        //I went through half of the collection, and deleted half of the users
        //userIDs.size()/2 - represents half of the spartans
        for (int i = 0; i < userIDs.size() / 2; i++) {
            //will delete spartan based on id that you specify
            when().delete("/spartans/{id}", userIDs.get(i));
        }

//        Response response2 = when().delete("/spartans/{id}", idOfTheUserThatYouWantToDelete);
//
//        response.prettyPeek();
    }



    @Test
    @DisplayName("Add 10 test users to Spartan app")
    public void test8(){
        Faker faker = new Faker();
        for(int i=0; i<10; i++){
            Spartan spartan = new Spartan();
            spartan.setName(faker.name().firstName());
            // remove all non-Digits
            // replaceAll() --> takes regex(regular expression)
            // regex --> it's pattern, means that 1 character can represent group of chars/symbols/digits
            // \\D --> everything that is not digit(0-9)
            String phone = faker.phoneNumber().subscriberNumber(11).replaceAll("\\D","");
//           phone.matches("\\d"); check if this string contains only digits
//           phone.matches("[a-x]"); check if this string contains letters in the range from a to x
            //convert from String to Long
            spartan.setPhone(Long.parseLong(phone));

            spartan.setGender("Female");

            System.out.println(spartan);

            Response response = given().
                    contentType(ContentType.JSON).
                    body(spartan).
                    when().
                    post("/spartans").prettyPeek();

            //whenever you successfully add new spartan, you will get this message: "A Spartan is Born!",
            System.out.println(response.jsonPath().getString("success"));
            //verify that response status code is 201,
            //in our case 201 means that post request went well
            assertEquals(201, response.getStatusCode());
        }
    }

    @Test
    @DisplayName("Update user")
    public void test9(){
        Spartan spartan = new Spartan().
                            withGender("Male").
                            withName("Super SDET 1657").
                            withPhone(15455224541L);

        Response response = given().
                                accept(ContentType.JSON).
                                contentType(ContentType.JSON).
                                body(spartan).
                                pathParam("id",60).
                                put("/spartans/{id}").prettyPeek();

        // pur update existing record
        // also when you make PUT request, you need to specify entire body
        // post - create new one
        // we never POST/PUT id, it must be auto generated
        // if it's not like this - its a bug


    }

    @Test
    @DisplayName("Update only name with PATCH")
    public void test10(){
        Map<String , String> update = new HashMap<>();
        update.put("name","Super SDET");

        Response response = given().
                                accept(ContentType.JSON).
                                contentType(ContentType.JSON).
                                body(update).
                                pathParam("id",60).
                                patch("/spartans/{id}");
        response.prettyPeek();
        // POST --> add new spartan
        // PUT ---> update existing one, but you have to specify all properties
        // PATCH --> Update existing one , but you may specify one or two properties (no need to update all)
    }

}
