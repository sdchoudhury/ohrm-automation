package testCases;

import appSpecific.Wait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import generic.Base;
import generic.ConfigFileReader;
import generic.Report;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class roughtest {


    final String expectedTitle = "OrangeHRM";
    LoginPage loginPage;
    HomePage homePage;

    final static String filePath = "config/config";
    final static String reportPath = System.getProperty("user.dir") + "/Reports/";
    final static String expectedHomePageURL = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    final static String expectedInvalidCredMsg = "Invalid credentials";
    final static String URL = ConfigFileReader.getPropertyValue(filePath,"webUrl");

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) {
        // Initialize thread-safe driver
        Base.initDriver(browser);   // must properly initialize driver
        Base.getUrl(URL);
        loginPage = new LoginPage(Base.getDriver());
        homePage = new HomePage(Base.getDriver());
    }

    @Test(testName = "Login using valid cred", groups = {"smoke"}, priority = 1)
    public void test_single_user() {
        Report.startReport(reportPath, "Test Login Functionality", "Login With Valid UserName and Password");

        String loginPageTitle = loginPage.getTitle(Base.getDriver());
        if (expectedTitle.equalsIgnoreCase(loginPageTitle)) {
            Assert.assertEquals(loginPageTitle, expectedTitle);
            loginPage.loginToApp("admin", "admin123");

            Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getdashBoardLabel(), 30);
            Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getWelcomeMsg(), 30);

            if (homePage.getWelcomeMsg().isDisplayed() && homePage.getdashBoardLabel().isDisplayed()) {
                Report.reportLog(Status.PASS, "User Logged in Successfully");
                homePage.logOutFromApp(Base.getDriver(), 30);
            }
        } else {
            Assert.assertEquals(loginPageTitle, expectedTitle);
            Report.reportLog(Status.FAIL, "User Unable to Login using Valid Credentials");
        }
    }

    @Test(testName = "Login Using Multiple Users", groups = {"regression"}, priority = 2)
    public void test_multiple_users() {
        Report.startReport(reportPath, "Test Login Functionality", "Login With Multiple Credentials");

        String loginPageTitle = loginPage.getTitle(Base.getDriver());
        if (expectedTitle.equalsIgnoreCase(loginPageTitle)) {
            Assert.assertEquals(loginPageTitle, expectedTitle);

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    i++;
                    if (i > 1) { // Skip header
                        String usrName = line.split(" ")[0];
                        String passWord = line.split(" ")[1];

                        loginPage.loginToApp(usrName, passWord);
                        String currURL = Base.getDriver().getCurrentUrl();

                        Assert.assertNotNull(currURL);
                        if (currURL.equalsIgnoreCase(expectedHomePageURL)) {
                            Report.reportLog(Status.PASS, "User Logged in Successfully");
                            Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getdashBoardLabel(), 30);
                            Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getWelcomeMsg(), 30);
                            homePage.logOutFromApp(Base.getDriver(), 30);
                        } else {
                            Wait.waitForElementToBeVisible(Base.getDriver(), loginPage.getInvalidCredentialMsg(), 30);
                            String actualInvalidCredMsg = loginPage.getInvalidCredentialMsg().getText();
                            Report.reportLog(Status.FAIL, "User Unable to Login using Valid Credentials");
                            Assert.assertEquals(actualInvalidCredMsg, expectedInvalidCredMsg);
                        }
                    }
                }
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            }
        } else {
            Assert.assertEquals(loginPageTitle, expectedTitle);
        }
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        try {
            if (ITestResult.FAILURE == result.getStatus()) {
                Report.reportLog(Status.FAIL, result.getThrowable().toString());
            }
        } finally {
            Reporter.log("Test Case Executed: " + result.getName(), true);
            Report.endReport();
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Base.quitDriver();
    }

//    public static void startReport(String reportPath, String reportName, String reportTitle) {
//
//        // Create Reports directory if not exists
//        File dir = new File(reportPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // Timestamp
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
//                .format(new Date());
//
//        // Correct file path creation
//        File reportFile = new File(dir, reportName + "_" + timeStamp + ".html");
//
//        htmlReporter = new ExtentSparkReporter(reportFile);
//        report = new ExtentReports();
//        report.attachReporter(htmlReporter);
//
//        logger = report.createTest(reportTitle);
//    }
}
