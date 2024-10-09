package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.AccountCard;
import co.wedevx.digitalbank.automation.ui.models.BankTransaction;
import co.wedevx.digitalbank.automation.ui.models.NewCheckingAccountInfo;
import co.wedevx.digitalbank.automation.ui.pages.CreateCheckingPage;
import co.wedevx.digitalbank.automation.ui.pages.LoginPage;
import co.wedevx.digitalbank.automation.ui.pages.ViewCheckingAccountPage;
import co.wedevx.digitalbank.automation.ui.utils.DateTimeFormat;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountSteps {

   WebDriver driver = Driver.getDriver();
    private LoginPage loginPage = new LoginPage(driver);
    private DateTimeFormat dateTimeFormat = new DateTimeFormat();
    private CreateCheckingPage createCheckingPage =  new CreateCheckingPage(driver);
    private ViewCheckingAccountPage viewCheckingAccountPage = new ViewCheckingAccountPage(driver);



    @Given("the user logged in as {string} {string}")
    public void the_user_logged_in_as(String username, String password) {
        loginPage.login(username, password);

    }

    @When("the user creates new checking account with following data")
    public void the_user_creates_new_checking_account_with_following_data(List<NewCheckingAccountInfo> checkingAccountInfoList) {

        createCheckingPage.createNewChecking(checkingAccountInfoList);
//        NewCheckingAccountInfo testDataForOneCheckingAccount =  checkingAccountInfoList.get(0);
//
//        String formattedDateTime = dateTimeFormat.getFormattedDateTime();
//        System.out.println("The current date of created account is: " + formattedDateTime);
//
//        //user clicks on checking button
//        WebElement checkingMenu = driver.findElement(By.id("checking-menu"));
//        checkingMenu.click();
//
//        //the user clicks on the new checking button
//        WebElement newCheckingMenu = driver.findElement(By.id("new-checking-menu-item"));
//        newCheckingMenu.click();
//        //test assertions to get expected url (provided) and method to get actual url with get,
//        // and comment, if the test case is failed, and we need to see comment
//        String expectedUrl = "https://dbank-qa.wedevx.co/bank/account/checking-add";
//        assertEquals(expectedUrl, driver.getCurrentUrl(), "New checking button didn't take to the " + expectedUrl);
//
//        // the user selects the account type
//        WebElement accountTypeRadioButton =  driver.findElement(By.id(testDataForOneCheckingAccount.getCheckingAccountType()));
//        accountTypeRadioButton.click();
//
//        // the user selects account ownership
//        WebElement ownershipTypeRadioButton =  driver.findElement(By.id(testDataForOneCheckingAccount.getAccountOwnership()));
//        ownershipTypeRadioButton.click();
//
//        // the user names the account
//        WebElement accountNameTxtBox =  driver.findElement(By.id("name"));
//        accountNameTxtBox.sendKeys(testDataForOneCheckingAccount.getAccountName());
//
//        // the user makes the initial deposit
//        WebElement openingBalanceTxtBox =  driver.findElement(By.id("openingBalance"));
//        //convert double into string, because sendKeys method takes only string
//        openingBalanceTxtBox.sendKeys(String.valueOf(testDataForOneCheckingAccount.getInitialDepositAmount()));
//
//        //the user clicks submit
//        WebElement submitButton =  driver.findElement(By.id("newCheckingSubmit"));
//        submitButton.click();

    }

    @Then("the user should see the green {string} message")
    public void the_user_should_see_the_green_message(String expectedConfMessage) {
        expectedConfMessage = "Confirmation " + expectedConfMessage + "\n×";
        //now we compare expected result which is string and x character from the website
        // with actual result which is located in webElement
        //take actual from POM
        assertEquals(expectedConfMessage, viewCheckingAccountPage.getActualCreateAccountConfirmationMessage());

    }
    @Then("the user should see newly added account card")
    public void the_user_should_see_newly_added_account_card(List<AccountCard> accountCardList) {
        //firstRow is actually a data table or a bunch of elements
       //all child divs are in a list of elements
//        //looking for all direct div from firstRow id
//        List<WebElement> allFirstRowDivs = driver.findElements(By.xpath("//div[@id='firstRow']/div"));
//        System.out.println("First Row Size " + allFirstRowDivs.size());
//        // for (WebElement card : allFirstRowDivs){
//        //   System.out.println(card.getText());
//        // }
//
//        //LAst account number
//        WebElement lastAccountCard = allFirstRowDivs.get(allFirstRowDivs.size() - 1);
//        String actualResult = lastAccountCard.getText();
//      // System.out.println("This is last account number:" + lastAccountCard.getText());
//
//        //Madlen Pierre's Second Checking
//        String actualAccountName = actualResult.substring(0, actualResult.indexOf("\n")).trim();
//        System.out.println(actualAccountName);
//
//        //Account: Standard Checking
//        String actualAccountType = actualResult.substring(actualResult.indexOf("Account"), actualResult.indexOf("Ownership")).trim();
//        System.out.println(actualAccountType.trim());
//
//        //Ownership
//        String actualOwnership = actualResult.substring(actualResult.indexOf("Ownership"), actualResult.indexOf("Account Number:")).trim();
//        System.out.println(actualOwnership.trim());
//
//        //Actual account number
//        String actualAccountNumber = actualResult.substring(actualResult.indexOf("Account Number:" ), actualResult.indexOf("Interest Rate:")).trim();
//        System.out.println(actualAccountNumber.trim());
//        assertTrue(actualAccountNumber.startsWith("Account Number: "), "Account number is not correct");
//
//        //Printing out interest rate
//        String actualInterestRate = actualResult.substring(actualResult.indexOf("Interest Rate:"), actualResult.indexOf("Balance:")).trim();
//        System.out.println(actualInterestRate.trim());
//
//        //Balance
//        String actualBalance = actualResult.substring(actualResult.indexOf("Balance:")).trim();
//        System.out.println(actualBalance.trim());
//
//        //get the expected result from accountCardList
//        AccountCard expectedResult = accountCardList.get(0);
//       String accountNumber = String.valueOf((int) expectedResult.getAccountNumber()+allFirstRowDivs.size());
//        // assertEquals("Account Number: " + accountNumber, actualAccountNumber);

       Map<String, String> actualResultMap = viewCheckingAccountPage.getNewlyAddedCheckingAccountInfoMap();
       AccountCard expectedResult = accountCardList.get(0);

        assertEquals(expectedResult.getAccountName(), actualResultMap.get("actualAccountName"));
        assertEquals("Account: "+ expectedResult.getAccountType(), actualResultMap.get("actualAccountType"));
        assertEquals("Ownership: " + expectedResult.getOwnership(), actualResultMap.get("actualOwnership"));
        // assertEquals("Account Number: " + expectedResult.getAccountNumber(), actualAccountNumber);
        assertEquals("Interest Rate: " + expectedResult.getInterestRate(), actualResultMap.get("actualInterestRate"));
        //to print out the balance with actual 00 as a double
        String expectedBalance = String.format("%.2f", expectedResult.getBalance());
        assertEquals("Balance: $" + expectedBalance, actualResultMap.get("actualBalance"));

    }
    @Then("the user should see the following transaction")
    public void the_user_should_see_the_following_transaction(List<BankTransaction> expectedTransactions) {
//        WebElement firstRowOfTransactions = driver.findElement(By.xpath("//table[@id='transactionTable']/tbody/tr"));
//
//        //div[@id='transactionTable']/tbody/tr/td  can look like this: saved all columns in a list
//        List<WebElement> firstRowColumns = firstRowOfTransactions.findElements(By.xpath("td"));
//        String actualCategory = firstRowColumns.get(1).getText();
//        String actualDescription = firstRowColumns.get(2).getText();
//        double actualAmount = Double.parseDouble(firstRowColumns.get(3).getText().substring(1));
//        // finding the 3rd row, then get rid of $ sign with substring starting from index 1,
//        // which is second character in a line. Then convert it to double with parse.Cause before we converted it into String from double.
//        // now we converted it again into double from string.
//        double actualBalance = Double.parseDouble(firstRowColumns.get(4).getText().substring(1));
//
//        //   assertEquals(expectedTransactions.get(0).getCategory(), actualCategory);
//        //    assertEquals(expectedTransactions.get(0).getDescription(), actualDescription);
//        //  assertEquals(expectedTransactions.get(0).getAmount(), actualAmount);
//        //   assertEquals(expectedTransactions.get(0).getBalance(), actualBalance);
//        // if you don't wanna over type it, just use this

       Map<String, String> actualResultMap = viewCheckingAccountPage.getNewlyAddedCheckingAccountTransactionInfoMap();
       BankTransaction expectedTransaction = expectedTransactions.get(0);
       // assertEquals(dateTimeFormat.getFormattedDateTime(), firstRowColumns.get(0).getText());

        assertEquals(expectedTransaction.getCategory(), actualResultMap.get("actualCategory"), "Transaction category mismatch");
        assertEquals(expectedTransaction.getAmount(), Double.parseDouble(actualResultMap.get("actualAmount")), "Transaction amount mismatch");
        assertEquals(expectedTransaction.getBalance(), Double.parseDouble(actualResultMap.get("actualBalance")), "Transaction balance mismatch");

    }


}


