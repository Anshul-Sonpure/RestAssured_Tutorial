package testScripts;

import org.RestAssured_Tutorials.Products;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/*In this code demo will we use @JsonInclude from jackson, @JsonInclude annotation can be
used when we don't want to pass certain data in json payload.
We have a products POJO class where we have defined @jsoninclude for variables
@JsonInclude annotation can be used at class level and data level.
*/
public class JsonIncludeDemo {

    @Test
    public void ProductAdd()
    {
        Products prod = new Products();
        prod.setTitle("test product");
        prod.setPrice(145.5);
        prod.setCategory("Food");
        String token=
                given()
                        .contentType("application/json")
                        .body(prod)
                        .when()
                        .post("https://fakestoreapi.com/products")
                        .then()
                        .statusCode(200)
                        .log().all().toString();
    }


}
