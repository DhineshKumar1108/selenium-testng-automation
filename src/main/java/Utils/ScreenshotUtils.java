package Utils;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot; 
import org.openqa.selenium.WebDriver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
	public static String capture(WebDriver driver, String testName) {
		
		if (driver == null) {

	        System.out.println("Screenshot skipped - driver was null when failure occurred");

	        return null;

	    }
		
		try {
			String timestamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			String fileName=testName+"_"+timestamp;
			String dirPath="test-output/screenshots";
			Files.createDirectories(Paths.get(dirPath));
			
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			String destPath = dirPath+"/"+fileName;
			Files.copy(src.toPath(),Paths.get(destPath));
			return destPath;
		}
		catch(IOException e) {
			System.err.println("Screenshot Captured Failed:"+e.getMessage());
			return null;
		}
	}

}
