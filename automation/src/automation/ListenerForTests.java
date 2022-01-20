package automation;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ListenerForTests implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log("Test has started. \n", true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log("Test has passed. \n", true);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log("Test has failed.\n", true);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log("Test has been skipped. \n", true);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Reporter.log("Test has failed within success percentage. \n", true);
	}

	@Override
	public void onStart(ITestContext context) {
		Reporter.log("Tests are going to be run...\n", true);
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.log("All tests were run...\n", true);
	}
	
}