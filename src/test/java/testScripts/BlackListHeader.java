package testScripts;

/*
    In below test we will blacklist some of the response headers from being logged in console.
 */

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BlackListHeader {
    @Test
    public  void BlacklistHeaders()
    {
        List headers = new ArrayList<String>();
        headers.add("Server");
        headers.add("User-Agent");
        headers.add("Etag");

            given().config(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
                        .log().headers().get("https://jsonplaceholder.typicode.com/posts/1")
            .then().log().headers();
    }



}
