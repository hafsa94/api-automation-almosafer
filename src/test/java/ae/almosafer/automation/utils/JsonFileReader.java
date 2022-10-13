package ae.almosafer.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {

    public String generateStringFromJsonFile(String jsonFileName) {
        String jsonFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "schema" + File.separator + jsonFileName;
        try {
            return new String(Files.readAllBytes(Paths.get(jsonFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
