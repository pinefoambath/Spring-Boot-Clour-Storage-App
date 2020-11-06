package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static String firstName = "checkFirstName";
	private static String lastName = "checkLastName";
	private static String userName = "root";
	private static String password = "password";
	private static String noteTitle = "Super test title";
	private static String noteDescription = "Super test description";
	private static String credURL = "example.com";

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

	//1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
//		Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getUnauthorizedResultPage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("http://localhost:" + this.port + "/result");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void newUserAccessTest() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// write a test for user signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("submit-button"));
		signUpButton.click();

		//write a test for login process
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//write a test for logging out
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals("Login", driver.getTitle());

		//verifies home page no longer accessible after logging out from the home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
//
//	2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
////  Write a test that creates a note, and verifies it is displayed.
//		Write a test that edits an existing note and verifies that the changes are displayed.
//		Write a test that deletes a note and verifies that the note is no longer displayed.

	@Test
//	Write a test that creates a note, and verifies it is displayed.
	public void noteCreationTest() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
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

		//add a note
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		wait.withTimeout(Duration.ofSeconds(50));
		WebElement newNote = driver.findElement(By.id("newNote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys(noteDescription);
		WebElement savechanges = driver.findElement(By.id("save-changes"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check for note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
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

	@Test
	public void noteUpdationTest() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newNoteTitle = "new note title";
		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//update note
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			editElement = element.findElement(By.name("edit"));
			if (editElement != null) {
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement notetitle = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(notetitle));
		notetitle.clear();
		notetitle.sendKeys(newNoteTitle);
		WebElement savechanges = driver.findElement(By.id("save-changes"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check the updated note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("th"));
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

	@Test
	public void noteDeletionTest() {
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

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null) {
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}

	@Test
	public void credentialCreationTest() {
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
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credURL);
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		credUsername.sendKeys(userName);
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		credPassword.sendKeys(password);
		WebElement submit = driver.findElement(By.id("save-credential"));
		submit.click();
		Assertions.assertEquals("Result", driver.getTitle());

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

	@Test
	public void credentialUpdationTest() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newCredUsername = "newUser";
		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//update credential
		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			editElement = element.findElement(By.name("editCred"));
			if (editElement != null) {
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		wait.until(ExpectedConditions.elementToBeClickable(credUsername));
		credUsername.clear();
		credUsername.sendKeys(newCredUsername);
		WebElement savechanges = driver.findElement(By.id("save-credential"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check the updated note
		driver.get("http://localhost:" + this.port + "/home");
		credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		credsTable = driver.findElement(By.id("credentialTable"));
		credsList = credsTable.findElements(By.tagName("td"));
		Boolean edited = false;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			if (element.getAttribute("innerHTML").equals(newCredUsername)) {
				edited = true;
				break;
			}
		}
		Assertions.assertTrue(edited);
	}

	@Test
	public void credentialDeletionTest() {
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
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null) {
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}

}
//		Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
//
//		3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
//		Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
//		Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
//		Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.