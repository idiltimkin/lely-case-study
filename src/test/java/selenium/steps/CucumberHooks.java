package selenium.steps;


import io.cucumber.java.After;
import selenium.utilities.DriverManager;

public class CucumberHooks {

	@After
    public void afterScenario(){
        DriverManager.closeDriver();
    }
}