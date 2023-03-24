package testScripts;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PrintJsonElement extends ListenerTest {
/*
 In below we will iterate over the products to find the id corresponding to
 product name="Green Side Placket Detail T-Shirt"
 */

    @Test
    public void idUsingMap()
    {
        Response res = given().get("https://automationexercise.com/api/productsList");
        JsonPath path = res.jsonPath();
        ArrayList<Map<String,Object>> prd= path.get("products");
        System.out.println(prd.size());
        String prdname="Green Side Placket Detail T-Shirt";
        System.out.println(prd.getClass().getSimpleName());
        String id = "";
        for(int i=0;i<prd.size();i++)
        {
            Map<String,Object> map = (prd.get(i));
             if(map.containsKey("name") &&  map.get("name").toString().equalsIgnoreCase(prdname) )
                id = map.get("id").toString();
        }
        System.out.println("Id is :"+id);
        test.get().info("Green Side Placket Detail T-Shirt id:"+id);
    }
    /*
    Another way for doing the same is using find in jsonpath
    here we have used jsonpath as products.find
     */
    @Test
    public void JsonwithFilters()
    {
        Response res= RestAssured.given().get("https://automationexercise.com/api/productsList");
        JsonPath jsonPath = res.jsonPath();
        System.out.println(jsonPath.getString("products.find{it.name =='Green Side Placket Detail T-Shirt'}.id"));
        test.get().info("Fetching product id using jsonpath filter");
    }

    @Test
    public void ExtractElementUsingFind()
    {
        String id = given().when().get("https://automationexercise.com/api/productsList").andReturn()
                .jsonPath().getString("products.find{it.name=='Green Side Placket Detail T-Shirt'}.id");
        System.out.println("Green Side Placket Detail T-Shirt, id is:"+id);
        List products = given().when().get("https://automationexercise.com/api/productsList").andReturn()
                .jsonPath().getList("products.findAll{it.name}.name");
        System.out.println("Products"+products);

        test.get().info("Fetching data from reponse"+id);
    }

    @Test
    public void ExtractElementUsingFind2()
    {
        List products = given().when().get("https://fakestoreapi.com/products").andReturn()
                .jsonPath().getList("findAll{it.price>100}.title");
        System.out.println("Products"+products);
    }

}
