package testScripts;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.*;

public class GETMethod extends testScripts.ListenerTest {

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

        Response response = (Response) given().get("https://reqres.in/api/users/2")
                .andReturn();
            test.get().info(response.prettyPrint());
    }
    /*
    Getting Response Data
     */
    @Test
    void responseData() throws IOException {
        String json = get("https://reqres.in/api/users/2").asString();
        System.out.println("String json ---> " +json );
        test.get().info("String json ---> " +json);

    }

}
