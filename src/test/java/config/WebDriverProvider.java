package config;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        Map<String, String> props = new HashMap<>();
        String runType = System.getProperty("run.type");
        if (runType != null) {
            props.put("run.type", runType);
        }
        this.config = ConfigFactory.create(WebDriverConfig.class, props);
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createDriver();
        driver.get(config.getBaseUrl());
        return driver;
    }

    public WebDriver createDriver() {
        if ("remote".equalsIgnoreCase(config.getRunType())) {
            return createRemoteDriver();
        }
        return createLocalDriver();
    }

    private WebDriver createLocalDriver() {
        switch (config.getBrowser()) {
            case CHROME: {
                return new ChromeDriver();
            }
            case FIREFOX: {
                return new FirefoxDriver();
            }
            default: {
                throw new RuntimeException("No such local browser");
            }
        }
    }

    private WebDriver createRemoteDriver() {
        try {
            URL remoteUrl = new URL(config.getRemoteUrl());
            MutableCapabilities options;

            Map<String, Object> selenoidOptions = Map.of(
                    "enableVNC", true,
                    "enableVideo", false
            );

            switch (config.getBrowser()) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setCapability("browserVersion", config.getBrowserVersion());
                    chromeOptions.setCapability("selenoid:options", selenoidOptions);
                    options = chromeOptions;
                    break;

                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("browserVersion", config.getBrowserVersion());
                    firefoxOptions.setCapability("selenoid:options", selenoidOptions);
                    options = firefoxOptions;
                    break;

                default:
                    throw new RuntimeException("No such remote browser: " + config.getBrowser());
            }

            return new RemoteWebDriver(remoteUrl, options);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RemoteWebDriver", e);
        }
    }
}