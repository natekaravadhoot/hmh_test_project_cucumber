package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "html:target/cucumber-reports",
        features = {"src/test/java/featureFiles"},
        glue = "stepDefinitions"
)
public class Runner{
}