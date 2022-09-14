package tests;

import base.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.SavingsCalculatorPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.*;
import static org.openqa.selenium.By.cssSelector;

public class SavingsCalculatorTest extends TestBase {

    private SavingsCalculatorPage savingsCalculatorPage;

    @Before
    public void openPage() {
        open("/savingscalculator.php");
        // savingsCalculatorPage = new SavingsCalculatorPage(driver);  // No Page Factory
        savingsCalculatorPage = new SavingsCalculatorPage();
    }

    @Test
    public void itShouldEnterOneTimeInvestment(){
        // SELENIUM
        driver.findElement(By.xpath("//input[@placeholder='One tome investment']"));

        // SELENIDE
        $(byAttribute("placeholder", "One time investment"));
    }

    @Test
    public void itShouldEnableApplyButton() {
        savingsCalculatorPage.selectFund("Hoggwart's Fund");
        savingsCalculatorPage.enterOneTimeInvestment("15000");
        savingsCalculatorPage.enterYears(20);
        savingsCalculatorPage.enterEmail("info@furbo.sk");

        // assertTrue(savingsCalculatorPage.getApplyButton().isEnabled());
        savingsCalculatorPage.getApplyButton().shouldBe(enabled);
    }

    @Test
    public void itShouldDisplayCalculatedAmounts() {
        savingsCalculatorPage.selectFund("Hoggwart's Fund");
        savingsCalculatorPage.enterOneTimeInvestment("15000");
        savingsCalculatorPage.enterYears(20);
        savingsCalculatorPage.enterEmail("info@furbo.sk");

        /* I would rather have validating method in PO,
        because you have to add either method regardless */
        // assertFalse(savingsCalculatorPage.getCalculatedTotalIncomeElement().getText().isEmpty());
        savingsCalculatorPage.getCalculatedTotalIncomeElement().shouldNotBe(empty);

        // assertFalse(savingsCalculatorPage.getCalculatedInterestIncomeElement().getText().isEmpty());
        savingsCalculatorPage.getCalculatedInterestIncomeElement().shouldNotBe(empty);
    }

    @Test
    public void itShouldDisplayCalculatedRisk() {
        savingsCalculatorPage.selectFund("Hoggwart's Fund");
        savingsCalculatorPage.enterOneTimeInvestment("15000");
        savingsCalculatorPage.enterYears(20);
        savingsCalculatorPage.enterEmail("info@furbo.sk");

        // assertFalse(savingsCalculatorPage.getCalculatedRiskElement().getText().isEmpty());
        // assertFalse(savingsCalculatorPage.getCalculatedRiskElement().getText().contains("kr"));
        savingsCalculatorPage.getCalculatedRiskElement()
                .shouldNotBe(empty)
                .shouldHave(text("kr"));
    }

    // BECAUSE
    @Test
    public void itShouldContainFundNameInNewRequest() {
        String fundToSelect = "Hoggwart's Fund";
        savingsCalculatorPage.selectFund(fundToSelect);
        savingsCalculatorPage.enterOneTimeInvestment("25000");
        savingsCalculatorPage.enterYears(20);
        savingsCalculatorPage.enterEmail("info@furbo.sk");
        savingsCalculatorPage.applyForSaving();

        // SELENIDE
        savingsCalculatorPage.getRecentRequestDetail()
                .find("p.fund-description")
                .shouldHave(exactText(fundToSelect)
                        .because("because bear on four is teddy"));
        // SELENIUM
        assertEquals(
                fundToSelect,
                savingsCalculatorPage.getRecentRequestDetail().findElement(cssSelector("p.fund-description")).getText(),
                "because bear on four is teddy"
        );
    }

    @Test
    public void itShouldDisplayErrorMessageWhenEmailIsInvalid() {
        savingsCalculatorPage.enterEmail("invalid");
        // assertTrue(savingsCalculatorPage.getEmailInputParent().getAttribute("class").contains("error"));
        savingsCalculatorPage.getEmailInputParent().shouldHave(cssClass("error"));
    }

    // HOVER
    @Test()
    public void itShouldHighlightNewRequestOnHover() throws InterruptedException {
        savingsCalculatorPage.selectFund("Hoggwart's Fund");
        savingsCalculatorPage.enterOneTimeInvestment("15000");
        savingsCalculatorPage.enterYears(20);
        savingsCalculatorPage.enterEmail("info@furbo.sk");
        savingsCalculatorPage.applyForSaving();

//        Actions action = new Actions(driver);
//        WebElement we = $(By.cssSelector("div.saving-detail"));
//        action.moveToElement(we).build().perform();
//        Thread.sleep(300);
//        assertEquals("rgba(4, 102, 156, 1)", we.getCssValue("background-color"));
        $("div.saving-detail")
                .hover()
                .shouldHave(cssValue("background-color", "rgba(4, 102, 156, 1)"));
    }

}



