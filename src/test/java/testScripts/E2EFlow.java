package testScripts;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class E2EFlow {

    public static int userId;
    public static String Token;

    /*
      In below we will see how we to perform End to End flow for api testing.
      we will be using bearer token as well for accessing protected routes.
       We will register a user(POST call),
       Then we will login with that user(POST call)
       Then we will make Get All Users and Get user by Id (GET call with authorization)
       Create User object(POST call),Update User Object(PATCH call),DELETE user by Id(Delete call)
     */


    /*
     In below test we will create a new user and store UserId and Token in token.txt file.
     for your testing just increase the Demouser<number>
     */

    @Test
    public void RegisterUser() throws IOException {
        JSONObject request = new JSONObject();
        request.put("name","Demouser131");
        request.put("email","Demouser131@gmail.com");
        request.put("password","Demo_user@131");
        Response response =
                given()
                        .contentType("application/json")
                        .body(request)
                        .when()
                        .post("http://restapi.adequateshop.com/api/authaccount/registration");
        JsonPath path = response.jsonPath();
        userId = path.get("data.Id");
        Token = path.get("data.Token");
        FileWriter fWriter = new FileWriter("src/main/resources/token.txt");
        fWriter.write(Token);
        System.out.println("UserId is : "+ userId + "&"+"Token is: "+ Token);
        fWriter.close();

    }


    @Test
    public void UserLogin()
    {
        JSONObject request = new JSONObject();
        request.put("email","Demouser131@gmail.com");
        request.put("password","Demo_user@131");
                String logintoken = given()
                        .contentType("application/json")
                        .body(request)
                        .when()
                        .post("http://restapi.adequateshop.com/api/authaccount/login")
                        .then().extract().path("data.Token");

        System.out.println(logintoken);
    }

    /*
      In below test we will set Token as Authorization header &
      then we will validate for userid 11133,11134,11135,11136
     */
    @Test
    public void Get_AllUsers() throws IOException
    {
        JSONObject request = new JSONObject();
        request.put("email","Demouser105@gmail.com");
        request.put("password","Demo_user@105");
        Token = given().contentType("application/json")
                .body(request)
                .when().post("http://restapi.adequateshop.com/api/authaccount/login")
                .then().extract().path("data.Token");
        System.out.println(Token);
        given().header("Authorization","Bearer "+Token)
                .header("Content-Type","application/json")
                .when().get("http://restapi.adequateshop.com/api/users?page=1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .assertThat().
                body("data.id",hasItems(11133, 11134, 11135, 11136, 11137, 11138, 11139, 11140, 11142, 11143))
                .log().all();


    }

    @Test
    public void CreateUser()
    {
        JSONObject request = new JSONObject();
        request.put("email","Demouser105@gmail.com");
        request.put("password","Demo_user@105");
        Token = given().contentType("application/json")
                .body(request)
                .when().post("http://restapi.adequateshop.com/api/authaccount/login")
                .then().extract().path("data.Token");
        System.out.println(Token);
        JSONObject createuser = new JSONObject();
        createuser.put("name","IronMan02");
        createuser.put("email","IronMan02@stark.io");
        createuser.put("location","USA");
        given().header("Authorization","Bearer "+Token)
                .header("Content-Type","application/json")
                .body(createuser)
                .when().post("http://restapi.adequateshop.com/api/users")
                .then().body("email",equalTo("IronMan02@stark.io")).log().all();



    }

    @Test
    public void GetuserbyId()
    {
        JSONObject request = new JSONObject();
        request.put("email","Demouser105@gmail.com");
        request.put("password","Demo_user@105");
        Token = given().contentType("application/json")
                .body(request)
                .when().post("http://restapi.adequateshop.com/api/authaccount/login")
                .then().extract().path("data.Token");
        System.out.println(Token);
        given().header("Authorization","Bearer "+Token)
                .header("Content-Type","application/json")
                .when().get("http://restapi.adequateshop.com/api/users/167655")
                .then()
                .statusCode(200)
                .body("email",equalTo("IronMan02@stark.io"))
                .log().all();
    }

    /*
        Update user is performed on different api-- https://dummy.restapiexample.com/update
        hence no token is required,**Note: please change the employee id when you
        wanna test as it will give error if you use same :)
     */
    @Test
    public void UpdateUser()
    {
        JSONObject request = new JSONObject();
        request.put("name","test");
        request.put("salary","123332");
        request.put("age","25");
        given().get("https://dummy.restapiexample.com/api/v1/employee/20")
                .then().log().all();
        given().contentType("application/json")
                .body(request)
                .when()
                .put("https://dummy.restapiexample.com/api/v1/update/20")
                .then().log().all();


    }


    @Test
    public void DeleteUser()
    {
        given()
                .when().delete("https://dummy.restapiexample.com/api/v1/delete/25")
                .then().statusCode(200)
                .body("message",equalTo("Successfully! Record has been deleted")).log().all();
    }


}
