package me.kn.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all page objects. It defines common helpers to locate
 * elements by their test identifiers and to wait for elements to appear.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Returns the element identified by a data-testid attribute. Using
     * data-testid attributes in the HTML makes the selectors resilient to
     * layout or style changes and simplifies the test code.
     *
     * @param testId the value of the data-testid attribute
     * @return the located WebElement
     */
    protected WebElement findByTestId(String testId) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='" + testId + "']")));
    }

    /**
     * Returns the element identified by the given id attribute.
     *
     * @param id element id
     * @return the located WebElement
     */
    protected WebElement findById(String id) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    /**
     * Clicks the given element and waits for its clickability beforehand. This
     * helper reduces flakiness when buttons become enabled asynchronously.
     *
     * @param element the WebElement to click
     */
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}