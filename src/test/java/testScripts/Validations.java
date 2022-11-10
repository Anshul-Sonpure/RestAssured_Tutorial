package testScripts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.from;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.MatchesPattern.matchesPattern;



public class Validations extends ExtentReporterNG {

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
            test.info("Validated response body has Items"+"---"+"Sincere@april.biz,Shanna@melissa.tv,Nathan@yesenia.net,Julianne.OConner@kory.org,Lucio_Hettinger@annie.ca");

    }

    // Will Validate whether the endpoint contains username given
    @Test
    public void ValidateMultipleData()
    {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then().assertThat().body("username",hasItems("Bret","Antonette","Samantha"));

        test.info("Validated response body has username"+"---"+"Bret\",\"Antonette\",\"Samantha");


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
        test.info("Validated response body has email of 3rd user as "+"---"+"Julianne.OConner@kory.org");
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
        test.info("Validated response body has first_name of 3rd user "+"---"+firstname);
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
       ITestResult result = null;
        test = extent.createTest(result.getMethod().getMethodName()).assignAuthor(System.getProperty("user.name"));
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

    @Test
    void ValidateViaPattern()
    {
        test = extent.createTest("ValidateViaPattern").assignAuthor(System.getProperty("user.name"));
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
        }

       /*
           using .getlist() method we can fetch all related data at once. for e.g. we fetched rating here
           from rating.rate.
           Secondly we are filtering price based on rating where rating is >2
        */
        @Test
        public void JsonwithFilters()
        {
            test = extent.createTest("JsonwithFilters").assignAuthor(System.getProperty("user.name"));
            Response products = RestAssured.given().get("https://fakestoreapi.com/products");
            JsonPath path = products.jsonPath();
            List<String> rating = path.getList("rating.rate");
            System.out.println(rating);
            List<String> prodt = path.getList("findAll{it.rating.rate>2}.price");
            System.out.println(prodt);
//            String rate_prod = path.getString("find{it.category == 'men's clothing' & it.title == 'Mens Cotton Jacket'}.price");
//            System.out.println(rate_prod);
            test.log(test.getStatus(),"test is "+test.getStatus()+"ed");
        }
}
