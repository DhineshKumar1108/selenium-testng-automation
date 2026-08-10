package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {
		if(extent==null) {
			String reportPath = "test-output/ExtentReport/index.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setDocumentTitle("Automation Test Report");
			spark.config().setReportName("Amazon style E-commerce automation suite");
			extent = new ExtentReports();
			extent.attachReporter(spark);
			
			extent.setSystemInfo("Environment", "QA - automationexercise.com");
			extent.setSystemInfo("Tester", "Practice Run");
		}
		return extent;
	}

}
