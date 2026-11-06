package lk.kdu.sqa.tests;

import lk.kdu.sqa.base.BaseTest;
import lk.kdu.sqa.pages.HomePage;
import lk.kdu.sqa.pages.LoginPage;
import lk.kdu.sqa.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @Test
    public void searchShowsResults() {
        requireCredentials();
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login(System.getenv("LINKEDIN_EMAIL"), System.getenv("LINKEDIN_PASSWORD"));
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isLoggedIn(), "Login blocked or not completed");
        home.search("software testing");
        Assert.assertTrue(new SearchResultsPage(driver).isResultsPage(), "Search URL or result locator not found");
        saveScreenshot("search-results");
    }
}
