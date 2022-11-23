package testScripts;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class PrintJsonElement {
/*
 In below we will iterate over the products to find the id corresponding to
 product name="Green Side Placket Detail T-Shirt"
 */

    @Test
    public void demo1()
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
        System.out.println(id);
    }


}
