package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import base.TestBase;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RandomTableTest extends TestBase {

    @Before
    public void openPage() {
        open(BASE_URL + "/tabulka.php");
    }

    @Test
    public void itShouldDisplaySecondRow() {
        // Selenium
        System.out.println(driver.findElement(By.xpath("//table/body/tr[2]/td[4]"))
                .getText());

        // Selenide
        System.out.println($("table > tbody > tr",1).find("td", 3)
                .getText());
    }

    @Test
    public void itShouldContainDataForEachRow() {
        for (WebElement tableRow : getRows()) {
            Assert.assertFalse(tableRow.getText().isEmpty());
        }
    }

    @Test
    public void itShouldContainNameForEachRow() {
        List<WebElement> tableRows = getRows();
        for (WebElement tableRow : tableRows) {
            tableRow.findElement(By.cssSelector("td:nth-child(2)"));
            WebElement rowName = tableRow.findElement(By.xpath("./td[2]"));
            Assert.assertFalse(rowName.getText().isEmpty());
        }
    }

    @Test // SCROLL
    public void itShouldScrollToLastElement() {
        // WebElement lastRow = driver.findElement(By.cssSelector("table > tbody > tr:last-child"));
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastRow);
        $(By.cssSelector("table > tbody > tr:last-child")).scrollIntoView(false);
    }

    private List<WebElement> getRows() {
        return driver.findElements(By.cssSelector("table tbody tr"));
    }
}
