package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

	@Test(groups = { "smoke", "regression" }, description = "TC:01, Valid login should succeed")
	public void testValidLogin() {

		LoginPage loginPage = new LoginPage(driver);

		loginPage.goToLoginPage();

		loginPage.login("dhineshSelenium@gmail.com", "DhineshSelenium");

		Assert.assertTrue(loginPage.isLoggedIn(),"User should be logged in");
	}

	@Test(groups = "regression", description = "TC:02, Invalid login should show error")
	public void testInvalidLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.goToLoginPage();
		loginPage.login("dhineshSelenium@gmail.com", "WDhineshSelenium");
		Assert.assertTrue(loginPage.isLoginErrorDisplayed(), "Error message should be shown");
	}

}
