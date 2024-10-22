package co.wedevx.digitalbank.automation.ui.pages;
import co.wedevx.digitalbank.automation.ui.models.NewCheckingAccountInfo;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.NoSuchElementException;
import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCheckingPage extends BaseMenuPage{

    private String getExpectedUrl="http://aijanjak1940.mydevx.com/bank/account/checking-add";



    public CreateCheckingPage(WebDriver driver){
        //driver initialized from the parents class - BasePage
        super(driver);
    }


    @FindBy(id="Standard Checking")
    private WebElement standardCheckingAccountTypeRadioButton;

    @FindBy(id="Interest Checking")
    private WebElement interestCheckingAccountTypeRadioButton;

    @FindBy(id="Individual")
    private WebElement individualOwnershipTypeRadioButton;

    @FindBy(id="Joint")
    private WebElement jointOwnershipTypeRadioButton;

    @FindBy(id="name")
    private WebElement accountNameTxt;

    @FindBy(id="openingBalance")
    private WebElement openingBalanceTxtBox;

    @FindBy(id="newCheckingSubmit")
    private WebElement submitButton;


    //create a method for opening new checking account
    public void createNewChecking (List<NewCheckingAccountInfo> checkingAccountInfoList){
        NewCheckingAccountInfo testDataForOneCheckingAccount = checkingAccountInfoList.get(0);

        //this web elements are visible here, cause in their parent class (BaseMenuPge) they are protected
        //and this class extends the BaseMenuPage class
        checkingMenu.click();
        newCheckingButton.click();

        assertEquals(ConfigReader.getPropertiesValue("digitalbank.createnewcheckingurl"), getDriver().getCurrentUrl(), "New checking button didn't take to the " + ConfigReader.getPropertiesValue("digitalbank.createnewcheckingurl"));

      if (testDataForOneCheckingAccount.getCheckingAccountType().equalsIgnoreCase("Standard Checking")){
          standardCheckingAccountTypeRadioButton.click();
      }
      else if (testDataForOneCheckingAccount.getCheckingAccountType().equalsIgnoreCase("Interest Checking")){
        interestCheckingAccountTypeRadioButton.click();
      }
      else {
          throw new NoSuchElementException("Invalid checking account type option provided. Only supports Standard Checking and Interest Checking");
      }

      if (testDataForOneCheckingAccount.getAccountOwnership().equalsIgnoreCase("Individual")) {
          individualOwnershipTypeRadioButton.click();
      }
          else if (testDataForOneCheckingAccount.getCheckingAccountType().equalsIgnoreCase("Joint")){
              jointOwnershipTypeRadioButton.click();
          }
          else {
              throw new NoSuchElementException("Invalid ownership type option provided. Only supports Individual Checking and Joint Checking");
      }

        accountNameTxt.sendKeys(testDataForOneCheckingAccount.getAccountName());

        //convert double into string, because sendKeys method takes only string
        openingBalanceTxtBox.sendKeys(String.valueOf(testDataForOneCheckingAccount.getInitialDepositAmount()));

        submitButton.click();
    }



}
