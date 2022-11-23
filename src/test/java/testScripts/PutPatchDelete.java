package testScripts;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PutPatchDelete extends testScripts.ListenerTest{

    /*
        In below test we will make Patch,Put and delete request
        1. Patch is used for partial update, i.e. if we need to update a single
        field of an already present data.
        2. PUT is used to modify the existing data present.
        3. Delete is used to delete the existing data.

     */

    @Test
    public void PatchRequest()
    {
        JSONObject request = new JSONObject();
        request.put(" title","IronMan vs Captain America");
        Response response= (Response) given()
                .contentType("application/json")
                .body(request.toJSONString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/posts/1");
        int statuscode= response.getStatusCode();
        Assert.assertEquals(200,statuscode);
        test.get().info("Patch User with details:"+request);
    }

    @Test
    public void  PutRequest()
    {
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "Teacher");
        given().contentType("application/json").body(request)
                .when().put("https://reqres.in/api/users/2")
                .then().log().all();
        test.get().info("Updated User with details:"+request);
    }


    @Test
    public void DeleteRequest()
    {
        given()
                .when().delete("https://reqres.in/api/users/2")
                .then().statusCode(204);
        test.get().info("Deleted Users");
    }
}
