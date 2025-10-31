package me.kn.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object modelling the user form and user table. It provides methods to
 * interact with the form fields, trigger CRUD actions and query validation
 * messages. Using explicit waits ensures that tests remain robust in the
 * presence of asynchronous JavaScript operations.
 */
public class UserPage extends BasePage {

    public UserPage(WebDriver driver) {
        super(driver);
    }

    public void setName(String name) {
        WebElement input = findByTestId("name");
        input.clear();
        input.sendKeys(name);
    }

    public void setEmail(String email) {
        WebElement input = findByTestId("email");
        input.clear();
        input.sendKeys(email);
    }

    public void setPhone(String phone) {
        WebElement input = findByTestId("phone");
        input.clear();
        input.sendKeys(phone);
    }

    public void selectGender(String genderLabel) {
        WebElement selectEl = findByTestId("gender");
        Select genderSelect = new Select(selectEl);
        genderSelect.selectByVisibleText(genderLabel);
    }

    public void setTerms(boolean checked) {
        WebElement checkbox = findByTestId("terms");
        if (checkbox.isSelected() != checked) {
            checkbox.click();
        }
    }

    public void clickAdd() {
        click(findByTestId("add-btn"));
    }

    public void clickReset() {
        click(findByTestId("reset-btn"));
    }

    /**
     * Returns the current message displayed by the user form. An empty string
     * indicates that no message is shown.
     */
    public String getFormMessage() {
        WebElement msg = driver.findElement(By.id("formMessage"));
        return msg.getText().trim();
    }

    /**
     * Returns a list of texts for each table row currently rendered in the
     * users table. Each string contains pipe-separated values for the
     * columns (name, email, phone, gender) excluding the actions column.
     */
    public List<String> getTableRows() {
        WebElement tableBody = findByTestId("table").findElement(By.tagName("tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        return rows.stream().map(row -> {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            // Some rows may be placeholders (e.g. load failed). Guard accordingly.
            if (cells.size() < 4) {
                return row.getText();
            }
            String name = cells.get(0).getText().trim();
            String email = cells.get(1).getText().trim();
            String phone = cells.get(2).getText().trim();
            String gender = cells.get(3).getText().trim();
            return String.join(" | ", name, email, phone, gender);
        }).collect(Collectors.toList());
    }

    /**
     * Click nút Edit trong đúng hàng có Email = ...
     * @param email
     * @return
     */
    public boolean clickEditByEmail(String email) {
        String xpBtn = "//table[@data-testid='table']//tbody//tr[td[2][normalize-space()='" + email + "']]//button[@data-action='edit']";
        var els = driver.findElements(By.xpath(xpBtn));
        if (els.isEmpty()) return false;
        WebElement btn = els.get(0);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
        return true;
    }

    /**
     * Click nút Delete trong đúng hàng có Email = ...
     * @param email
     * @return
     */
    public boolean clickDeleteByEmail(String email) {
        String xpBtn = "//table[@data-testid='table']//tbody//tr[td[2][normalize-space()='" + email + "']]//button[@data-action='del']";
        var els = driver.findElements(By.xpath(xpBtn));
        if (els.isEmpty()) return false;
        WebElement btn = els.get(0);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
        return true;
    }

    /**
     * Whether a specific input field currently has the 'is-invalid' CSS class
     * applied. This helper allows tests to assert that validation rules are
     * visually flagged on incorrect input.
     *
     * @param testId data-testid of the input element
     * @return true if the class list contains 'is-invalid'
     */
    public boolean isInvalid(String testId) {
        WebElement el = findByTestId(testId);
        String classes = el.getAttribute("class");
        return classes != null && classes.contains("is-invalid");
    }

    /**
     * Returns the text of the table row containing the specified email.
     * @param email
     * @return
     */
    public String getRowTextByEmail(String email) {
        // Tìm đúng <tr> chứa ô td có text = email (không phụ thuộc vị trí cột)
        var rows = driver.findElements(By.xpath(
                "//table[@data-testid='table']//tbody//tr[.//td[normalize-space()='"+email+"'] or .//td[contains(normalize-space(), '"+email+"')]]"
        ));
        if (rows.isEmpty()) return null;
        return rows.get(0).getText();
    }

    public void waitForFormMessage(String text) {
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("formMessage"), text));
    }

    /** Chờ có 1 hàng mà ô Email (cột 2) bằng email chỉ định */
    public void waitUntilRowByEmail(String email) {
        String xp = "//table[@data-testid='table']//tbody//tr[td[2][normalize-space()='" + email + "']]";
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
    }
}