package testCases;

import appSpecific.ScreenshotUtil;
import appSpecific.Wait;
import com.aventstack.extentreports.Status;
import generic.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pages.HomePage;
import pages.LoginPage;

import java.io.*;
import java.lang.reflect.Method;

public class LoginTest {

    // ---------- CONSTANTS ----------
    private static final String EXPECTED_TITLE = "OrangeHRM";
    private static final String EXPECTED_INVALID_MSG = "Invalid credentials";
    private static final String FILE_PATH =
            System.getProperty("user.dir") + "/config/config";
    private static final String USER_DATA =
            System.getProperty("user.dir") + "/test-data/users";
    private static final String REPORT_PATH =
            System.getProperty("user.dir") + "/reports/";
    private static final String IMAGE_PATH =
            System.getProperty("user.dir") + "/screenshots/";
    private static final String EXPECTED_HOME_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    private static final String EXCEL_PATH =
            System.getProperty("user.dir") + "/test-data/LoginData.xls";
    private static final String URL =
            ConfigFileReader.getPropertyValue(FILE_PATH, "webUrl");

    // ---------- OBJECTS ----------
    LoginPage loginPage;
    HomePage homePage;

    // ---------- EXTENT REPORT ----------
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        Report.startReport(REPORT_PATH, "AutomationReport", "Login Test Suite");
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
    @Test(testName = "Login using valid credentials", groups = {"Smoke Test"}, priority = 1,enabled = true)
    public void test_single_user_login() {
        String actualTitle = loginPage.getTitle(Base.getDriver());
        if (!EXPECTED_TITLE.equalsIgnoreCase(actualTitle)) {
            Report.reportLog(Status.FAIL, "Login page title mismatch. Expected: " + EXPECTED_TITLE + " | Actual: " + actualTitle);
            Assert.fail("Login page title mismatch");
        }
        Report.reportLog(Status.INFO, "Entering valid username and password");
        loginPage.loginToApp("admin", "admin123");
        Report.reportLog(Status.INFO, "User waiting for the dashboard to load completely");
        Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getdashBoardLabel(), 30);
        Report.reportLog(Status.INFO, "User waiting for the welcome message to load completely");
        Wait.waitForElementToBeVisible(Base.getDriver(), homePage.getWelcomeMsg(), 30);
        if (homePage.getdashBoardLabel().isDisplayed() && homePage.getWelcomeMsg().isDisplayed()) {
            Report.reportLog(Status.PASS,
                    "User logged in successfully");
            Report.reportLog(Status.INFO, "User is now logging out of the application");
            homePage.logOutFromApp(Base.getDriver(), 30);

        } else {
            Report.reportLog(Status.FAIL,
                    "Dashboard or Welcome message not visible");
            Assert.fail("Login validation failed");
        }
    }

    // ---------- TEST 2 ----------
    @Test(testName = "Login using multiple users", groups = {"regression","Smoke Test"}, priority = 2,enabled = true)
    public void test_multiple_users_login() {

        String actualTitle = loginPage.getTitle(Base.getDriver());
        Assert.assertEquals(actualTitle, EXPECTED_TITLE,
                "Login page title mismatch");

        try (BufferedReader br = new BufferedReader(new FileReader(USER_DATA))) {

            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                row++;

                if (row == 1) continue; // skip header

                String[] data = line.split(" ");
                String username = data[0];
                String password = data[1];

                Report.reportLog(Status.INFO, "Attempting login with username: " + username);
                loginPage.loginToApp(username, password);
                String currentUrl = Base.getDriver().getCurrentUrl();
                if (EXPECTED_HOME_URL.equalsIgnoreCase(currentUrl)) {

                    Report.reportLog(Status.PASS, "Login successful for username: " + username);
                    homePage.logOutFromApp(Base.getDriver(), 30);

                } else {
                    Report.reportLog(Status.PASS, "Login successful for username : " + username +" not possible");
                    Wait.waitForElementToBeVisible(Base.getDriver(), loginPage.getInvalidCredentialMsg(), 30);
                    String actualMsg = loginPage.getInvalidCredentialMsg().getText();
                    Assert.assertEquals(actualMsg, EXPECTED_INVALID_MSG, "Invalid credential");
                    Report.reportLog(Status.PASS, "Login failed for user: " + username+" due to "+actualMsg);
                }
            }

        } catch (IOException e) {
            Report.reportLog(Status.FAIL,"Failed due to :"+e.getMessage());
            Assert.fail("Error reading test data file", e);
        }
    }

    // ---------- TEST 3 ----------
    @DataProvider(name = "testData")
    public Object[][] testDataFeed() {

        ReadExcelFile config = new ReadExcelFile(EXCEL_PATH);
        int rows = config.getRowCount(0);

        Object[][] credentials = new Object[rows - 1][2];
        int dataIndex = 0;
        for (int i = 1; i < rows; i++) {
            credentials[dataIndex][0] = config.getData(0, i, 0);
            credentials[dataIndex][1] = config.getData(0, i, 1);
            dataIndex++;
        }

        return credentials;
    }


    @Test(
            testName = "Login DDT using Data Providers",
            dataProvider = "testData",
            enabled = false,
            groups = {"Regression Test", "Smoke Test"},
            priority = 3
    )
    public void test_using_dataProvider(String usrName, String password) throws IOException {

        try{
                Report.reportLog(Status.INFO, "Attempting login with username: " + usrName);
                Wait.waitForPageToLoad(Base.getDriver(), 30);
                loginPage.loginToApp(usrName, password);
                String currentUrl = Base.getDriver().getCurrentUrl();
                if (EXPECTED_HOME_URL.equalsIgnoreCase(currentUrl)) {
                    Report.reportLog(Status.PASS, "Login successful for username: " + usrName);
                    homePage.logOutFromApp(Base.getDriver(), 30);
                } else {
                Report.reportLog(Status.PASS, "Login successful for username : " + usrName +" not possible");
                Wait.waitForElementToBeVisible(Base.getDriver(), loginPage.getInvalidCredentialMsg(), 30);
                String actualMsg = loginPage.getInvalidCredentialMsg().getText();
                Assert.assertEquals(actualMsg, EXPECTED_INVALID_MSG, "Invalid credential");
                Report.reportLog(Status.PASS, "Login failed for user: " + usrName+" due to "+actualMsg);
            }

        } catch (Exception e) {
            Report.reportLog(Status.FAIL,"Failed due to :"+e.getMessage());
            Assert.fail("Error reading test data file", e);
        }
    }

    // ---------- RESULT LOGGING ----------
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            String screenshotPath = ScreenshotUtil.takeScreenshot(
                    Base.getDriver(),
                    result.getName()
            );

            if (screenshotPath != null) {
                Report.attachScreenshotInReport(
                        screenshotPath,
                        "Failure Screenshot"
                );
            }

            Report.reportLog(
                    Status.FAIL,
                    result.getThrowable().getMessage()
            );
        }
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
