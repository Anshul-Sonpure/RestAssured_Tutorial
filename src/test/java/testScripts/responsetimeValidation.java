package testScripts;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class responsetimeValidation {
    @Test
    public void validateResponseTime()
    {
        ValidatableResponse response = given()
                .when().get("https://reqres.in/api/users?page=2").then()
                .time(Matchers.lessThan(2000L));
        long gettime = given().when().get("https://reqres.in/api/users?delay=5").getTimeIn(TimeUnit.SECONDS);
        System.out.println(gettime);
    }
}
