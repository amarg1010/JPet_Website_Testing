package PetStroreTest;

import java.io.FileInputStream;



import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import PetstoreRepository.Browser;
import PetstoreRepository.FishCart;
import PetstoreRepository.HomePage;
import PetstoreRepository.LoginPage;
import PetstoreRepository.RegisterPage;
import PetstoreRepository.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Petstore {

	static ExtentTest test;
	static ExtentReports report;

	@BeforeTest
	public void startTest() {

		report = new ExtentReports(
				"C:\\Users\\Amarwamanrao.Gakhare\\Downloads\\com.petwebsite\\target\\ExtentReport.html");
		test = report.startTest("ExtentDemo");
	}

	//@Parameters({"browser"})
	@Test
	public void login() throws InterruptedException, IOException {
		
		//WebDriver driver = Browser.browserr(browser);
		 WebDriverManager.chromedriver().setup();
		 WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("https://petstore.octoperf.com/actions/Account.action");
		Thread.sleep(3000);

		// --------------HomePage---------------//
		HomePage hp = new HomePage(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		hp.registerNow().click();
		

		Properties prop = new Properties();// get the property
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Amarwamanrao.Gakhare\\Downloads\\com.petwebsite\\data.properties");
		prop.load(fis);

		// ------------ Assertion----------//
		String ActualTitle = driver.getTitle();
		String ExpectedTitle = "JPetStore Demo";
		AssertJUnit.assertEquals(ActualTitle, ExpectedTitle);
		System.out.println("AssertEquals Test Passed\n");

		// --------------RegisterPage--------------//
		RegisterPage rp = new RegisterPage(driver);
		// rl.userName().clear();
		rp.userName().sendKeys(prop.getProperty("username"));
		rp.password().sendKeys(prop.getProperty("password"));
		rp.repeatpass().sendKeys(prop.getProperty("password"));
		rp.fname().sendKeys(prop.getProperty("fname"));
		rp.lname().sendKeys(prop.getProperty("lname"));
		rp.email().sendKeys(prop.getProperty("email"));
		rp.phone().sendKeys(prop.getProperty("phone"));
		rp.add1().sendKeys(prop.getProperty("add1"));
		rp.add2().sendKeys(prop.getProperty("add2"));
		rp.city().sendKeys(prop.getProperty("city"));
		rp.state().sendKeys(prop.getProperty("state"));
		rp.zip().sendKeys(prop.getProperty("zip"));
		rp.country().sendKeys(prop.getProperty("country"));

		Thread.sleep(3000);

		rp.signIn().click();
		rp.home().click();
		Thread.sleep(3000);


		Properties prop1 = new Properties();// get the property
		FileInputStream fis1 = new FileInputStream(
				"C:\\Users\\Amarwamanrao.Gakhare\\Downloads\\com.petwebsite\\data.properties");
		prop1.load(fis1);

		// ------------LoginPage----------------//
		LoginPage rl = new LoginPage(driver);
		rl.userName().clear();
		rl.userName().sendKeys(prop1.getProperty("username"));
		rl.password().clear();
		rl.password().sendKeys(prop1.getProperty("password"));
		rl.loginbutton().click();

		
			
		try {
			Assert.assertTrue(rl.logout().isDisplayed());
			test.log(LogStatus.PASS, "Sign in Success");
		} catch (NoSuchElementException | AssertionError | ElementNotInteractableException ele) {
			test.log(LogStatus.FAIL, "Sign in Failed");
		}


		Properties prop2 = new Properties();// get the property
		FileInputStream fis2 = new FileInputStream(
				"C:\\Users\\Amarwamanrao.Gakhare\\Downloads\\com.petwebsite\\data.properties");
		prop2.load(fis2);

		// ------------FishCard---------------//
		FishCart fc = new FishCart(driver);
		fc.Fish().click();
		fc.ProdId().click();
		fc.Addcart().click();
		test.log(LogStatus.PASS, "Saved log for pet Adding succesfully");
		fc.Checkout().click();
		test.log(LogStatus.PASS, "Saved log for Checkout succesfully");
		fc.Contnue().click();
		fc.Confirm().click();
		test.log(LogStatus.PASS, "Saved log for Confirm succesfully");

		String filename = "photo1";
		Screenshot.takeScreenShot(filename, driver);

	}


	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();

	}
}