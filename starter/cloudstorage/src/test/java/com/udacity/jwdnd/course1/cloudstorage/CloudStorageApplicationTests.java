package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testAuthorizedAccess() {
		//signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Shawn", "Sun", "sunxw", "Sxw123456");
		//login
		driver.get("http://localhost:" + this.port +  "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("sunxw", "Sxw123456");
		Assertions.assertEquals("Home", driver.getTitle());
		//logout
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get("http://localhost:" + this.port +  "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNoteOperations() throws InterruptedException {
		//signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Shawn", "Sun", "sunxw", "Sxw123456");

		//login
		driver.get("http://localhost:" + this.port +  "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("sunxw", "Sxw123456");

		//add note
		HomePage homePage = new HomePage(driver);
		homePage.addNote("Note", "My first note.");

		//check if note can be seen in table
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.clickNoteTab();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
		Assertions.assertEquals("Note",driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th")).getText());
		Assertions.assertEquals("My first note.", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]")).getText());

		//check note revision
		homePage.editNote("new note", "my second note");
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.clickNoteTab();
		wait.until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
		Assertions.assertEquals("new note",driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th")).getText());
		Assertions.assertEquals("my second note", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]")).getText());

		//check note delete
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.deleteNote();
		Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='userTable']/tbody")).size());
	}

	@Test
	public void testCredentialOperations() throws InterruptedException {
		//signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Shawn", "Sun", "sunxw", "Sxw123456");

		//login
		driver.get("http://localhost:" + this.port +  "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("sunxw", "Sxw123456");

		//add credential
		HomePage homePage = new HomePage(driver);
		homePage.addCredential("www.baidu.com", "sunxw", "sxw123");
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.clickCredentialTab();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
		Assertions.assertEquals("www.baidu.com",driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th")).getText());
		Assertions.assertEquals("sunxw", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]")).getText());

		//check credential revision
		homePage.editCredential("www.google.com", "jqka", "yy123456");
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.clickCredentialTab();
		wait.until(ExpectedConditions.visibilityOf
				(driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
		Assertions.assertEquals("www.google.com",driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th")).getText());
		Assertions.assertEquals("jqka", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]")).getText());

		//check credential delete
		driver.get("http://localhost:" + this.port +  "/home");
		homePage.deleteCredential();
		Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='userTable']/tbody")).size());
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		//Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

}
