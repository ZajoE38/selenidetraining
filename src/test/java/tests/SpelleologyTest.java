package tests;

import base.TestBase;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utils.DataUtils.getExpectedSpells;

public class SpelleologyTest extends TestBase {

    @Before
    public void openPage() {
        open("/spelleology.php");
    }

    @Test
    public void itShouldContainSpellsSelenium() {

        String[] spellsToBePresent = {
                "counters sonorus",
                "erases memories",
                "counterspells",
                "controls a person – unforgivable"
        };

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.spells li")));

        List<String> displayedSpells = driver.findElements(By.cssSelector("ul.spells li"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        for (String spellToCheck : spellsToBePresent) {
            Assert.assertTrue(displayedSpells.contains(spellToCheck));
        }
    }

    @Test
    public void itShouldContainSpellsSelenide() throws FileNotFoundException {

        List<String> spellsToBePresent = getExpectedSpells();

        // The Good
        List<String> displayedSpells = $$("ul.spells li")
                .shouldHave(sizeGreaterThan(1))
                .stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());

        // The Better
        List<String> displayedSpellsAlt = $("ul.spells").$$("li")
                .shouldHave(sizeGreaterThan(1))
                .stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());

        // The Best
        List<String> displayedSpellsFind = $("ul.spells")
                .findAll("li")
                .shouldHave(sizeGreaterThan(1))
                .stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());

        for (String spellToCheck : spellsToBePresent) {
            Assert.assertTrue(displayedSpells.contains(spellToCheck));
            // Assert.assertTrue(displayedSpells.equals(spellToCheck));
        }

        // The Bestest
        // Doesn't work with arrays though (use AssertJ)
        $("ul.spells")
                .findAll("li")
                .shouldHave(sizeGreaterThan(1))
                .shouldHave(texts(spellsToBePresent));
                //.shouldHave(exactTexts(spellsToBePresent));

    }

    @Test
    public void itShouldDisplayTortureSpell() {

//        ElementsCollection spellElements = $$("ul.spells li").shouldHave(sizeGreaterThan(1));
        $("ul.spells li")
                .findAll("li")
                .shouldHave(sizeGreaterThan(1))
                .find(exactText("tortures a person"))
                .click();
        // same as above
//        for (WebElement spellElement : spellElements) {
//            if (spellElement.getText().equals("tortures a person")) {
//                spellElement.click();
//            }
//        }


        $("div.modal-container")
                .should(appear)
                .shouldHave(text("Crucio"));
        // same as above
//        new WebDriverWait(driver, 10)
//                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.modal-container")));
//        WebElement modal = $(By.cssSelector("div.modal-container"));
//        Assert.assertTrue(modal.getText().contains("Crucio"));
    }

    @Test
    public void itShouldExcludeSpells() {
        $("ul.spells")
                .findAll("li")
                .shouldHave(sizeGreaterThan(1))
//                .filterBy(matchText("^shoots.*"))
                .exclude(readonly)
                .exclude(hidden)
                .exclude(matchText("^opens.*"))
                .exclude(matchText("^shoots.*"))
                .exclude(matchText("^[a|b|c].*"))
                .forEach(selenideElement -> System.out.println(selenideElement.getText()));
    }

    @Test
    public void itShouldFilterSpells() {

        $("input").sendKeys("tortures a person");

        $$("ul.spells li").shouldHave(size(1));
//        new WebDriverWait(driver, 10).until(ExpectedConditions
//                .numberOfElementsToBe(By.cssSelector("ul.spells li"), 1));
//        Assert.assertEquals(driver.findElements(By.cssSelector("ul.spells li")).size(), 1);
    }

}
