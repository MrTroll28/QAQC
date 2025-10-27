package me.kn.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class GridViaNgrok {
    public static void main(String[] args) throws Exception {
        // Thay bằng URL ngrok của bạn
        URI grid = URI.create("https://centrically-drossiest-frederica.ngrok-free.dev/wd/hub");

        ChromeOptions options = new ChromeOptions();
        // Ví dụ cấu hình headless nếu cần:
        // options.addArguments("--headless=new");

        WebDriver driver = new RemoteWebDriver(grid.toURL(), options);
        driver.get("https://www.google.com");
        System.out.println("Title = " + driver.getTitle());
        driver.quit();
    }
}
