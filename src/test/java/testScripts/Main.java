package testScripts;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import static testScripts.E2EFlow.Token;

public class Main {

    @Test
    public void readToken() throws IOException {
        Path path = Paths.get("src/main/resources/token.txt");
        Token = Files.readAllLines(path).get(0);
        System.out.println(Token);
    }



}