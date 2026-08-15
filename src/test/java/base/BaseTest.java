package base;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	protected WebDriver driver;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		String browser = ConfigReader.get("browser").toLowerCase();

	    Boolean headless = ConfigReader.getBoolean("headless");
	    if(browser.equals("headless-chrome")) {
	    	browser="chrome";
	    	headless=true;
	    }
	    switch(browser) {
	    case "firefox":
	    	WebDriverManager.firefoxdriver().setup();
	    	driver = new FirefoxDriver();
	    	break;
	    	
	    case "chrome":
	    	default:
	    		WebDriverManager.chromedriver().setup();
	    		ChromeOptions options=new ChromeOptions();
	    		if(headless) {
	    			options.addArguments("--headless=new");
	    		}
	    		driver= new ChromeDriver(options);
	    		break;
	    }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait") ) );
        driver.manage().timeouts().pageLoadTimeout( Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));
        driver.get(ConfigReader.get("baseUrl"));

	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
		}
	}
	
	public WebDriver getDriver() {

		    return driver;
	}
	

}
