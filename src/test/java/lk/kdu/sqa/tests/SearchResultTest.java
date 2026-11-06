package lk.kdu.sqa.tests;

import lk.kdu.sqa.base.BaseTest;
import lk.kdu.sqa.pages.HomePage;
import lk.kdu.sqa.pages.LoginPage;
import lk.kdu.sqa.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseTest {
    @Test
    public void openingFirstResultGoesToExpectedPage() {
        requireCredentials();
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login(System.getenv("LINKEDIN_EMAIL"), System.getenv("LINKEDIN_PASSWORD"));
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isLoggedIn(), "Login blocked or not completed");
        home.search("software testing");
        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.isResultsPage(), "No visible matching result");
        String expectedPath = results.clickFirstResultAndGetExpectedPath();
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedPath), "Opened URL did not match selected result");
        saveScreenshot("opened-result");
    }
}
