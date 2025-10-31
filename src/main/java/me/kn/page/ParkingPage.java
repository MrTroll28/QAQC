package me.kn.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Page object modelling the parking fee calculator form. Methods are provided
 * to set input fields, toggle options, perform calculations and query
 * results or validation messages. The selectors utilise data-testid values
 * defined in the HTML which ensures resilience to layout changes.
 */
public class ParkingPage extends BasePage {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public ParkingPage(WebDriver driver) {
        super(driver);
    }

    public void setPlate(String plate) {
        WebElement input = findByTestId("plate");
        input.clear();
        input.sendKeys(plate);
    }

    public void selectVehicleByValue(String value) {
        WebElement selectEl = findByTestId("vehicle");
        Select select = new Select(selectEl);
        select.selectByValue(value);
    }

    private void setDateTimeLocal(WebElement el, LocalDateTime dt) {
        String value = dt.format(DATE_TIME_FORMATTER);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                el, value);
    }

    public void setStart(LocalDateTime dt) {
        WebElement el = findByTestId("start");
        setDateTimeLocal(el, dt);
    }

    public void setEnd(LocalDateTime dt) {
        WebElement el = findByTestId("end");
        setDateTimeLocal(el, dt);
    }

    public void setOvernight(boolean checked) {
        WebElement el = findByTestId("overnight");

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);

        if (el.isSelected() != checked) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", el);
        }
    }

    public void setMember(boolean checked) {
        WebElement el = findByTestId("member");

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);

        if (el.isSelected() != checked) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", el);
        }
    }

    public void clickCalculate() {
        WebElement calcBtn = findByTestId("calc-btn");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", calcBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calcBtn);
    }

    public void clickClear() {
        WebElement clearBtn = findByTestId("clear-calc");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clearBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clearBtn);
    }


    /**
     * Returns the current parking form message. Empty when hidden.
     */
    public String getParkingMessage() {
        WebElement msg = driver.findElement(By.id("parkingMessage"));
        return msg.getText().trim();
    }

    /**
     * Returns the total fee displayed in the UI. The value includes the
     * currency suffix (e.g. "5.000đ").
     */
    public String getTotalFee() {
        WebElement fee = driver.findElement(By.id("feeAmount"));
        return fee.getText().trim();
    }

    /**
     * Returns the detailed breakdown string rendered after calculation.
     */
    public String getFeeBreakdown() {
        WebElement out = driver.findElement(By.id("feeResult"));
        return out.getText();
    }

    /**
     * Determines if a field is currently marked invalid via the CSS class.
     *
     * @param testId data-testid of the input element
     * @return true if the element has 'is-invalid' class
     */
    public boolean isInvalid(String testId) {
        WebElement el = findByTestId(testId);
        String classes = el.getAttribute("class");
        return classes != null && classes.contains("is-invalid");
    }

    public void waitForFeeToAppear() {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
                .until(d -> !getTotalFee().isEmpty());
    }
}