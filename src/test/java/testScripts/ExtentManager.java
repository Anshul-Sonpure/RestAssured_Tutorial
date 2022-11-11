package base1;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentManager {
	 public static String timeStamp = new SimpleDateFormat("dd-MM-yyyy-HH_mm_ss").format(new Date());

	    public static ExtentReports extent;
	    public static ExtentSparkReporter spark;
	    public static ExtentTest test;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports createInstance() {
        
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
