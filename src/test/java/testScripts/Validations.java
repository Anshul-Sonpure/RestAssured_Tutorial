package testScripts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.MatchesPattern.matchesPattern;



public class Validations extends testScripts.ListenerTest {

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
            test.get().info("Validated response body has Items"+"---"+"Sincere@april.biz,Shanna@melissa.tv,Nathan@yesenia.net,Julianne.OConner@kory.org,Lucio_Hettinger@annie.ca");

    }

    // Will Validate whether the endpoint contains username given
    @Test
    public void ValidateMultipleData()
    {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then().assertThat().body("username",hasItems("Bret","Antonette","Samantha"));

        test.get().info("Validated response body has username"+"---"+"Bret\",\"Antonette\",\"Samantha");


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
        test.get().info("Validated response body has email of 3rd user as "+"---"+"Julianne.OConner@kory.org");
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
        test.get().info("Validated response body has first_name of 3rd user "+"---"+firstname);
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
        test.get().info("Validated response body has email of 3rd user "+"---"+email);

    }

    @Test
    public void ValidateXMLPath()
    {
        given().when().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/1/").then()
                .body("CUSTOMER.FIRSTNAME",equalTo("Susanne"));
        test.get().info("Validated CUSTOMER.FIRSTNAME is Susanne");
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
        test.get().info("Validated CUSTOMER.LASTNAME is King");
    }

    @Test
    void ValidateViaPattern()
    {

        String pattern = "([0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z)";
        Pattern r = Pattern.compile(pattern);
            JSONObject request = new JSONObject();
            request.put("name", "morpheus");
            request.put("job", "leader");
            Response response = given()
                    .body(request)
                    .contentType(ContentType.JSON)
                    .post("https://reqres.in/api/users");
            JsonPath path = response.jsonPath();
            String createdAt = path.get("createdAt");
            response.then().assertThat()
                    .body("createdAt", matchesPattern(pattern));
            System.out.println(createdAt);
        test.get().info("Validate createdAt Via Pattern");
        }

       /*
           using .getlist() method we can fetch all related data at once. for e.g. we fetched rating here
           from rating.rate.
           Secondly we are filtering price based on rating where rating is >2
        */
        @Test
        public void JsonwithFilters()
        {
            Response products = RestAssured.given().get("https://fakestoreapi.com/products");
            JsonPath path = products.jsonPath();
            List<String> rating = path.getList("rating.rate");
            System.out.println(rating);
            List<String> prodt = new ArrayList<>((Arrays.asList("22.3", "55.99", "695", "109", "114", "9.85", "7.95")));
            List<String> actualprd = path.getList("findAll{it.rating.rate>4}.price").stream()
                    .map(val -> String.valueOf(val)).collect(Collectors.toList());
            if(prodt.containsAll(actualprd))
            {
                test.get().info("Validated Json With Filters"+" "+actualprd);
            }

        }
}
