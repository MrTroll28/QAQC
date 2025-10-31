package me.kn.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import me.kn.page.ParkingPage;
import me.kn.page.UserPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Base test class that initialises the WebDriver once for all tests and
 * provides helper methods to navigate to the tested HTML page and set up
 * page objects. Tests that extend this class can simply use the provided
 * driver, userPage and parkingPage fields.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected WebDriver driver;
    protected UserPage userPage;
    protected ParkingPage parkingPage;
    private String pageUrl;

    @BeforeAll
    public void setUpSuite() {
        // Use WebDriverManager to download and configure the appropriate
        // chromedriver binary. Then start Chrome in headless mode for
        // deterministic test execution in CI environments.
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        pageUrl = ("https://mrtroll28.github.io/Selenium_Ide/selenium.html");
    }

    @AfterAll
    public void tearDownSuite() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    public void setUpTest() {
        // Navigate fresh for each test to reset state. ChromeDriver will
        // automatically wait for the page to finish loading.
        driver.get(pageUrl);
        userPage = new UserPage(driver);
        parkingPage = new ParkingPage(driver);
    }
}