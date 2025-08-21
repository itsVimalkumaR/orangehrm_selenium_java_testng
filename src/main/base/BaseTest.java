package main.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import main.utils.ConfigReader;
import main.utils.ScreenshotUtil;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigReader config;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        config = new ConfigReader();
        
        // Initialize driver based on browser parameter
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new WebDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        
        // Navigate to base URL
        driver.get(config.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}