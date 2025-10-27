package me.kn.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URI;

public class GridOverTailscale {
    public static void main(String[] args) throws Exception {
        URI hubUrl = URI.create("http://100.72.218.8/wd/hub");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(hubUrl.toURL(), options);
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.quit();
    }
}
