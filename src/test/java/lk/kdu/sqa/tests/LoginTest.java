package lk.kdu.sqa.tests;

import lk.kdu.sqa.base.BaseTest;
import lk.kdu.sqa.pages.HomePage;
import lk.kdu.sqa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void validLogin() {
        requireCredentials();
        LoginPage page = new LoginPage(driver);
        page.open();
        page.login(System.getenv("LINKEDIN_EMAIL"), System.getenv("LINKEDIN_PASSWORD"));
        Assert.assertTrue(new HomePage(driver).isLoggedIn(),
                "Expected LinkedIn feed/search; security checkpoint or changed UI may prevent login.");
        saveScreenshot("valid-login");
    }

    @Test
    public void invalidPasswordShowsError() {
        requireCredentials();
        LoginPage page = new LoginPage(driver);
        page.open();
        page.login(System.getenv("LINKEDIN_EMAIL"), "intentionally-incorrect-password-1234");
        Assert.assertTrue(page.hasError(), "Expected a visible login error; inspect the current error selector.");
        saveScreenshot("invalid-login");
    }
}
