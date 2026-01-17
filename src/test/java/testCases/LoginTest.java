package testCases;

import appSpecific.Wait;
import com.aventstack.extentreports.Status;
import generic.Base;
import generic.ConfigFileReader;
import generic.Report;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import pages.HomePage;
import pages.LoginPage;

import java.io.*;
import java.lang.reflect.Method;

public class LoginTest {

    // ---------- CONSTANTS ----------
    private static final String EXPECTED_TITLE = "OrangeHRM";
    private static final String FILE_PATH = "config/config";
    private static final String REPORT_PATH =
            System.getProperty("user.dir") + "/reports/";
    private static final String EXPECTED_HOME_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    private static final String EXPECTED_INVALID_MSG = "Invalid credentials";
    private static final String URL =
            ConfigFileReader.getPropertyValue(FILE_PATH, "webUrl");

    // ---------- OBJECTS ----------
    LoginPage loginPage;
    HomePage homePage;

    // ---------- EXTENT REPORT ----------
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        Report.startReport(REPORT_PATH,
                "AutomationReport",
                "Login Test Suite");
    }

    // ---------- DRIVER SETUP ----------
    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser) {
        Base.initDriver(browser);
        Base.getUrl(URL);
        loginPage = new LoginPage(Base.getDriver());
        homePage = new HomePage(Base.getDriver());
    }

    // ---------- CREATE TEST NODE ----------
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        Report.startTest(method.getName());
    }

    // ---------- TEST 1 ----------
    @Test(testName = "Login using valid credentials", groups = {"smoke"}, priority = 1)
    public void test_single_user_login() {
        String actualTitle = loginPage.getTitle(Base.getDriver());
        Assert.assertEquals(actualTitle, EXPECTED_TITLE, "Login page title mismatch");
        Report.reportLog(Status.INFO, "Entering valid username and password");
        loginPage.loginToApp("admin", "admin123");
        Report.reportLog(Status.INFO, "User waiting for the dashboard to load completely");
        Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getdashBoardLabel(), 30);
        Report.reportLog(Status.INFO, "User waiting for the welcome message to load completely");
        Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getWelcomeMsg(), 30);
        Assert.assertTrue(homePage.getdashBoardLabel().isDisplayed() && homePage.getWelcomeMsg().isDisplayed(), "Dashboard or Welcome message not visible");
        Report.reportLog(Status.PASS, "User logged in successfully");
        Report.reportLog(Status.INFO, "User is now logging out of the application");
        homePage.logOutFromApp(Base.getDriver(), 30);
    }

    // ---------- TEST 2 ----------
    @Test(testName = "Login using multiple users",
            groups = {"regression"}, priority = 2)
    public void test_multiple_users_login() {

        String actualTitle = loginPage.getTitle(Base.getDriver());
        Assert.assertEquals(actualTitle, EXPECTED_TITLE,
                "Login page title mismatch");

        try (BufferedReader br =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                row++;

                if (row == 1) continue; // skip header

                String[] data = line.split(" ");
                String username = data[0];
                String password = data[1];

                Report.reportLog(Status.INFO,
                        "Attempting login with user: " + username);

                loginPage.loginToApp(username, password);

                String currentUrl =
                        Base.getDriver().getCurrentUrl();

                if (EXPECTED_HOME_URL.equalsIgnoreCase(currentUrl)) {

                    Report.reportLog(Status.PASS,
                            "Login successful for user: " + username);

                    homePage.logOutFromApp(Base.getDriver(), 30);

                } else {

                    Wait.waitForElementToBeVisible(
                            Base.getDriver(),
                            loginPage.getInvalidCredentialMsg(),
                            30
                    );

                    String actualMsg =
                            loginPage.getInvalidCredentialMsg().getText();

                    Assert.assertEquals(
                            actualMsg,
                            EXPECTED_INVALID_MSG,
                            "Invalid credential message mismatch"
                    );

                    Report.reportLog(Status.FAIL,
                            "Login failed for user: " + username);
                }
            }

        } catch (IOException e) {
            Assert.fail("Error reading test data file", e);
        }
    }

    // ---------- RESULT LOGGING ----------
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Report.reportLog(Status.FAIL,
                    result.getThrowable().getMessage());
        }
        Reporter.log("Executed: " + result.getName(), true);
    }

    // ---------- CLEANUP ----------
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Base.quitDriver();
    }

    // ---------- FLUSH REPORT ----------
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        Report.endReport();
    }
}
