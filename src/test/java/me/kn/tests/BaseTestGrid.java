package me.kn.tests;

import me.kn.page.ParkingPage;
import me.kn.page.UserPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URI;

public class BaseTestGrid {

    protected WebDriver driver;
    protected UserPage userPage;
    protected ParkingPage parkingPage;

    @BeforeEach
    public void setup() throws Exception {

        URI hub = URI.create("http://100.72.218.8:4444/wd/hub");

//        EdgeOptions opts = new EdgeOptions();
//        FirefoxOptions opts = new FirefoxOptions();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--start-maximized");

        driver = new RemoteWebDriver(hub.toURL(), opts);

        // Load file HTML
        String pageUrl = getPageUrl();
        driver.get(pageUrl);

        // Create pages
        userPage = new UserPage(driver);
        parkingPage = new ParkingPage(driver);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) driver.quit();
    }

    private String getPageUrl() {
        return "https://mrtroll28.github.io/Selenium_Ide/selenium.html";
    }
}
