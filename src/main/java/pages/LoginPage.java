package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(css="a[href='/login']")
	private WebElement signupLoginLink;
	
	@FindBy(css="input[data-qa='login-email']")
	private WebElement loginEmailField;
	
	@FindBy(css="input[data-qa='login-password']")
	private WebElement loginPasswordField;
	
	@FindBy(css="button[data-qa='login-button']")
	private WebElement loginButton;
	
	@FindBy(css=".login-form p")
	private WebElement loginErrorMessage;
	
	@FindBy(xpath="//a[contains(text(),'Logged in as')]")
	private WebElement isLoggedIn;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    

	    PageFactory.initElements(driver, this);

	}
	
	public void goToLoginPage() {
		wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink)).click();
	}
	
	public void login(String email, String password) {
		wait.until(ExpectedConditions.visibilityOf(loginEmailField)).sendKeys(email);
		loginPasswordField.sendKeys(password);
		loginButton.click();
	}
	
	public boolean isLoginErrorDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(loginErrorMessage)).isDisplayed();
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean isLoggedIn() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(isLoggedIn)).isDisplayed();
		}
		catch(Exception e) {
			return false;
		}
	}
	
	

}
