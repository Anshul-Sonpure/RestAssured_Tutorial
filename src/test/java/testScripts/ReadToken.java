package testScripts;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static testScripts.E2EFlow.Token;

public class ReadToken {

    @Test
    public void readToken() throws IOException {
        Path path = Paths.get("src/main/resources/response.json");
        Token = Files.readAllLines(path).toString();
        System.out.println(Token);
    }

}