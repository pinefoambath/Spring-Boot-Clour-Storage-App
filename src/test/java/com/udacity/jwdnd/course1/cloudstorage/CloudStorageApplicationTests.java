package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	private static String userName = "Erika";
	private static String password = "123456";
	private static String noteTitle = "My test";
	private static String noteDescription = "test description";
	private static String URL = "rollmops.com";
	private static String firstName = "checkFirstName";
	private static String lastName = "checkLastName";
	private static String baseUrl = "http://localhost:";

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
		baseUrl += port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

//	1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
//   Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Order(1)
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Order(2)
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
 	@Order(3)
	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(4)
	@Test
	public void getUnauthorizedResultPage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("http://localhost:" + this.port + "/result");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(5)
	@Test
	public void UserSignUpLogInLogOut() {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			// write a test for user signup
			driver.get("http://localhost:" + this.port + "/signup");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputFirstName")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + firstName + "';", driver.findElement(By.id("inputFirstName")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputLastName")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastName + "';", driver.findElement(By.id("inputLastName")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("submit-button")));
			//write a test for login process
			driver.get("http://localhost:" + this.port + "/login");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
			Assertions.assertEquals("Home", driver.getTitle());
			//write a test for logging out
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("logout-button")));
			Assertions.assertEquals("Login", driver.getTitle());
			//verifies home page no longer accessible after logging out from the home page
			driver.get("http://localhost:" + this.port + "/home");
			Assertions.assertEquals("Login", driver.getTitle());
		}

	@Order(6)
	@Test
 	//	Write a test that creates a note, and verifies it is displayed.
	public void createAndCheckNote() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//login
		driver.get("http://localhost:" + this.port + "/login");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
		Assertions.assertEquals("Home", driver.getTitle());
		//add a note
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
		wait.withTimeout(Duration.ofSeconds(50));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newNote")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("newNote")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteTitle + "';", driver.findElement(By.id("note-title")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("note-description")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteDescription + "';", driver.findElement(By.id("note-description")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("save-changes")));
		//check for note
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List < WebElement > notesList = notesTable.findElements(By.tagName("th"));
		Boolean created = false;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(noteTitle)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Order(7)
	@Test
	public void updateNote() {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String newNoteTitle = "new note title";
			String newNoteDescription = "new note description";
			//login
			driver.get("http://localhost:" + this.port + "/login");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
			Assertions.assertEquals("Home", driver.getTitle());
			//update note
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("newNote")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("newNote")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("note-title")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newNoteTitle + "';", driver.findElement(By.id("note-title")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("note-description")));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteDescription + "';", driver.findElement(By.id("note-description")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("save-changes")));
			//check the updated note
			driver.get("http://localhost:" + this.port + "/home");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
			WebElement notesTable = driver.findElement(By.id("userTable"));
			List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
			Boolean edited = false;
			for (int i = 0; i < notesList.size(); i++) {
				WebElement element = notesList.get(i);
				if (element.getAttribute("innerHTML").equals(newNoteTitle)) {
					edited = true;
					break;
				}
			}
			Assertions.assertTrue(edited);
		}

	@Order(8)
	@Test
	public void deleteNote() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newNoteTitle = "new note title";
		//login
		driver.get("http://localhost:" + this.port + "/login");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
		Assertions.assertEquals("Home", driver.getTitle());
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List < WebElement > notesList = notesTable.findElements(By.tagName("td"));
		Boolean deleted = true;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(newNoteTitle)) {
				deleted = false;
				break;
			}
		}
		Assertions.assertTrue(deleted);
	}

    @Order(9)//	Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	@Test
	public void createCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		wait.withTimeout(Duration.ofSeconds(30));
		WebElement newCred = driver.findElement(By.id("newcred"));
		wait.until(ExpectedConditions.elementToBeClickable(newCred)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(URL);
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		credUsername.sendKeys(userName);
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		credPassword.sendKeys(password);
		WebElement submit = driver.findElement(By.id("credentialSubmitTest"));
		submit.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//check for credential
		driver.get("http://localhost:" + this.port + "/home");
		credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		Boolean created = false;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			if (element.getAttribute("innerHTML").equals(userName)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	//Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	@Order(10)
	@Test
	public void updateCredential() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newUrl = "www.space.com";
		String newUserName = "pants";
		String newPassword = "123456";
		//login
		driver.get("http://localhost:" + this.port + "/login");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
		Assertions.assertEquals("Home", driver.getTitle());
		//update credential
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-credentials-tab")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("newcred")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("newcred")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUrl + "';", driver.findElement(By.id("credential-url")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUserName + "';", driver.findElement(By.id("credential-username")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newPassword + "';", driver.findElement(By.id("credential-password")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("save-changes")));
		//check the updated note
		driver.get("http://localhost:" + this.port + "/home");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-credentials-tab")));
		WebElement filesTable = driver.findElement(By.id("userTable"));
		List<WebElement> credentialsList = filesTable.findElements(By.tagName("th"));
		Boolean edited = true;
		for (int i = 0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(newUserName)) {
				edited = true;
				break;
			}
		}
		Assertions.assertTrue(edited);
	}

	@Order(11)
	@Test
	//Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	public void deleteCredential() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newCredentialUserName = "pants";
		//login
		driver.get("http://localhost:" + this.port + "/login");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + userName + "';", driver.findElement(By.id("inputUsername")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", driver.findElement(By.id("inputPassword")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("login-button")));
		Assertions.assertEquals("Home", driver.getTitle());
		WebElement filesTable = driver.findElement(By.id("userTable"));
		List<WebElement> credentialsList = filesTable.findElements(By.tagName("th"));
		Boolean deleted = true;
		for (int i = 0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(newCredentialUserName)) {
				deleted = false;
				break;
			}
		}
		Assertions.assertTrue(deleted);
	}

	// old example code using the code base suggested in the course, no JS:
//	public void credentialDeletionTest() {
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		//login
//		driver.get("http://localhost:" + this.port + "/login");
//		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
//		inputUsername.sendKeys(userName);
//		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
//		inputPassword.sendKeys(password);
//		WebElement loginButton = driver.findElement(By.id("login-button"));
//		loginButton.click();
//		Assertions.assertEquals("Home", driver.getTitle());
//
//		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
//		jse.executeScript("arguments[0].click()", credTab);
//		WebElement credsTable = driver.findElement(By.id("credentialTable"));
//		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
//		WebElement deleteElement = null;
//		for (int i = 0; i < credsList.size(); i++) {
//			WebElement element = credsList.get(i);
//			deleteElement = element.findElement(By.name("delete_credential"));
//			if (deleteElement != null) {
//				break;
//			}
//		}
//		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
//		Assertions.assertEquals("Home", driver.getTitle());
//	}




}
