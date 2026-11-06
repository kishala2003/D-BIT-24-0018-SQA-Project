
package lk.kdu.sqa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchField =
    	    By.cssSelector("input[data-testid='typeahead-input']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(20)
        );
    }

    public boolean isLoggedIn() {
        try {
            return wait.until(d ->
                d.getCurrentUrl().contains("/feed")
                || d.findElements(searchField).stream()
                    .anyMatch(WebElement::isDisplayed)
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


public void search(String query) {

    wait.until(d -> {
        try {
            WebElement input = d.findElements(searchField)
                .stream()
                .filter(WebElement::isDisplayed)
                .filter(WebElement::isEnabled)
                .findFirst()
                .orElse(null);

            if (input == null) {
                return false;
            }

            input.click();
            input.clear();
            input.sendKeys(query);
            input.sendKeys(Keys.ENTER);

            System.out.println("Search submitted: " + query);
            return true;

        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            // LinkedIn replaced the search input.
            // WebDriverWait will find the new element and retry.
            return false;
        }
    });
}

}
