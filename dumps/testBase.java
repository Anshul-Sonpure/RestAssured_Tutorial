package testScripts;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;


public class testBase extends ExtentReportSetUp {

    String className;
    //ExtentTest test;

    @BeforeTest
    public void Initialise_ExtentReport(ITestContext iTestContext ) throws Exception
    {
        className = iTestContext.getCurrentXmlTest().getClasses().stream()
                .findFirst().get().getName();
        className=className.replace("testScripts.","");
        extent = ExtentReportSetUp.setupExtentReport();
        test = extent.createTest(className).assignAuthor(System.getProperty("user.name"));
        ExtentFactory.getInstance().setExtent(test);

    }

    @BeforeMethod
    public void setup(Method method) {
        String testMethodName = method.getName();
        test.createNode(testMethodName);

    }
    @AfterTest
    public void teardown()
    {
        extent.flush();

    }
    @AfterMethod
    public void testLogs(ITestResult result) throws Exception
    {
        if(result.getStatus()==ITestResult.FAILURE)
        {
            Throwable e = result.getThrowable();
            test.fail(e);
            ExtentFactory.getInstance().setExtent(test);
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Test Case: "+result.getMethod().getMethodName()+ " is Failed.");
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, result.getThrowable());
            ExtentFactory.getInstance().removeExtentObject();
        }
        else if(result.getStatus()==ITestResult.SKIP)
        {
            Throwable e = result.getThrowable();
            test.skip(e);
            ExtentFactory.getInstance().setExtent(test);
            ExtentFactory.getInstance().getExtent().log(Status.SKIP, "Test Case: "+result.getMethod().getMethodName()+ " is skipped.");
            ExtentFactory.getInstance().removeExtentObject();
        } else if (result.getStatus()==ITestResult.SUCCESS)
        {
            ExtentFactory.getInstance().setExtent(test);
            ExtentFactory.getInstance().getExtent().log(Status.PASS,"Test Case: "+result.getMethod().getMethodName()+ " is passed.");
            ExtentFactory.getInstance().removeExtentObject();


        }
    }

}
