package selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabs2 {

    public static void main(String[] args) throws MalformedURLException {
        // webdriver manager = driver setup()
        // how to use SauceLabs
        //first get username and password

        String USERNAME = "oauth-aijan.jakypbek-aa8bd";
        String ACCESS_KEY = "47b35e62-97cd-476d-97d0-5a99ad0df638";

        //SETUP URL TO THE HUB WHICH IS RUNNING ON SAUCELABS VMs

        String url = "https://oauth-aijan.jakypbek-aa8bd:47b35e62-97cd-476d-97d0-5a99ad0df638@ondemand.us-west-1.saucelabs.com:443/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", Platform.MOJAVE); // change to another platform
        capabilities.setCapability("browserName", BrowserType.SAFARI); // change to another type
        capabilities.setCapability("browserVersion", "latest");

        WebDriver driver = new RemoteWebDriver(new URL(url), capabilities);

        driver.get("https://www.amazon.com/");

        //driver findElement(by id)
        WebElement searchBox = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
        searchBox.sendKeys("iphone" + Keys.ENTER);

        WebElement appleBrand = driver.findElement(By.cssSelector("li[id='p_89/Apple']"));
        System.out.println(appleBrand.getText());
    }
}
