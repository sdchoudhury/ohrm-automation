package generic;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {

    private static ExtentReports extent;
    private static ExtentTest logger;

    // ---------------- START REPORT (ONCE PER SUITE) ----------------
    public static void startReport(String path, String reportName, String docTitle) {

        if (extent == null) {

            // Ensure report directory exists
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String timeStamp =
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            String reportFile =
                    path + reportName + "_" + timeStamp + ".html";

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportFile);

            spark.config().setReportName(reportName);
            spark.config().setDocumentTitle(docTitle);

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    // ---------------- START TEST (ONCE PER TEST METHOD) ----------------
    public static void startTest(String testName) {
        logger = extent.createTest(testName);
    }

    // ---------------- LOG ----------------
    public static void reportLog(Status status, String message) {

        if (logger == null) {
            throw new RuntimeException(
                    "ExtentTest is null. Did you forget to call Report.startTest()?"
            );
        }

        String time =
                new SimpleDateFormat("HH:mm:ss").format(new Date());

        logger.log(status, "[" + time + "] " + message);
    }

    // ---------------- SCREENSHOT ----------------
    public static void attachScreenshotInReport(String screenshotPath, String message) {

        if (logger == null) {
            throw new RuntimeException(
                    "ExtentTest is null. Did you forget to call Report.startTest()?"
            );
        }

        try {
            logger.log(Status.INFO, message,
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(screenshotPath)
                            .build());
        } catch (Exception e) {
            logger.log(Status.WARNING,
                    "Screenshot attach failed: " + e.getMessage());
        }
    }



    // ---------------- END REPORT (ONCE PER SUITE) ----------------
    public static void endReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
