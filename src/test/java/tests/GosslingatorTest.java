package tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GosslingatorTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/win/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);  // otherwise, selenide would open its own driver
        open("http://localhost:80/gosslingator.php");
    }

    @Test
    public void itShouldDisplayTitle() {
        // SELENIUM
        Assert.assertEquals("GOSLINGATE ME", driver.findElement(By.cssSelector(".ryan-title")).getText());

        // SELENIDE
        $(".ryan-title").shouldHave(exactText("GOSLINGATE ME"));
    }

    @Test
    public void selenideFindDemo() {
        // SELENIUM
        driver.findElement(By.cssSelector("img"));  // will crash with elementNotFoundException

        // SELENIDE
        $(By.cssSelector("img"));
        // or simply
        $("img");
    }

    @Test
    public void itShouldAddOneRyan() {
        addRyan();

        $("div.ryan-counter h2").shouldHave(text("1"));
        $("div.ryan-counter h3").shouldHave(text("ryan"));
    }

    @Test
    public void itShouldTwoRyans() {
        addRyan(2);

//        String actualNumberOfRyans = $(By.id("ryanCounter")).getText();
//        String actualRyanDescription = $(By.cssSelector("div.ryan-counter h3")).getText();
//
//        Assert.assertEquals("2", actualNumberOfRyans);
//        Assert.assertEquals("ryans", actualRyanDescription);

        SelenideElement counter = $("ryanCounter");  // this will be in Page Object
        counter.shouldHave(exactText("2"));

        $("div.ryan-counter h3").shouldHave(exactText("ryans"));
    }

    // SELENIUM
    @Test
    public void itShouldDisplayWarningMessageSelenium() {
        addRyan(50);
        Assert.assertEquals(
                "NUMBER OF\n" +
                        "RYANS\n" +
                        "IS TOO DAMN\n" +
                        "HIGH",
                driver.findElement(By.cssSelector("h1.tooManyRyans")).getText()
        );
    }

    // SELENIDE
    @Test
    public void itShouldDisplayWarningMessageSelenide() {
        addRyan(50);
        $("h1.tooManyRyans")
                .shouldHave(exactText(
                        "NUMBER OF\n" +
                        "RYANS\n" +
                        "IS TOO DAMN\n" +
                        "HIGH"
                ));
    }

    @Test
    public void itShouldDisplayNoRyanOnPageOpen() {
        Assert.assertEquals(0, driver.findElements(By.cssSelector("img")).size());
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void addRyan() {
        $(By.id("addRyan")).click();
    }

    private void addRyan(int numberOfRyans) {
        SelenideElement addRyanButton = $(By.id("addRyan"));
        for (int i = 0; i < numberOfRyans; i++) {
            addRyanButton.click();
        }
    }
}
