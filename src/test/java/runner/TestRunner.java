package runner;
import Ellithium.core.base.BDDSetup;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(
        glue = {"steps","hooks"},
        features="src/test/resources/features"
)
public class TestRunner extends BDDSetup {
}