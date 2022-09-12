package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class SavingsCalculatorPage {

//    @FindBy(id = "emailInput")
//    private WebElement emailInput;

//    @FindBy(id = "yearsInput")
//    private WebElement yearsInput;

//    @FindBy(id = "oneTimeInvestmentInput")
//    private WebElement oneTimeInvestmentInput;

//    @FindBy(id = "fundSelect")
//    private WebElement fundSelect;

//    @FindBy(css = "button.btn")
//    private WebElement applyButton;

//    @FindBy(css = "div.result")
//    private WebElement resultElement;

//    @FindBy(css = "ul.saving-list div.saving-detail")
//    private WebElement mostRecentSavingsDetail;

    private final SelenideElement emailInput = $(byId("emailInput"));
    private final SelenideElement yearsInput = $(byId("yearsInput"));
    private final SelenideElement oneTimeInvestmentInput = $(byId("oneTimeInvestmentInput"));
    private final SelenideElement fundSelect = $(byId("fundSelect."));
    private final SelenideElement applyButton = $(byCssSelector("button.btn"));
    private final SelenideElement resultElement = $(byCssSelector("div.result"));
    private final SelenideElement mostRecentSavingsDetail = $(byId("ul.saving-list div.saving-detail"));


//    private WebDriver pageDriver;

    // Page Factory constructor
//    public SavingsCalculatorPage(WebDriver driver) {
//        this.pageDriver = driver;
//         PageFactory.initElements(driver, this);
//    }

    // Replaces Page Factory
    public SavingsCalculatorPage() {
        page(this);
    }

    public void enterEmail(String email) {
//        emailInput.clear();
//        emailInput.sendKeys(email);
//        emailInput.sendKeys(Keys.TAB);
        emailInput.val(email).pressTab();
    }

    public void enterYears(int years) {
//        yearsInput.clear();
//        yearsInput.sendKeys(String.valueOf(years));
        yearsInput.val(String.valueOf(years));
    }

    public void enterOneTimeInvestment(String amount) {
//        oneTimeInvestmentInput.clear();
//        oneTimeInvestmentInput.sendKeys(amount);
        oneTimeInvestmentInput.val(amount);
    }

    public void selectFund(String fundToSelect) {
        // Selenium way
        // new Select(fundSelect).selectByVisibleText(fundToSelect);

        // Selenide way
        fundSelect.selectOption(fundToSelect);
    }

    public void applyForSaving() {
        applyButton.click();
    }


    public SelenideElement getCalculatedTotalIncomeElement() {
        // return resultElement.findElement(By.xpath("./div[1]/p"));
        // return $(resultElement).find(By.xpath("./div[1]/p"));
        return resultElement.find("div", 0).find("p");
    }

    public SelenideElement getCalculatedInterestIncomeElement() {
        // return resultElement.findElement(By.cssSelector("div.result > div:nth-child(2) p"));
        return resultElement.find(By.xpath("./div[2]/p"));
    }

    public SelenideElement getCalculatedRiskElement() {
        // return resultElement.findElement(By.xpath("./div[3]/p"));
        return resultElement.find(By.xpath("./div[3]/p"));
    }

    public SelenideElement getRecentRequestDetail() {
//        return mostRecentSavingsDetail;
        return mostRecentSavingsDetail;
    }

    public SelenideElement getApplyButton() {
        return applyButton;
    }

    public SelenideElement getEmailInputParent() {
//        return pageDriver.findElement(By.xpath("//input[@id='emailInput']/.."));
        return emailInput.parent();
    }
}