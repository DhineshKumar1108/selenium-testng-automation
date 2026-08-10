package Listeners;

import Utils.ExtentManager;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import Utils.ScreenshotUtils;
import base.BaseTest;

public class TestListeners implements ITestListener {

	private static ExtentReports extent;

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		extent = ExtentManager.getInstance();
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		BaseTest baseTest = (BaseTest) result.getInstance();

		test.get().fail(result.getThrowable());

		WebDriver driver = baseTest.getDriver();

		if (driver != null) {

			String screenshotPath = ScreenshotUtils.capture(driver,result.getMethod().getMethodName());

			if (screenshotPath != null) {

				try {

					test.get().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

				} catch (Exception e) {

					test.get().fail("Could not attach screenshot: " + e.getMessage());

				}

			}

		} else {

			System.out.println("Screenshot skipped - driver was null when failure occurred");

		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().skip("Test Skipped" + result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
