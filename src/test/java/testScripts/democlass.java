package testScripts;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class democlass extends testScripts.ListenerTest {


    @Test
    public void ExtractElementUsingFind()
    {
        String id = given().when().get("https://automationexercise.com/api/productsList").andReturn()
                .jsonPath().getString("products.find{it.name=='Green Side Placket Detail T-Shirt'}.id");
        System.out.println(id);
    }
}
