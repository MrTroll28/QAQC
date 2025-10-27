package me.kn.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // --- Locators ---
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton   = By.cssSelector("button[type='submit']");
    private By messageField  = By.id("message");
    private By welcomeText   = By.id("welcome");

    // --- Constructor ---
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Actions ---
    public void setUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // High-level action
    public void login(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLogin();
    }

    // --- Get results ---
    public String getMessage() {
        return driver.findElement(messageField).getText();
    }

    public String getWelcomeText() {
        WebElement e = driver.findElement(welcomeText);
        return e.getText();
    }
}
