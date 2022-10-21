package testScripts;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GETMethod {

     /* In below test we will make get request to our endpoint
    1. validate StatusCode
    2. log all response
    */

    //checking Status Code for Get Call
    @Test
    public void ValidateStatusCode()
    {
        given().get("https://bookstore.toolsqa.com/BookStore/v1/Books").then().statusCode(200);
    }

    //Print response in logs
    @Test
    public void PrintResponse()
    {

        given().get("https://reqres.in/api/users/2").then().log().all();
    }


}
