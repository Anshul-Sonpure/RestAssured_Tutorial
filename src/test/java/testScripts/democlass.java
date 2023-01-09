package testScripts;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.*;

public class democlass extends testScripts.ListenerTest {

    int id = (int)(Math.random()*(100-05+1)+05);

        @Test
        public void RegisterUser() throws IOException {


        JSONObject request = new JSONObject();
        request.put("name","Demouser"+id);
        request.put("email","Demouser"+id+"@gmail.com");
        request.put("password","Demo_user@"+id);

        Response response =
                given()
                        .contentType("application/json")
                        .body(request)
                        .when()
                        .post("http://restapi.adequateshop.com/api/authaccount/registration");
        JsonPath path = response.jsonPath();
            String Token = path.get("data.Token");
            System.out.println(Token);
        int userId=path.get("data.Id");
            System.out.println(userId);

    }
}
