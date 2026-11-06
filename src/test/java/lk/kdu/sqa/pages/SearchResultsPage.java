package lk.kdu.sqa.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // A results page often contains profiles or companies, but this locator must be verified in DevTools.
    private final By resultLinks = By.cssSelector("main a[href*='/in/'], main a[href*='/company/']");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public boolean isResultsPage() {
        try {
            wait.until(ExpectedConditions.urlContains("/search/"));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resultLinks)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException ex) { return false; }
    }
    public String clickFirstResultAndGetExpectedPath() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(resultLinks));
        String href = link.getAttribute("href");
        if (href == null || href.isBlank()) throw new IllegalStateException("Search result has no URL");
        String path = java.net.URI.create(href).getPath();
        link.click();
        wait.until(ExpectedConditions.urlContains(path));
        return path;
    }
}
