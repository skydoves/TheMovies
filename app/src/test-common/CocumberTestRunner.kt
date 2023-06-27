import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["your.package.name"],
    tags = ["@smoke"] // Optional: Run specific scenarios or tags
)
class CucumberTestRunner
