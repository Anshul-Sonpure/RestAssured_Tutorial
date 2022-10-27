package testScripts;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.*;


public class Validations {

    /* In below test we will validate in our given
    endpoint whether the response contains given certain data or not
    1. using hamcrest hasItems
    2. using hamcrest equalTo
    3. validating JSON Path
    4. validating XML Path
    */


    // Will Validate whether the endpoint has Items as "Sincere@april.biz","Shanna@melissa.tv"..
    @Test
    public void ValidateMultipleContent() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .assertThat().
                body("email",hasItems("Sincere@april.biz","Shanna@melissa.tv","Nathan@yesenia.net","Julianne.OConner@kory.org","Lucio_Hettinger@annie.ca"));
    }

    // Will Validate whether the endpoint contains username given
    @Test
    public void ValidateMultipleData()
    {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then().assertThat().body("username",hasItems("Bret","Antonette","Samantha"));
    }

    //Validate single content using Hamcrest
    @Test
    public void ValidateSingleContent()
    {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .body("[3].email",equalTo("Julianne.OConner@kory.org"));
    }

    //Validate content via JSON Path
    @Test
    public void ValidateJsonPath()
    {
        String firstname=
                given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .path("data[3].first_name");
        Assert.assertEquals(firstname,"Byron");
    }

    //Another way to validate JSON path
    @Test
    public void ValidateJsonResponse()
    {
        Response response =
                given()
                .when()
                .get("https://reqres.in/api/users?page=2");
        JsonPath path = response.jsonPath();
        String email = path.get("data[3].email");
        Assert.assertEquals(email,"byron.fields@reqres.in");


    }

    @Test
    public void ValidateXMLPath()
    {
        given().when().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/1/").then()
                .body("CUSTOMER.FIRSTNAME",equalTo("Susanne"));

    }

    @Test
    void ValidateXmlPath()
    {
        String xml = given().when()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/1/").thenReturn().asString();
        String firstName = from(xml).get("CUSTOMER.FIRSTNAME");
        System.out.println(firstName);
        Assert.assertEquals(firstName,"Susanne");
        // or a bit more efficiently:
        XmlPath xmlPath = new XmlPath(xml);
        String LastName = xmlPath.get("CUSTOMER.LASTNAME");
        System.out.println(LastName);
        Assert.assertEquals(LastName,"King");

    }
}
