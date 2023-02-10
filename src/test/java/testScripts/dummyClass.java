package testScripts;

import io.restassured.response.Response;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class dummyClass extends testScripts.ListenerTest {



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

    @Test(timeOut = 6000)
    public void PrintResponse() throws InterruptedException {
        for (int i = 1; i <= 4; i++) {
            Thread.sleep(1000);
            System.out.println(i+"Seconds");
        }
            given().get("https://reqres.in/api/users/2").then().log().all();
    }


    @Test
    public void AwaitwithResponseCode() {
        int code = 50;
        int statuscode;
        for (int i = 1; i <= 5; i++) {
            statuscode = i * code;
            System.out.println("random StatusCode" + statuscode);

            if (statuscode == 200) {
                int finalStatuscode = statuscode;
                Response response =
                        given()
                                .get("https://reqres.in/api/users/2")
                                .andReturn();
                System.out.println("Making the get call");
                Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.TWO_SECONDS)
                        .until(() -> response.statusCode() == finalStatuscode);
                System.out.println("ResponseCode" + response.statusCode());
                System.out.println("ResponseCode" + response.prettyPrint());
                break;
            }
        }
    }
}
