package testScripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportSetUp {

    public static String timeStamp = new SimpleDateFormat("dd-MM-yyyy-HH_mm_ss").format(new Date());

    public static ExtentReports extent;
    public static ExtentSparkReporter spark;
    public static ExtentTest test;
    public static ExtentReports setupExtentReport() throws Exception {


        spark = new ExtentSparkReporter("test-output/Reports/extentreport_" + timeStamp + ".html");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("JVM", System.getProperty("java.runtime.version"));
        extent.setSystemInfo("UserDirectory", System.getProperty("user.dir"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        return extent;
    }

}