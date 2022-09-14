package base;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestBase {

    protected WebDriver driver;
    static {
        Configuration.baseUrl = "http://localhost:80";
        Configuration.timeout = 4000;  // default
        Configuration.pollingInterval = 200;  // default
        Configuration.browser = Browsers.CHROME;
        Configuration.reportsFolder = "src/test/resources/reports";
//        Configuration.clickViaJs = true;
//        Configuration.reportsUrl = "adresa pre jenkins";
//        Configuration.holdBrowserOpen = true;
//        Configuration.remote = "http://localhost:4444";
//        Configuration.headless = true;
//        Configuration.browser = "1920x1080";
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);  // otherwise selenide would open its own driver
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
