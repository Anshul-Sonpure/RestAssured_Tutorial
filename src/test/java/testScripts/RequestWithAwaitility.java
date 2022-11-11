package testScripts;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.json.simple.JSONObject;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



/*
 In below test we will 10 post call
 but when ranNum is even then a post call will be made
 next post call will be made once the status code is 200
 */
public class RequestWithAwaitility extends testScripts.ListenerTest{
    public static Response response;
    String pattern = "([0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z)";
    Pattern r = Pattern.compile(pattern);
    String id;
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

/*
Below test is the best example how to use awaitility
we are making a get call and after 5 seconds we make another get call using path parameter to an endpoint
*/
    @Test
    void getViaAwaility() {
        Response body = given().get("https://reqres.in/api/users/");
        id = body.path("data[2].id").toString();
        System.out.println("id is ::"+id);
        Awaitility.
                await()
                .atMost(20, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() ->
        {
            Thread.sleep(5000);
            int z;
            for(z =0;z<=5;z++){
                Thread.sleep(1000);
                System.out.println("Waiting:"+z+"seconds");
            }
            System.out.println("Calling Get request");
                    response= given().pathParam("id",id).when().get("https://reqres.in/api/users/{id}");
                    String email = response.path("data.email",equalTo("emma.wong@reqres.in").toString());
            System.out.println(email);
                    if(response != null)
                        return  true;
                    else
                        return false;
        });
    }
/*
   In below Await scenario we will make get call until the response code
   matches 200
   to mimick the status code received back we have used forloop
    and some variable
 */
    @Test
    public void AwaitwithResponseCode() {
        int code = 50;
        int statuscode;
        for (int i = 1; i <= 5; i++) {
            statuscode = i * code;
            System.out.println("random StatusCode" + statuscode);
            Response response =
                    given()
                            .get("https://reqres.in/api/users/2")
                            .andReturn();
            if (statuscode == 200) {
                int finalStatuscode = statuscode;
                System.out.println("Making the get call");
                Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS)
                        .until(() -> response.statusCode() == finalStatuscode);
                System.out.println("ResponseCode" + response.statusCode());
                break;
            }
        }
    }
    /*
    Another implementation for making a timeout request.
     */
    @Test(timeOut = 6000)
    public void PrintResponse() throws InterruptedException {
        for (int i = 1; i <= 4; i++) {
            Thread.sleep(1000);
            System.out.println(i+"Seconds");
        }
       given().get("https://reqres.in/api/users/2").then().log().all();

    }


}
