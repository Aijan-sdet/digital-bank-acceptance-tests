package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationSteps {
    //bring the driver, Driver is the utility class and getDriver is the method of utility class to call the Driver here
    RegistrationPage registrationPage = new RegistrationPage(getDriver());
    List<Map<String, Object>> nextValList = new ArrayList<>();

    @Given("User navigates to Digital Bank signup page")
    public void user_navigates_to_digital_bank_signup_page() {
        //import driver and set the values from digitalbank.properties in properties class
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.regisrtationpageurl"));
        //validate that title has a name Digital Bank, otherwise - mismatch
        assertEquals("Digital Bank", getDriver().getTitle(), "Registration page title mismatch");
    }
    @When("User creates account with following fields")
    public void user_creates_account_with_following_fields(List<Map<String,String>> registrationTestDataListOfMap){
        //our PageObject Model
        //calling the method from Registration Page class, using created List Of Maps
        registrationPage.fillOutRegistrationForm(registrationTestDataListOfMap);
    }
    @Then("User should be displayed with the message {string}")
    public void user_should_be_displayed_with_the_message(String expectedSuccessMessage) {
        assertEquals(expectedSuccessMessage, registrationPage.getMessage(), "Success message mismatch");
    }
    @Then("the User should see the {string} required field error message {string}")
    public void user_should_see_the_required_field_error_message(String fieldName, String expectedErrorMessage) {
      String actualErrorMessage = registrationPage.getRequiredFieldErrorMessage(fieldName);
      assertEquals(expectedErrorMessage, actualErrorMessage,"The error message of required " + fieldName +" mismatch");
    }
    @Then("the following user info should be saved in the db")
    public void the_following_user_info_should_be_saved_in_the_db(List<Map<String, String>> expectedUserProfileInfoInDBList) {
       Map<String,String> expectedUserInfoMap = expectedUserProfileInfoInDBList.get(0);
       String queryUserTable = String.format("Select * from users where username='%s'", expectedUserInfoMap.get("email"));
       String queryUserProfile = String.format("Select * from user_profile where email_address='%s'", expectedUserInfoMap.get("email"));

     List<Map<String, Object>> actualUserInfoList =  DBUtils.runSQLSelectQuery(queryUserTable);
     List<Map<String, Object>> actualUserProfileInfoList =  DBUtils.runSQLSelectQuery(queryUserProfile);
     //assertions for this email only returning one record
    assertEquals(1, actualUserInfoList.size(), "Registration generated unexpected number of users");
    assertEquals(1, actualUserProfileInfoList.size(), "Registration generated unexpected number of user profiles");

    Map<String, Object> actualUserInfoMap = actualUserInfoList.get(0);
    Map<String, Object> actualUserProfileInfoMap = actualUserProfileInfoList.get(0);

    assertEquals(expectedUserInfoMap.get("title"), actualUserProfileInfoMap.get("title"), "Registration generated wrong title");
    assertEquals(expectedUserInfoMap.get("firstName"), actualUserProfileInfoMap.get("first_name"), "Registration generated wrong first name");
    assertEquals(expectedUserInfoMap.get("lastName"), actualUserProfileInfoMap.get("last_name"), "Registration generated wrong last name");
    assertEquals(expectedUserInfoMap.get("gender"), actualUserProfileInfoMap.get("gender"), "Registration generated wrong gender");
   // assertEquals(expectedUserInfoMap.get("dob"), actualUserProfileInfoMap.get("dob"), "Registration generated wrong date of birth");
    assertEquals(expectedUserInfoMap.get("ssn"), actualUserProfileInfoMap.get("ssn"), "Registration generated wrong ssn");
    assertEquals(expectedUserInfoMap.get("email"), actualUserProfileInfoMap.get("email_address"), "Registration generated wrong email address");
    assertEquals(expectedUserInfoMap.get("address"), actualUserProfileInfoMap.get("address"), "Registration generated wrong address");
    assertEquals(expectedUserInfoMap.get("locality"), actualUserProfileInfoMap.get("locality"), "Registration generated wrong locality");
    assertEquals(expectedUserInfoMap.get("region"), actualUserProfileInfoMap.get("region"), "Registration generated wrong region");
    assertEquals(expectedUserInfoMap.get("postalCode"), actualUserProfileInfoMap.get("postal_code"), "Registration generated wrong postal code");
    assertEquals(expectedUserInfoMap.get("country"), actualUserProfileInfoMap.get("country"), "Registration generated wrong country");
    assertEquals(expectedUserInfoMap.get("homePhone"), actualUserProfileInfoMap.get("home_phone"), "Registration generated wrong home phone");
    assertEquals(expectedUserInfoMap.get("mobilePhone"), actualUserProfileInfoMap.get("mobile_phone"), "Registration generated wrong mobile phone");
    assertEquals(expectedUserInfoMap.get("workPhone"), actualUserProfileInfoMap.get("work_phone"), "Registration generated wrong work phone");

    //validate users table
        //intensive validation
        assertEquals(expectedUserInfoMap.get("accountNonExpired"), String.valueOf(actualUserInfoMap.get("account_non_expired")), "accountNonExpired mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("accountNonLocked"), String.valueOf(actualUserInfoMap.get("account_non_locked")), "accountNonLocked mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("credentialsNonExpired"), String.valueOf(actualUserInfoMap.get("credentials_non_expired")), "credentialsNonExpired mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("enabled"), String.valueOf(actualUserInfoMap.get("enabled")), "enabled mismatch upon registration");
        assertEquals(expectedUserInfoMap.get("email"), actualUserInfoMap.get("username"), "username mismatch upon registration");
        assertEquals(nextValList.get(0).get("next_val"), actualUserInfoMap.get("id"), "ID mismatch");

        long expectedUserProfileId = Integer.parseInt(String.valueOf(nextValList.get(0).get("next_val")));
        //increment by ++ id
        assertEquals(++expectedUserProfileId, actualUserProfileInfoMap.get("id"), "ID mismatch");

    }


    @Given("The user with {string} is not in DB")
    public void theUserWithIsNotInDB(String email) {
        //delete user from the user-profile
        String queryForUserProfile = String.format("DELETE from user_profile WHERE email_address='%s'", email);
        String queryForUsers = String.format("DELETE from users WHERE username='%s'", email);

        String queryToGetNextValInHibernateSequenceTable = String.format("select * from hibernate_sequence");
        nextValList = DBUtils.runSQLSelectQuery(queryToGetNextValInHibernateSequenceTable);

        DBUtils.runSQLUpdateQuery(queryForUserProfile);
        DBUtils.runSQLUpdateQuery(queryForUsers);
    }
}
