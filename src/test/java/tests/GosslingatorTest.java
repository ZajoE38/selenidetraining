package tests;

import base.TestBase;
import com.codeborne.selenide.*;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.junit5.TextReportExtension;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GosslingatorTest extends TestBase {

    @Rule
    public TextReportExtension textReport = new TextReportExtension()
            .onSucceededTest(true)
            .onFailedTest(true);

    @Rule
    public SoftAssertsExtension softAsserts = new SoftAssertsExtension();

    @Before
    public void setUp() {
        open("/gosslingator.php");
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

    // SOFT ASSERT
    @Test
    public void itShouldAddOneRyan() {
        Configuration.assertionMode = AssertionMode.SOFT;
        addRyan();
        screenshot("nazovFilu");
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

        $("ryanCounter").shouldHave(exactText("2"));
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
        // Assert.assertEquals(0, driver.findElements(By.cssSelector("img")).size());
        $("img").shouldHave(exactText("0"));
    }

    // JAVASCRIPT EXECUTOR
    @Test
    public void itShouldRemoveRyanHeadByClickingOnIt() {
        Configuration.clickViaJs = true;
        addRyan(30);

        // $$("img").forEach(SelenideElement::click);  // deprecated
        ElementsCollection ryans = $$("img");
        for (SelenideElement ryan : ryans) {
            ryan.click();
        }
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
