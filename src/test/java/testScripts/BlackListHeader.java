package testScripts;

/*
    In below test we will blacklist some response headers from being logged in console.
 */

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BlackListHeader extends testScripts.ListenerTest {
    @Test
    public void BlacklistHeaders() {

        List headers = new ArrayList<String>();
        headers.add("Server");
        headers.add("Etag");

        Response response = given().config(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then().log().headers().extract().response();

            test.get().info(response.getBody().prettyPrint());
            test.get().info(response.getStatusLine());


    }
}
