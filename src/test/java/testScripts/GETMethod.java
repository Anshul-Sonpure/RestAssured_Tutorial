package testScripts;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.*;

public class GETMethod extends ExtentReporterNG {

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
    /*
    Getting Response Data
     */
    @Test
    void responseData() throws IOException {
        InputStream stream = get("https://reqres.in/api/users/2").asInputStream(); // Don't forget to close this one when you're done
        byte[] byteArray = get("https://reqres.in/api/users/2").asByteArray();
        String json = get("https://reqres.in/api/users/2").asString();
        stream.toString();
        stream.close();

        System.out.println("InputStream stream ---> " +stream );
        System.out.println("byte[] byteArray ---> " +byteArray );
        System.out.println("String json ---> " +json );

    }

}
