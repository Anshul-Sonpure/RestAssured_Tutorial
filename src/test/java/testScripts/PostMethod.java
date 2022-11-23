package testScripts;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssured_Tutorials.BookDetails;
import org.RestAssured_Tutorials.DummyUser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostMethod extends testScripts.ListenerTest {

    /* In below test will be making POST call in
     different ways.
    1. using map
    2. using json object
    3. using json file
    4. using POJO class -- concept of serialization-deserialization  is used in here
    5. using dataprovider
    */

    /* this test will post name,job
        and will register a new user,we are validating statuscode
        and log all response
   */
    @Test
    public void PostUsingMap()
    {
        HashMap map = new HashMap();

        map.put("name", "traveler");
        map.put("job", "Leader");
        Response response= (Response) given()
                .contentType("application/json")
                .body(map)
                .when()
                .post("https://reqres.in/api/users");
        String statusline = response.getStatusLine();
        Assert.assertEquals("HTTP/1.1 201 Created",statusline);
        test.get().info("PostUsingMap"+map+response.prettyPrint());
    }

    /* this test will post email,password
        and will register a new user,we are validating statuscode
        and token generated
   */
    @Test
    public void PostUsingJsonObject()
    {
        JSONObject request = new JSONObject();
        request.put("email","eve.holt@reqres.in");
        request.put("password","pistol");
        String token=
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        Assert.assertEquals(token,"QpwL5tke4Pnpja7X4");
        test.get().info("Assertion done for token"+token);
    }

    /* this test will post useremail without password and since
    no password is provided we expect and error message as response
    */
    @Test
    public void PostViaJsonFile()
    {
        File jsonData = new File("src/main/resources/user.json");
         given()
                .contentType(ContentType.JSON)
                .body(jsonData)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error",equalTo("Missing password"));
        test.get().info("Validated error message as:Missing password");
    }

    /*
    below test make use of BookDetails POJO class "src\main\java\org\RestAssured_Tutorials" and will use the setter method to set the data
    and make a POST Call to the end point and will validate StatusCode
     */
    @Test
    public void PostviaPOJO()
    {
        BookDetails bkdt = new BookDetails();
        bkdt.setTitle("HarryPotterV1");
        bkdt.setBody("HarryPotter in India");
        bkdt.setId(1);

        Response response= (Response) given()
                .contentType("application/json").body(bkdt)
                .when().post("https://jsonplaceholder.typicode.com/posts");
        int statuscode= response.getStatusCode();
        Assert.assertEquals(201,statuscode);
        test.get().info("PostViaJsonFile"+response.prettyPrint());
    }

    @DataProvider(name="testdata")
    public String[][] UserData()
    {
        return new String[][]
                {
                        {"Ervin Howell","Antonette","Shanna@melissa.tv"},
                        {"Adam Hitchok","Hitchok","Hitchok@melissa.tv"},
                        {"Madison Lee","Hitchok","Madison@melissa.tv"},
                };
    }

    @Test(dataProvider = "testdata")
    public void PostviaDataTable(String name,String username,String email)
    {
        JSONObject object = new JSONObject();
        object.put("name",name);
        object.put("username",username);
        object.put("email",email);

        Response response= (Response) given().contentType("application/json")
                .body(object)
                .when().post("https://jsonplaceholder.typicode.com/users");
        int statuscode= response.getStatusCode();
        Assert.assertEquals(201,statuscode);
        test.get().info("PostviaDataTable"+response.prettyPrint());
    }

    @Test
    public void DummyUserBuilder()
    {
        DummyUser Dummyuser = DummyUser.builder().firstName("Maddock").lastName("Kenith").age("32").gender("Male")
                .username("KenMad").password("Mad@Ken12").build();
        Response response= (Response) given().contentType("application/json")
                .body(Dummyuser)
                .when().post("https://dummyjson.com/users/add");
        int statuscode= response.getStatusCode();
        Assert.assertEquals(200,statuscode);
        test.get().info("Post using Builder"+response.prettyPrint());
    }
}
