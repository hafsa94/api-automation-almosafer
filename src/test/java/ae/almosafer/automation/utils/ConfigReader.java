package ae.almosafer.automation.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ae.almosafer.automation.utils.Constant.PARAMETERS.BASE_URI;

@Slf4j
public class ConfigReader {
    private static final Properties props = new Properties();

    public ConfigReader() {
        try {
            getProps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProps() throws IOException {
        FileInputStream is = null;
        String propsFileName = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "application.properties";

        if (props.isEmpty()) {
            try {
                log.info("loading config properties");
                is = new FileInputStream(propsFileName);
                props.load(is);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Failed to load config properties. ABORT!!" + e);
                throw e;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return props;
    }

    public String getBaseURI() {
        return props.getProperty(BASE_URI);
    }

}
