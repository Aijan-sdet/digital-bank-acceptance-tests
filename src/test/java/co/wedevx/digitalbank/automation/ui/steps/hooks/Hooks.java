package co.wedevx.digitalbank.automation.ui.steps.hooks;

import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.*;

import java.lang.annotation.Repeatable;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;

public class Hooks {

    @Before("@Registration")
    public static void establishConnectionToDB(){
        DBUtils.establishConnection();
    }
    @Before("not @Registration")
    public void the_user_on_dbank_homepage() {
        getDriver().get("http://aijanjak1940.mydevx.com/bank/login");
    }

    @After("not @NegativeRegistrationCases")
    public void afterEachScenario(Scenario scenario){
        Driver.takeScreenShot(scenario);
        Driver.closeDriver();
    }
    @After()
    public static void closeConnectionToDB(){
        DBUtils.closeConnection();
    }

}
