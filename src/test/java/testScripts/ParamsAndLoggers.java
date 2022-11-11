package testScripts;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ParamsAndLoggers extends testScripts.ListenerTest{

    /*
    In the below test cases we will see what all data can be logged
    and how to set parameters and headers
    1. to set parameter and header
    2. to set query parameter,form parameter and path parameter
    3. to log all response
    4. to log only body
    5. to log only headers
    6. to log cookies details
     */

    @Test
    public void setParamHeaders()
    {
        given()
                .param("param1","dummyparam1")
                .header("header1","dummyheader1")
                .when().get("https://reqres.in/api/unknown/2")
                .then().statusCode(200).log().all();

    }

    @Test
    public void setParams()
    {
        given().queryParam("a","Aval").formParam("z","12")
                        .pathParam("user","2")
                .when().get("https://reqres.in/api/unknown/{user}").then().statusCode(200);
    }


    @Test
    public void logAll()
    {
        given().queryParam("a","Aval").formParam("z","12")
                .pathParam("user","2")
                .when().get("https://reqres.in/api/unknown/{user}").then().statusCode(200)
                .log().all();
    }

    @Test
    public void logBody()
    {
        given().queryParam("a","Aval").formParam("z","12")
                .pathParam("user","2")
                .when().get("https://reqres.in/api/unknown/{user}").then().statusCode(200)
                .log().body();
    }

    @Test
    public void logHeader()
    {
        given().queryParam("a","Aval").formParam("z","12")
                .pathParam("user","2")
                .when().get("https://reqres.in/api/unknown/{user}").then().statusCode(200)
                .log().headers();
    }

    @Test
    public void logCookie()
    {
        given()
                .when().get("https://www.google.com/gmail/").then().statusCode(200)
                .log().cookies();
    }

    /* To log the request if validation fails */
    @Test
    public void LogifValidationFails()
    {
        given().log().ifValidationFails().queryParam("a","Aval").formParam("z","12")
                .when().get("https://www.google.com/gmail/").then().statusCode(201);
    }

    /* To log the response if validation fails */
    @Test
    public void logifValidationFails()
    {
        given().get("https://reqres.in/api/users/2").then().log().ifValidationFails().statusCode(201);
    }
}
