package co.wedevx.digitalbank.automation.ui.utils;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static WebDriver driver;

    private Driver(){

    }
    public static WebDriver getDriver(){
        if(driver==null){
           String browser = ConfigReader.getPropertiesValue("digitalbank.browser");
            System.out.println("Starting " + browser + " browser..."); // Вывод информации о запуске браузера

            switch (browser.toLowerCase()){
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "saucelabs":
                    //we have two way
                    //1-configReader and properties
                    //2-Edit Configuration(Run/Debug Configuration) Feature or folder path
//                    String platform = ConfigReader.getPropertiesValue("digitalbank.sauceLabs.platform");
//                    String browserType = ConfigReader.getPropertiesValue("digitalbank.sauceLabs.browser");
//                    String browserVersion = ConfigReader.getPropertiesValue("digitalbank.sauceLabs.version");
                    String platform = System.getProperty("digitalbank.sauceLabs.platform");
                    String browserType = System.getProperty("digitalbank.sauceLabs.browser");
                    String browserVersion = System.getProperty("digitalbank.sauceLabs.version");
                    loadSauceLabs(platform, browserType, browserVersion);
                    break;
                case "headless":
                    ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
                    ChromeOptions options = new ChromeOptions();
                    //this is optional below, you don't have to add it
                    options.addArguments("--window-size=1928,1080");
                    options.setExperimentalOption("useAutomationExtension", false);
                    options.addArguments("--proxy-server='direct://'");
                    options.addArguments("--proxy-bypass-list=*");
                    options.addArguments("--start-maximized");
                    options.addArguments("--headless");

                    driver = new ChromeDriver(options);
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;


            }
            System.out.println("Browser started");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public static void takeScreenShot(Scenario scenario){
        if (scenario.isFailed()) {
            //taking a screenshot
            final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            //adding the screenshot to the report
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    //when we are done with aal test suites, we make sure that we are closing Driver
public static void closeDriver(){
        if (driver != null){
            driver.quit();
            driver=null;
        }
}
private static WebDriver loadSauceLabs(String platform, String browserType, String browserVersion)  {
   // to make sauce labs dynamic and run in diff browsers we need to create parameters as String
    // we put the username value into digitalbank properties, so we can get it using ConfigReader method
    String USERNAME = ConfigReader.getPropertiesValue("digitalbank.sauceLabs.username");
    String ACCESS_KEY = "47b35e62-97cd-476d-97d0-5a99ad0df638";
    String HOST = ConfigReader.getPropertiesValue("digitalbank.sauceLabs.host");

    //SETUP URL TO THE HUB WHICH IS RUNNING ON SAUCELABS VMs
    String url = "https://"+ USERNAME + ":" + ACCESS_KEY + "@" + HOST;
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", platform); // change to another platform
    capabilities.setCapability("browserName", browserType); // change to another type
    capabilities.setCapability("browserVersion", browserVersion);

    WebDriver driver = null;
    try {
        driver = new RemoteWebDriver(new URL(url), capabilities);
    } catch (MalformedURLException e) {
        throw new RuntimeException(e);
    }
    return driver;
}

}
