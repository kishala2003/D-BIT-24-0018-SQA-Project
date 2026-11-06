package lk.kdu.sqa.base;

import java.nio.file.Files;

import java.nio.file.Path;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (driver != null) {

            if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("Test failed. Browser will remain open for inspection.");
            } else {
            	try {
            	    Thread.sleep(10000);
            	} catch (InterruptedException e) {
            	    Thread.currentThread().interrupt();
            	}
                driver.quit();
            }
        }
    }

    protected void requireCredentials() {
        if (System.getenv("LINKEDIN_EMAIL") == null || System.getenv("LINKEDIN_PASSWORD") == null
                || System.getenv("LINKEDIN_EMAIL").isBlank() || System.getenv("LINKEDIN_PASSWORD").isBlank()) {
            throw new IllegalStateException("Set LINKEDIN_EMAIL and LINKEDIN_PASSWORD as environment variables. Never put credentials in code.");
        }
    }

    protected void saveScreenshot(String name) {
        try {
            Files.createDirectories(Path.of("screenshots"));
            Files.write(Path.of("screenshots", name + ".png"),
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (Exception e) {
            throw new RuntimeException("Screenshot could not be saved", e);
        }
    }
}
