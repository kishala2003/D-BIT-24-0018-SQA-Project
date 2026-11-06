package lk.kdu.sqa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    
    private final By username =
    	    By.cssSelector("input[type='email']");
    
    private final By password =
    	    By.cssSelector("input[type='password']");

    private final By signIn = By.xpath(
    	    "//button[.//span[normalize-space()='Sign in']]"
    	);

    private final By loginError = By.cssSelector(
        "#error-for-password, #error-for-username, .alert, [role='alert']"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get("https://www.linkedin.com/login");

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
    }

    public void login(String email, String pass) {

        WebElement emailField = wait.until(driver ->
            driver.findElements(username).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null)
        );

        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(driver ->
            driver.findElements(password).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null)
        );

        passwordField.clear();
        passwordField.sendKeys(pass);

        WebElement loginButton = wait.until(d ->
        d.findElements(signIn).stream()
            .filter(WebElement::isDisplayed)
            .filter(WebElement::isEnabled)
            .findFirst()
            .orElse(null)
    );

    loginButton.click();
    }


public boolean hasError() {
    try {
        return wait.until(d -> {

            return d.findElements(
                By.cssSelector(
                    "[role='alert'], " +
                    "[aria-live='assertive'], " +
                    "#error-for-password, " +
                    "#error-for-username, " +
                    ".alert, " +
                    "[class*='error']"
                )
            ).stream()
             .anyMatch(e ->
                 e.isDisplayed()
                 && !e.getText().trim().isEmpty()
             );
        });

    } catch (org.openqa.selenium.TimeoutException e) {
        return false;
    }
}

}