package ae.almosafer.automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber/cucumber.json", "html:target/cucumber/cucumber-reports.html"},
        glue = "ae.almosafer.automation")
public class CucumberRunnerTest {

}
