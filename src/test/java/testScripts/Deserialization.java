package testScripts;

import org.RestAssured_Tutorials.UserDetails;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Deserialization {

    @Test
    public void deSerialization()
    {
        UserDetails usrdt = given()
                .get("https://gorest.co.in/public/v2/users/18").as(UserDetails.class);
        System.out.println(usrdt.getName());
        System.out.println(usrdt.getEmail());
        System.out.println(usrdt.getGender());
        System.out.println(usrdt.getStatus());

    }


}
