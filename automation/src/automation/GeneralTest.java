package automation;


import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


/***
 * Automated test for the form on https://docs.google.com/forms/d/e/1FAIpQLScVG7idLWR8sxNQygSnLuhehUNVFti0FnVviWCSjDh-JNhsMA/viewform
 * 
 * For reporting, TestNG is used.
 * In the code, you need to set your chrome driver path.
 * @author Utku ULKU
 *
 */
@Listeners(ListenerForTests.class)
public class GeneralTest {
	ArrayList<String> validEmailAdresses = new ArrayList<String>();
	ArrayList<String> invalidEmailAdresses = new ArrayList<String>();
	ArrayList<String> validPhones = new ArrayList<String>();
	ArrayList<String> invalidPhones = new ArrayList<String>();
	WebDriver driver;
	String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLScVG7idLWR8sxNQygSnLuhehUNVFti0FnVviWCSjDh-JNhsMA/viewform";
	String chromeDriverPath = "Chrome Driver Folder\\chromedriver.exe"; //set your chrome driver path.
	String chromeDriver = "webdriver.chrome.driver";
	String formResponseUrl = "https://docs.google.com/forms/d/e/1FAIpQLScVG7idLWR8sxNQygSnLuhehUNVFti0FnVviWCSjDh-JNhsMA/formResponse";
	WebElement nameTxtBox;
	WebElement mailTxtBox;
	WebElement addressTxtBox;
	WebElement sendBtn;
	int validEmailAddressIndex = 0, invalidEmailAddressIndex = 0;
	int validPhoneIndex = 0, invalidPhoneIndex = 0;
	@BeforeClass
	public void setValidEmails() {
		validEmailAdresses.add("email@domain.com");
		validEmailAdresses.add("firstname.lastname@domain.com");
		validEmailAdresses.add("email@subdomain.domain.com");
		validEmailAdresses.add("firstname+lastname@domain.com");
		validEmailAdresses.add("email@123.123.123.123");
		validEmailAdresses.add("email@[123.123.123.123]");
		validEmailAdresses.add("\"email\"@domain.com");
		validEmailAdresses.add("1234567890@domain.com");
		validEmailAdresses.add("email@domain-one.com");
		validEmailAdresses.add("_______@domain.com");
		validEmailAdresses.add("email@domain.name");
		validEmailAdresses.add("email@domain.co.jp");
		validEmailAdresses.add("firstname-lastname@domain.com");
	}
	
	@BeforeClass
	public void setValidPhones() {
		validPhones.add("+9055522221133");
		validPhones.add("055511223365");
		validPhones.add("009055004455");
	}
	
	@BeforeClass
	public void setInvalidPhones() {
		invalidPhones.add("+905552+133");
		invalidPhones.add("055Letter511223365");
		invalidPhones.add("Letter009055004455");
	}
	
	@BeforeClass
	public void setInvalidEmails() {
		invalidEmailAdresses.add("<email@domain.com>");
		invalidEmailAdresses.add("plainaddress");
		invalidEmailAdresses.add("#@%^%#$@#$@#.com");
		invalidEmailAdresses.add("@domain.com");
		invalidEmailAdresses.add("email.domain.com");
		invalidEmailAdresses.add("email@domain@domain.com");
		invalidEmailAdresses.add(".email@domain.com");
		invalidEmailAdresses.add("email.@domain.com");
		invalidEmailAdresses.add("email..email@domain.com");
		invalidEmailAdresses.add("&#12354;&#12356;&#12358;&#12360;&#12362;@domain.com");
		invalidEmailAdresses.add("email@domain.com (Joe Smith)");
		invalidEmailAdresses.add("email@domain");
		invalidEmailAdresses.add("email@-domain.com");
	}
	
	@BeforeClass
	public void bfClass() {
		System.setProperty(chromeDriver, chromeDriverPath);
	}
	
	private void setNecessaryTextBoxesAndSendBtn() {
		nameTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div[1]/input"));
		mailTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[3]/div/div/div[2]/div/div[1]/div/div[1]/input"));
		addressTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[4]/div/div/div[2]/div/div[1]/div[2]/textarea"));
		sendBtn=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[3]/div[1]/div[1]/div/span/span"));
	}
	
	private void implicitlyWaitForAllNecessaryBoxes() {
		while(!nameTxtBox.isEnabled() || !mailTxtBox.isEnabled() || !addressTxtBox.isEnabled()) {
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
		}
	}
	
	private void init() {
		setUpDriver();
		setNecessaryTextBoxesAndSendBtn();
	}
	private void setUpDriver() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(formUrl);
	}
	
	/***
	 * First test.
	 */
	@Test
	public void apositiveTest() {
		setUpDriver();
		
		WebElement optionRadioButton=driver.findElement(By.id("i5"));
		setNecessaryTextBoxesAndSendBtn();
		WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
		WebElement commentTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[6]/div/div/div[2]/div/div[1]/div[2]/textarea"));

		if(optionRadioButton != null && nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && phoneTxtBox != null && commentTxtBox != null && sendBtn != null) {
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			optionRadioButton.click();
			nameTxtBox.sendKeys("testName");
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("test address");
			phoneTxtBox.sendKeys("111222333");
			commentTxtBox.sendKeys("test comment");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLog("");
	}

	@Test
	public void bpositiveTestSecond() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("testName");
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("test address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}
		
		assertAndLog("");
	}
	
	@Test
	public void cnegativeTestFirst() {
		init();

		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("");
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("test address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("Name is empty.");
	}
	
	@Test
	public void dnegativeTestSecond() {
		setUpDriver();

		mailTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[3]/div/div/div[2]/div/div[1]/div/div[1]/input"));
		addressTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[4]/div/div/div[2]/div/div[1]/div[2]/textarea"));
		sendBtn=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[3]/div[1]/div[1]/div/span/span"));
		
		if(mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			while(!mailTxtBox.isEnabled() || !addressTxtBox.isEnabled()) {
				driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			}
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("test address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("Name is empty.");
	}
	
	@Test
	public void enegativeTestWithSpacesInName() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("     ");
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("test address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("Name only contains spaces");
	}

	@Test
	public void fnegativeTestWithSpacesInAddress() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@testdomain.com");
			addressTxtBox.sendKeys("    ");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("Address only contains spaces");
	}

	@Test
	public void gnegativeTestWithSpacesInMail() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("   ");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail only contains spaces");
	}

	@Test
	public void hnegativeTestWrongFormatInMail() {
		init();
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("testtestdomain.com");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail: testtestdomain.com");
	}
	
	@Test
	public void inegativeTestAnotherWrongFormatInMail() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@testdomain");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail: test@testdomain");
	}
	
	@Test
	public void knegativeTestAnotherWrongFormatInMail() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@testdomain.");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail: test@testdomain.");
	}
	
	@Test
	public void lnegativeTestAnotherWrongFormatInMail() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@testdomain.c");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail: test@testdomain.c");
	}
	
	@Test
	public void mnegativeTestAnotherWrongFormatInMail() {
		init();
		
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@.co");
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		assertAndLogForInvalidInput("E-mail: test@.co");
	}
	
	
	@Test
	public void firstInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void secondInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void thirdInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void fourthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void fifthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void sixthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}

	@Test
	public void seventhInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void eighthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void ninethInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void tenthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void eleventhInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void twelwethInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}
	
	@Test
	public void thirteenthInvalidEmailTest() {
		init();
		String emailAddress = invalidEmailAdresses.get(invalidEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidEmailAddressIndex++;
		assertAndLogForInvalidInput("Email:" + emailAddress);
	}

	private void assertAndLog(String additionalText) {
		String expectedUrl=formResponseUrl;
		String actualUrl= driver.getCurrentUrl();
		
		try {
			Assert.assertEquals(actualUrl, expectedUrl);
		}catch(AssertionError assError) {
			Reporter.log("Test is failed. The form wasn't submitted, but it should be submitted " + additionalText);
			fail("Test is failed. The form wasn't submitted, but it should be submitted. " + additionalText);
		}finally {
			driver.close();
		}
	}
	
	private void assertAndLogForInvalidInput(String additionalText) {
		String expectedUrl=formUrl;
		String htmlText = additionalText.replace("<","&lt;").replace(">", "&gt;");
		String actualUrl= driver.getCurrentUrl();
		
		try {
			Assert.assertEquals(actualUrl, expectedUrl);
		}catch(AssertionError assError) {
			Reporter.log("Test is failed. The form was submitted, but it shouldn't be submitted " + htmlText);
			fail("Test is failed. The form was submitted, but it shouldn't be submitted. " + additionalText);
		}finally {
			driver.close();
		}
	}
	
	@Test
	public void firstValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void secondValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void thirdValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void fourthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void fifthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void sixthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}

	@Test
	public void seventhValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void eighthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void ninethValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void tenthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void eleventhValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void twelwethValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void thirteenthValidEmailTest() {
		init();
		String emailAddress = validEmailAdresses.get(validEmailAddressIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys(emailAddress);
			addressTxtBox.sendKeys("test Address");
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validEmailAddressIndex++;
		assertAndLog("Email:" + emailAddress);
	}
	
	@Test
	public void firstValidphoneTest() {
		init();
		String phone = validPhones.get(validPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validPhoneIndex++;
		assertAndLog("Phone:" + phone);
	}
	
	@Test
	public void secondValidphoneTest() {
		init();
		String phone = validPhones.get(validPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validPhoneIndex++;
		assertAndLog("Phone:" + phone);
	}
	
	@Test
	public void thirdValidphoneTest() {
		init();
		String phone = validPhones.get(validPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		validPhoneIndex++;
		assertAndLog("Phone:" + phone);
	}
	
	@Test
	public void firstInvalidphoneTest() {
		init();
		String phone = invalidPhones.get(invalidPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidPhoneIndex++;
		assertAndLogForInvalidInput("Phone:" + phone);
	}
	
	@Test
	public void secondInvalidphoneTest() {
		init();
		String phone = invalidPhones.get(invalidPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidPhoneIndex++;
		assertAndLogForInvalidInput("Phone:" + phone);
	}
	
	@Test
	public void thirdInalidphoneTest() {
		init();
		String phone = invalidPhones.get(invalidPhoneIndex);
		if(nameTxtBox != null && mailTxtBox != null
				&& addressTxtBox != null && sendBtn != null) {
			implicitlyWaitForAllNecessaryBoxes();
			WebElement phoneTxtBox=driver.findElement(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input"));
			nameTxtBox.sendKeys("test Name");
			mailTxtBox.sendKeys("test@test.com");
			addressTxtBox.sendKeys("test Address");
			phoneTxtBox.sendKeys(phone);
			sendBtn.click();
		}else {
			System.out.println("Null value exists");
		}

		invalidPhoneIndex++;
		assertAndLogForInvalidInput("Phone:" + phone);
	}
}


