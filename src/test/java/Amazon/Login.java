package Amazon;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
public class Login {
	WebDriver driver;
	
	// This method is executed before the test suite starts.
	
	@BeforeSuite
	public void setup()
	{
		//Initialize the Firefox driver and maximize the browser window.
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
	}
	
	// This is the first test case, and it opens the Amazon website.
	@Test(priority=1)
	public void openApp()
	{
		driver.get("https://www.amazon.in/-/hi/ap/signin?openid.pape.max_auth_age=3600&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fspr%2Freturns%2Fgift&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=amzn_psr_desktop_in&openid.mode=checkid_setup&language=en_IN&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	// This is the second test case that handles the CAPTCHA.
	@Test(priority=2)
	
	 // Find the CAPTCHA image and capture a screenshot of it.
	public void handleCaptcha() throws IOException, TesseractException, InterruptedException
	{
		try
		{
		WebElement imageElement=driver.findElement(By.xpath("//div[@class='a-row a-text-center']/child::img"));
	    File src=imageElement.getScreenshotAs(OutputType.FILE);
	    File dst=new File("C:\\Users\\lenovo\\eclipse-workspace\\EduBridgeDemoProject\\CaptchaImages\\amazoncaptcha.png");
	    FileUtils.copyFile(src, dst);
	    Thread.sleep(3000);
	    
	 // Use Tesseract to extract text from the CAPTCHA image.
	    ITesseract it=new Tesseract();
	    String captcha=it.doOCR(dst);
	    
	 // Enter the extracted CAPTCHA text into the input field.
	    WebElement enterCaptcha=driver.findElement(By.id("captchacharacters"));
	    enterCaptcha.sendKeys(captcha);
	    System.out.println(captcha);
	    Thread.sleep(6000);
	    
	 // Click the "Continue shopping" button.
	    WebElement continueShopping=driver.findElement(By.xpath("//button[text()='Continue shopping']"));
	    continueShopping.click();
		}
		catch(NoSuchElementException e)
	{
			// If there is no CAPTCHA, it means "Captcha not required."
			System.out.println("Captcha not required");
		}
	}
	
	// This is the third test case that performs the login.
	@Test(priority=3)
	public void login()
	{
		// Find the username input field and enter the username
		WebElement username=driver.findElement(By.id("ap_email"));
		username.sendKeys("jindalpuneet8@gmail.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		// Find the password input field and enter the password.
		WebElement password=driver.findElement(By.id("ap_password"));
		password.sendKeys("Puneet20@");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		// Find the "Sign In" button and click it.
		WebElement signInBtn=driver.findElement(By.id("signInSubmit"));
		signInBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		// Find the "Hello, sign in" element and check if the login was successful.
		WebElement signIn=driver.findElement(By.id("nav-link-accountList-nav-line-1"));
		String text=signIn.getText();
        Assert.assertFalse(text.equals("Hello, sign in"));	
	}
	
	// This method is executed after all the test cases, and it closes the browser.
	
	@AfterTest
	public void teardown()
	{
		driver.close();
	}
	
}
