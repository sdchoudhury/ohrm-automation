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

    private static final String FILE_PATH =System.getProperty("user.dir") + "config/config";

    public static void main(String[] args) {

        System.out.println(FILE_PATH);
        String val = ConfigFileReader.getPropertyValue(FILE_PATH, "webUrl");
        System.out.println(val);
    }
}
