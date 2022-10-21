package testScripts;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadFile {

    /*
        In below test we will upload file from resources folder to the server
        Upload will be POST call made to sever
        An HTTP multipart request is an HTTP request that HTTP clients construct
        to send files and data over to an HTTP Server.
        It is commonly used by browsers and HTTP clients to upload files to the server.
     */
    @Test
    public  void UploadData()
    {
        File file = new File("src/main/resources/response.json");
        Response response = given()
                .multiPart("file",file,"multipart/form-data")
                .post("https://the-internet.herokuapp.com/upload")
                .thenReturn();
        System.out.println(response.prettyPrint());

    }


}

