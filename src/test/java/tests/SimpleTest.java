package tests;

import config.WebDriverProvider;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    void testGooglePage() {
        WebDriverProvider provider = new WebDriverProvider();
        WebDriver driver = provider.get();
        String browser = driver instanceof RemoteWebDriver
                ? ((RemoteWebDriver) driver).getCapabilities().getBrowserName()
                : driver.getClass().getSimpleName();
        System.out.println("Browser: " + browser);
        System.out.println("Заголовок страницы: " + driver.getTitle());
        assertEquals("Google", driver.getTitle());
        driver.quit();
    }
}
