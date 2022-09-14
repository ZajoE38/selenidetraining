package tests;

import base.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SortingHatTest extends TestBase {


    @Test
    public void itShouldDisplayNameOfHouse() {
        open("/sortinghat.php");
        $(By.cssSelector("button")).click();
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.loading")));
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("img.loading")));
        Assert.assertFalse($(By.cssSelector("p.result")).getText().isEmpty());

    }

    @Test
    public void itShouldDisplayNameOfHouseAlt() {
        open("/sortinghat.php");
        $("button").click();
        $("img.loading").should(appear);
        $("img.loading").should(disappear);
        // same as above, but with method chaining
        $("p.result").shouldBe(visible).shouldNotBe(empty);
    }

    @Test
    public void itShouldDisplayGriffindor() {
        open("/sortinghat.php");
        String house = "";
        while (!house.equals("Gryffindor")) {
            $("button").shouldBe(enabled).click();
            $("img.loading").should(appear).should(disappear);
            house = $("p.result")
                    .shouldBe(visible)
                    .shouldNotBe(empty)
                    .getText();
        }

    }
}
