package generic;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Report {

	
	public static ExtentReports report;
	public static ExtentTest logger;
	public static ExtentSparkReporter htmlReporter;
	
	/* 
	 * Start of the extent report
	 * 
	 * Parameters: 
	 * @reportPath - Path where report needs to be saved
	 * @reportName - Name of the report that gets saved in the above mentioned path
	 * @reportTitle - Title that gets displayed in the report
	 */

    public static void startReport(String reportPath, String reportName, String reportTitle) {

        report = new ExtentReports();
        File file = new File(reportPath + reportName + ".html");
        htmlReporter = new ExtentSparkReporter(file);
        logger=report.createTest(reportTitle);
        report.attachReporter(htmlReporter);
    }
	
	
	/* Logging in the report
	 * Parameters:
	 * @logstatus - INFO/PASS/FAIL/SKIP/ERROR   (Eg: LogStatus.INFO)
	 * @loginfo   - Message to be displayed in the report log
	 * eg: ExtentReport.reportLog(LogStatus.INFO, "Safe login Page");
	 */	
	public static void reportLog(Status logstatus, String loginfo){
	
		logger.log(logstatus, loginfo);
		
	}
	
	/* Attaching screenshot in the report
	 * Parameters:
	 * @screenshot_path - path of the screenshot that needs to be attached in the report
	 * This returns the complete path of the screenshot with its extension (Eg: C:\Users\Public\Pictures\Sample Pictures\image.jpg)
	 *  Eg: String screenshot=ExtentReport.attachScreenshotInReport(filename);
	 *	ExtentReport.reportLog(LogStatus.INFO, "Safe login Page"+screenshot);
	 */	
	public static String attachScreenshotInReport(String screenshot_path)
	{
		String screenshot = logger.addScreenCaptureFromPath(screenshot_path).toString();
		return screenshot;
	}
		
	/* End of the extent report
	 * Eg: ExtentReport.endReport();
	 */	
	public static void endReport(){
		report.flush();
	
	}

}
