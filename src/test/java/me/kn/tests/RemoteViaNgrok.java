package me.kn.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class RemoteViaNgrok {
    public static void main(String[] args) throws Exception {
        // Thay bằng URL Ngrok của bạn, nhớ có /wd/hub
        var grid = URI.create("https://centrically-drossiest-frederica.ngrok-free.dev/wd/hub");

        ChromeOptions options = new ChromeOptions();
        // Để nhìn thấy trình duyệt mở thật trên máy Grid, KHÔNG bật headless
        // options.addArguments("--headless=new"); // đừng dùng nếu muốn thấy

        WebDriver driver = new RemoteWebDriver(grid.toURL(), options);
        driver.get("https://www.google.com");
        System.out.println("Title = " + driver.getTitle());
        driver.quit();
    }
}
