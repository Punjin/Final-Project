package Amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.Set;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddProductToCart {
WebDriver driver;


    //This method is executed before the test suite starts.
	@BeforeSuite
	public void setup()
	{
		// Set up and initialize the Chrome driver and maximize the browser window.
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	  // This is the first test case that opens the Amazon website.
	  @Test(priority=1)
	public void openApp() throws Exception
	{
		driver.get("https://www.amazon.in/?&ext_vrnc=hi&tag=googhydrabk1-21&ref=pd_sl_7hz2t19t5c_e&adgrpid=58355126069&hvpone=&hvptwo=&hvadid=610644601173&hvpos=&hvnetw=g&hvrand=201308671182816415&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9302561&hvtargid=kwd-10573980&hydadcr=14453_2316415");
		driver.manage().deleteAllCookies();
	    Thread.sleep(8000);
	}
	  
	// This is the second test case that adds a product to the cart.
	@Test(priority=2)
	public void addToCart()
	{
		// Find the search box and enter a search query.
		WebElement searchBox=driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Puma sneakers for men",Keys.ENTER);
		
		// Get the parent window handle.
		String parentWindow=driver.getWindowHandle();
		// Find and click on a product.
		WebElement shoes=driver.findElement(By.xpath("(//a[@class=\"a-link-normal s-no-outline\"])[1]"));
		shoes.click();
		
		// Switch to the new window that opened after clicking on the product.
		Set<String> handles=driver.getWindowHandles();
		for(String handle:handles)
			{
			if(!(handle==parentWindow))
			{
				driver.switchTo().window(handle);
			}
			}
		
		// Find and click on the "Add to Cart" button.
		WebElement addToCartBtn=driver.findElement(By.id("add-to-cart-button"));
		addToCartBtn.click();
	}
	
	// This method is executed after all the test cases, and it quits the browser.
	@AfterSuite
	public void teardown()
	{
		driver.quit();
	}

}
