package tests;

import base.TestBase;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FellowshipTest extends TestBase {

    // OPEN
    @Before
    public void openPage() {
        // open(BASE_URL + "/fellowship.php");
        open("/fellowship.php"); // uses baseurl defined in static
        open(""); // opens base url
    }

    // COLLECTION
    @Test
    public void itShouldContainNameForEachFellow() {

        // List<WebElement> fellowElements = getFellowElements();
        ElementsCollection fellowElements = getFellowElements();

        for (SelenideElement fellowElement : fellowElements) {
            // Assert.assertFalse(fellowElement.findElement(By.cssSelector("h1")).getText().isEmpty());
            fellowElement.find("h1").shouldNotBe(empty);
        }
    }

    // COLLECTION
    @Test
    public void itShouldContainSpecifiedFellows() {

        // List<WebElement> fellowElements = getFellowElements();
        ElementsCollection fellowElements = getFellowElements();
        List<String> fellowNames = new ArrayList<String>();

        for (SelenideElement fellowElement : fellowElements) {
            // fellowNames.add(fellowElement.findElement(By.cssSelector("h1")).getText());
            fellowNames.add(fellowElement.find("h1").getText());
        }

        System.out.println(fellowNames);
        Assert.assertTrue(fellowNames.contains("Gandalf"));
        Assert.assertTrue(fellowNames.contains("Aragorn"));
        Assert.assertTrue(fellowNames.contains("Frodo"));
    }

    @Test
    public void itShouldDisplayMessageComplete() {

        List<String> fellowsToSelect = new ArrayList<String>();
        fellowsToSelect.add("Gandalf");
        fellowsToSelect.add("Aragorn");
        fellowsToSelect.add("Legolas");
        fellowsToSelect.add("Frodo");

        for (String fellowToSelect : fellowsToSelect) {
            selectFellow(fellowToSelect);
        }

        // Assert.assertEquals("Complete", $("div.points-left h3").getText());
        $("div.points-left h3").shouldHave(text("Complete"));
    }

    // STREAM
    @Test
    public void itShouldDisplayPointsForEachFellow() {

        // List<WebElement> displayedFellows = getFellowElements();
        ElementsCollection displayedFellows = getFellowElements();

        displayedFellows.forEach(selenideElement -> selenideElement
                .find("div.fellow-points h2")
                .shouldNotBe(empty)
        );

        // same as stream above
//        for (WebElement displayedFellow : displayedFellows) {
//            String actualPoints = displayedFellow.findElement(By.cssSelector("div.fellow-points h2")).getText();
//            Assert.assertFalse(actualPoints.isEmpty());
//        }
    }

    // STREAM
    @Test
    public void itShouldHighlightFellows() {
        List<String> fellowsToSelect = new ArrayList<String>();
        fellowsToSelect.add("Gandalf");
        fellowsToSelect.add("Aragorn");
        fellowsToSelect.add("Legolas");
        fellowsToSelect.add("Frodo");

        for (String fellowToSelect : fellowsToSelect) {
            selectFellow(fellowToSelect);
        }

        List<String> highlightedFellows = driver.findElements
                        (By.xpath("//ul[contains(@class,'list-of-fellows')]/li/div[contains(@class,'active')]//h1"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());

        $("ul.list-of-fellows")
                .findAll("li > div")
                .filterBy(cssClass("active"))
                .shouldHave(textsInAnyOrder(fellowsToSelect));

        for (String highlightedFellow : highlightedFellows) {
            Assert.assertTrue(fellowsToSelect.contains(highlightedFellow));
        }
    }

    // XPATH
    private void selectFellow(String fellowName) {
        // Implicitly selenide thinks it's css selector
        $("//h1[contains(text(),'" + fellowName + "')]").click();

        // Need to specify explicitly
        $(By.xpath("//h1[contains(text(),'" + fellowName + "')]")).click();

        // shorthand for xpath
        $x("//h1[contains(text(),'" + fellowName + "')]").click();

        // byText() simplifies some xpath
        $(byText(fellowName)).click();
    }

//    private List<WebElement> getFellowElements() {
//        return driver.findElements(By.cssSelector("ul.list-of-fellows li"));
//    }

    // COLLECTION
    private ElementsCollection getFellowElements() {
        return $$("ul.list-of-fellows li");
    }
}
