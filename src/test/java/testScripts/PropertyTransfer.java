package testScripts;

/*
     In below test we will see how we can pass response from one request
     to another request. Also we will make use of dependsonMethods of TestNG

 */


import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PropertyTransfer extends testScripts.ListenerTest {

    String id;
    @Test
    public void CreateUser()
    {
        JSONObject object = new JSONObject();
        object.put("name","Tim Cooper");
        object.put("job","Teacher");
        id = given().contentType("application/json").body(object)
                .when().post("https://reqres.in/api/users")
                .then().statusCode(201).extract().path("id");

        System.out.println(id);
        test.get().info("User created with id:"+id);
    }

    @Test(dependsOnMethods = {"CreateUser"})
    public void UpdateUser()
    {
        JSONObject object = new JSONObject();
        object.put("name","Ronaldo");
        object.put("job","Football Player");

        given().contentType("application/json").body(object)
                .when().put("https://reqres.in/api/users/"+id)
                .then().statusCode(200).log().all();
        test.get().info("User updated with id:"+id);

    }

    @Test(dependsOnMethods = {"UpdateUser"})
    public void DeleteUser()
    {
        given().when().delete("https://reqres.in/api/users/"+id)
                .then().log().all();
        test.get().info("User updated with id:"+id);
    }

}
