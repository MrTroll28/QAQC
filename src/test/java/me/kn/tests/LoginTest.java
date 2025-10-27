package me.kn.tests;

import me.kn.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest{
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private static final String BASE_URL = "http://127.0.0.1:5500/login.html";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test @Order(1)
    void testEmptyFields() {
        loginPage.clickLogin();
        wait.until(d -> !loginPage.getMessage().isEmpty());
        Assertions.assertEquals("Không được để trống tên đăng nhập hoặc mật khẩu!", loginPage.getMessage());
    }

    @Test @Order(2)
    void testShortPassword() {
        loginPage.login("student", "123");
        Assertions.assertEquals("Mật khẩu phải có ít nhất 6 ký tự!", loginPage.getMessage());
    }

    @Test @Order(3)
    void testWrongCredentials() {
        loginPage.login("abc", "654321");
        Assertions.assertEquals("Sai thông tin đăng nhập!", loginPage.getMessage());
    }

    @Test @Order(4)
    void testSuccessfulLogin() {
        loginPage.login("student", "123456");
        Assertions.assertEquals("Xin chào, student!", loginPage.getWelcomeText());
    }
}
