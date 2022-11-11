package testScripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTest implements ITestListener {

    public static ExtentReports extent = base1.ExtentManager.createInstance();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription())
                .assignAuthor(System.getProperty("user.name"));
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS,"Test Case: "+result.getMethod().getMethodName()+ " is passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        test.get().log(Status.PASS,"Test Case: "+result.getMethod().getMethodName()+ " is failed.");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
        test.get().log(Status.PASS,"Test Case: "+result.getMethod().getMethodName()+ " is skipped.");
    }


    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
