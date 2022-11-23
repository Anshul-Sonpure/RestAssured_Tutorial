package testScripts;

import io.restassured.common.mapper.TypeRef;
import org.RestAssured_Tutorials.UserDetails;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class Deserialization extends testScripts.ListenerTest {


    /*
        In below test we are going to see how we can perform deSerialization,
        we have created a POJO class named UserDetails and we will store the response
        using the object of UserDetails class.
        lastly we will print the details.
     */
    @Test
    public void deSerialization()
    {

        UserDetails usrdt = given()
                .get("https://gorest.co.in/public/v2/users/3663").as(UserDetails.class);
        System.out.println(usrdt.getName());
        System.out.println(usrdt.getEmail());
        System.out.println(usrdt.getGender());
        System.out.println(usrdt.getStatus());


    }
    /*io.restassured.mapper.TypeRef class that allows you to de-serialize the response
    to a container with a generic type.
    This works were response is a json array*/
    @Test
    public void DeserializationwithGenerics()
    {


        List<Map<String, Object>> users = given().
                get("https://jsonplaceholder.typicode.com/comments?postId=1").as(new TypeRef<List<Map<String, Object>>>() {});
                assertThat(users.get(0).get("id"),equalTo(1));
                assertThat(users.get(0).get("email"),equalTo("Eliseo@gardner.biz"));
        assertThat(users.get(2).get("id"),equalTo(3));
        assertThat(users.get(2).get("email"),equalTo("Nikita@garfield.biz"));


    }

}
