package me.kn.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.net.URI;

public class OnNodeVisible {
    public static void main(String[] args) throws Exception {
        var hub = URI.create("http://100.72.218.8:4444/wd/hub");

        // Chọn 1 trong 3:
//        ChromeOptions opts = new ChromeOptions();
         EdgeOptions opts = new EdgeOptions();
//         FirefoxOptions opts = new FirefoxOptions();

        // ĐẢM BẢO CÓ GIAO DIỆN:
        // (Không set headless) + mở to cửa sổ
        opts.addArguments("--start-maximized");
        // hoặc: opts.addArguments("--window-size=1366,768");

        WebDriver driver = new RemoteWebDriver(hub.toURL(), opts);
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        Thread.sleep(3000); // giữ cho bạn thấy trình duyệt 3s
        driver.quit();
    }
}
