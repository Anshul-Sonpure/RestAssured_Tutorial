package testScripts;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.RestAssured_Tutorials.BookDetails;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.json.simple.JSONObject;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.text.MatchesPattern.matchesPattern;


/*
 In below test we will 10 post call
 but when ranNum is even then a post call will be made
 next post call will be made once the status code is 200
 */
public class Awaility_postRequest {
    public static Response response;
    String pattern = "([0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z)";
    Pattern r = Pattern.compile(pattern);
    @Test
    public void PostUsingJsonObject() {
        for (int i = 0; i <= 10; i++) {
            int ranNum = (int) (Math.random() * (100 - 05 + 1) + 05);
            if (ranNum % 2 == 0) {
                System.out.println("ranNum::" + ranNum);
                System.out.println("Creating a User");
                JSONObject request = new JSONObject();
                request.put("email", "eve.holt@reqres.in");
                request.put("password", "pistol");
                Response response =
                        given()
                                .body(request)
                                .contentType(ContentType.JSON)
                                .post("https://reqres.in/api/register")
                                .andReturn();
                Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS)
                        .until(() -> response.statusCode() == 200);
                System.out.println(response.statusCode());

            } else {
                System.out.println("ranNum::" + ranNum);
                System.out.println("User not created");
            }
        }
    }

    /*
 In below test we will 10 post call
 but when ranNum is even then a post call will be made
 next post call will be made once the status code is 202
 but since valid status code is 200 so it will wait for 10 seconds
 */
    @Test
    public void PostUsingJsonObject2() {
        for (int i = 0; i <= 10; i++) {
            int ranNum = (int) (Math.random() * (100 - 05 + 1) + 05);
            if (ranNum % 2 == 0) {
                System.out.println("ranNum::" + ranNum);
                System.out.println("Creating a User");
                JSONObject request = new JSONObject();
                request.put("email", "eve.holt@reqres.in");
                request.put("password", "pistol");
                Response response =
                        given()
                                .body(request)
                                .contentType(ContentType.JSON)
                                .post("https://reqres.in/api/register")
                                .andReturn();
                Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS)
                        .until(() -> response.statusCode() == 202);
                System.out.println(response.statusCode());

            } else {
                System.out.println("ranNum::" + ranNum);
                System.out.println("User not created");
            }
        }
    }


    @Test
    void getViaAwaility() {
        Awaitility.
                await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() ->
        {
            final Response body = given().get("https://reqres.in/api/users/2");
            final boolean status = body.path("data.email").equals("janet.weaver@reqres.in");
            String email = body.path("data.email");
            System.out.println(email);
            return status;

        });

    }

}
