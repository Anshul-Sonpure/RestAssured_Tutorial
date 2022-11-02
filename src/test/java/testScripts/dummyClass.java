package testScripts;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class dummyClass {
    @Test(timeOut = 2000) // time in milliseconds
    public void PassTest() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("This test was successful");
    }

    @Test(timeOut = 4000)
    public void FailTest() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("This test fails");
    }

    @Test
    public void PostUsingJsonObject() {
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
}
