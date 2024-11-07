package extender;

//import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Extender {

	WebDriver driver;
	int updatedCredits;
	int valueBeforeUse;
	int maxCredit;
	JavascriptExecutor js;

	@BeforeClass
	void AppLaunch() throws InterruptedException, IOException, URISyntaxException

	{

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.phot.ai/");
//		driver.get("https://www.phot.ai/");

		js = (JavascriptExecutor) driver;              //JavascriptExecutor initiation for scrolling the page

		driver.manage().window().maximize();
		Thread.sleep(4000);
		driver.manage().deleteAllCookies();
		js.executeScript("window.localStorage.clear();");

	}

	@Test(priority = 1)
	void TC_01_userLogin() throws InterruptedException {

		Thread.sleep(3000);

		// login/signup click        
		driver.findElement(By.xpath("//*[@id=\"__next\"]/div/header/nav/div[2]/div")).click();

		// entering email
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("quiwevauzixu-8681@yopmail.com");

		// Get Magic Link click
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[text()=' Get magic link']")).click();
		Thread.sleep(20000);


	}

	@Test(priority = 2)
	void TC_02_surveyDetailsSubmitting() throws InterruptedException {

		try {

			// survey 1 What is your occupation?
			driver.findElement(By.xpath("//label[normalize-space()='E-commerce Seller']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

			// survey 2 How did you hear about Phot.AI?
			driver.findElement(By.xpath("//label[normalize-space()='Ads']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

			// survey 3 What do you use Phot.AI primarily for?
			driver.findElement(By.xpath("//label[normalize-space()='E-commerce']")).click();
			driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

			// survey 4
			// Which tool you used?
			driver.findElement(By.xpath("//label[normalize-space()='Figma']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

			// Lets Get Started button click
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[contains(@class,'style_surveyCompleteBtn__DNLft')]")).click();
		}

		catch (Exception e) {
			System.out.println("Survey is not displayed");
		} finally {

			// Continue button click
			Thread.sleep(3000);
			driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div/button")).click();
		}

	}

	@Test(priority = 3 )
	public void TC_03_beforeCreditBalanceCheck() throws InterruptedException {

		Thread.sleep(3000);

		// Locate the user profile element to hover over
		WebElement userprofile = driver
				.findElement(By.xpath("//div[contains(@class,'style_roundDP__I7IIW')]//*[name()='svg']"));

		// Create Actions class instance to handle mouse hover feature
		Actions actions = new Actions(driver);

		Thread.sleep(3000);
		// Perform mouse hover action
		actions.moveToElement(userprofile).perform();

		Thread.sleep(3000);
		
		// Optionally, click on My Dashboard icon if it appears after hover.
		WebElement myDashboard = driver.findElement(By.xpath("//a[normalize-space()='My Dashboard']"));
		myDashboard.click();

		
		// credit check logic for existing credits of user

		// Locate and get the initial balance
		Thread.sleep(5000);

		WebElement actualCreditsElement = driver
				.findElement(By.xpath("//p[@class='styles_usedVsTotalCredits__OE9u4 para-3-medium']"));
		String initialCredits = actualCreditsElement.getText();
		Thread.sleep(5000);

		int index1 = initialCredits.indexOf("/");

		if (index1 == -1) {
			throw new IllegalStateException("Unexpected balance format: " + initialCredits);
		}

		// Parse the current credit value from "current/maximum" format
		valueBeforeUse = Integer.parseInt(initialCredits.substring(0, index1).trim());
		System.out.println(valueBeforeUse);

	}

	@Test(enabled = true, priority = 4)
	void TC_04_toolUsingJourney() throws InterruptedException {

		
		Thread.sleep(3000);

		
		//Locating the AI Image Extender tool icon from dashboard
		WebElement Element = driver.findElement(By.xpath("//a[15]"));

		// This will scroll the page Horizontally till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
		Thread.sleep(3000);
		Element.click();

		Thread.sleep(8000); // Waiting for loading the element

		// File uploading from storage
		WebElement uploadbutton = driver.findElement(By.xpath("//input[@id='inputFile']"));
		uploadbutton.sendKeys("C:\\Users\\shiva\\eclipse-workspace\\PhotAI\\images\\robbin.jpg");

		// Locator for Sample image upload
//		 WebElement uploadbutton=driver.findElement(By.xpath("//img[@alt='6682e51f462b3354f338582f-sample']"));

		Thread.sleep(9000);
		
		//entering custom values in ratio input fields
		driver.findElement(By.xpath("//input[@placeholder='Width']")).sendKeys("1912");
		driver.findElement(By.xpath("//input[@placeholder='Height']")).sendKeys("810");
		
		Thread.sleep(8000);
		
		try {
			driver.findElement(By.xpath("//span[normalize-space()='Generate']")).click();
			
			Thread.sleep(20000);

			boolean resultImageStatus = driver
					.findElement(By.xpath("//figure[@class='tw-flex tw-justify-center tw-items-center']//img"))
					.isDisplayed();

			Assert.assertTrue(resultImageStatus);   //validating the result image is displaying or not.
		}catch(Exception e) {
			System.out.println("Generate button is not displayed");
		}finally {

			    driver.get("https://www.phot.ai/plan-details");
		        
				// Locate and get the initial balance
				Thread.sleep(7000);
		}
		
		

		


	}
	
	@Test(enabled = true,priority = 5)
	void TC_05_afterCreditBalance() throws InterruptedException {
		
		        // credit check logic for after using it

				WebElement actualCreditsElement = driver.findElement(By.xpath("//p[@class='styles_usedVsTotalCredits__OE9u4 para-3-medium']"));
				String afterCredits = actualCreditsElement.getText();
				Thread.sleep(5000);

				int index1 = afterCredits.indexOf("/");

				if (index1 == -1) {
					throw new IllegalStateException("Unexpected balance format: " + afterCredits);
				}

				Thread.sleep(10000);
				
				 // Extracting and Parsing the current and maximum credit value from "current/maximum" format
				 updatedCredits = Integer.parseInt(afterCredits.substring(0, index1).trim());
				 maxCredit = Integer.parseInt(afterCredits.split("/")[1].trim());
				
				 System.out.println(updatedCredits);
				 
				// Ensure credits are not greater than the maximum
				    Assert.assertTrue(updatedCredits <= maxCredit, "Current credit exceeds maximum limit.");
				    System.out.println("Remaining credits value is under/equal to the maximum credit limit");

				Thread.sleep(5000);
				        
				//Checking the credit balance after using the tool.
				Assert.assertEquals(updatedCredits, valueBeforeUse + 2, "Credit did not increment as expected.");
				System.out.println("Credit is deducted as expected");
		       
		            
		            
		        
		           
		       
	}
	

	@Test(enabled = true,priority = 6)
	void Max() {
		
		
	}
	
	
	
	
	

}
