package testScripts;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;

public class DownloadFile extends testScripts.ListenerTest {
    /*
        In below test we are performing how to download
        upload a file using HTTP methods.
        Upload is basically creating an object on the server hence POST method is used for uploading.
        Download is basically requesting an object from the server hence GET method is used for downloading.

        Below test is for downloading text,image and zip file from the endpoint.
        Please note provide the exact file name that needs to be downloaded from the server
        otherwise no data will be there in the file saved
        all files are getting saved in src/main/resources/
     */

    @Test
    public void DownloadtextFile() throws IOException
    {

        Response response = given()
                .log().all().when()
                .get("https://the-internet.herokuapp.com/download/test.txt").andReturn();
        byte[] bytes = response.getBody().asByteArray();

        FileOutputStream fout = new FileOutputStream("src/main/resources/test12.txt",
                true);
        fout.write(bytes);
        fout.close();

    }

    @Test
    void DownloadZipFile() throws IOException
    {
        byte[] dowloadedFile = RestAssured.given().when()
                .get("https://chercher.tech/files/minion.zip")
                .then().extract().asByteArray();
        File file = new File("src/main/resources/minion.zip");
        Files.write(file.toPath(), dowloadedFile);

    }


    @Test
    void DownloadImgFile() throws IOException {

        byte[] dowloadedFile = RestAssured.given().when()
                .get("https://the-internet.herokuapp.com/download/sampleFile.jpeg")
                .then().extract().asByteArray();
        File file = new File("src/main/resources/sampleFile.jpeg");
        Files.write(file.toPath(), dowloadedFile);

    }

    /*
        In below test we are making a request to an endpoint and
        saving the json response to a file locally.
     */
    @Test
    public void SaveJsonResponsetoFile() throws IOException {

        Response response = given().get("https://reqres.in/api/users/2").andReturn();
        byte[] bytes = response.asByteArray();
        File file = new File("src/main/resources/response.json");
        Files.write(file.toPath(),bytes);


    }

    @Test
    public void UploadFile()
    {

        File file = new File("src/main/resources/sampleFile.jpeg");
        Response resp= RestAssured.given().multiPart(file).when().post("https://the-internet.herokuapp.com/upload")
                .then().extract().response();
        System.out.println("File uploaded with status code::"+resp.getStatusLine());
        System.out.println(resp.getStatusLine());
        test.get().log(test.get().getStatus(),resp.getStatusLine());


    }
}