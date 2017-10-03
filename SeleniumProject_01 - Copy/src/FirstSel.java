

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
public class FirstSel {
	private WebDriver driver;
	@Test
	public void test() throws Exception{
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		//System.setProperty("webdriver.chrome.driver", "\\Users\\V_Bel\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver.get("https://www.hotmail.com");
		driver.findElement(By.id("i0116")).sendKeys("v_belonenko@hotmail.com");
		driver.findElement(By.id("ariaId_25")).click();
	
		//WebElement searchBox = driver.findElement(By.className("gb_P"));
		WebElement searchBox = driver.findElement(By.className("gsfi"));
		searchBox.click();
		searchBox.sendKeys("Hahaha");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("WebDriver Test Successful.");
		Thread.sleep(5000);  // Let the user actually see something!
		driver.quit();
	}
	
	@BeforeMethod
	public void beforeMethod(){
		FactoryDriver driverType = new FactoryDriver();
		driver = driverType.getBrowser("Chrome");
		//max window screen to full size
		driver.manage().window().fullscreen();
	}
	
	
	@AfterMethod
	public void afterMethod() throws InterruptedException{
		Thread.sleep(5000);
		driver.quit();
	}
	
	//encrypt
	public byte[] encode(String buffer){
		byte[] encodeByte = Base64.encodeBase64(buffer.getBytes());
		return encodeByte;
		
	}
	
	//decrypt
	public String decode(byte[] decode){
		byte[] decodeByte = Base64.decodeBase64(decode);
		return new String(decodeByte);
	}
	//if click not working, use javascript
	public void javascriptClick(WebElement element){
		((JavascriptExecutor)driver).executeScript("argument[0].click();",element);
	}
	
	//create new tab
	public void createTab(){
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
	}
	
	//switch tabs
	public void switchTab(){
		ArrayList<String> tabList = new  ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabList.get(0));
	}
	
	//is text present
	public boolean isTextPresent(String text){
		if(driver.getPageSource().contains(text)){
			System.out.println("Text is present");
			return true;
		}
		return false;
	}
	
	//switch window
	public void switchWindows(){
		//Get the current window handle
		String hBefore = driver.getWindowHandle();
		
		//click to open new windows
		
		//switch to new windows
		for(String hNew:driver.getWindowHandles()){
			driver.switchTo().window(hNew);
		}
		driver.close();
		driver.switchTo().window(hBefore);
		//Resume your work
	}
	//switch Iframe
	public void switchFrame(){
		driver.switchTo().frame(0);
	}
	
	//run javascript
	public void javaScriptExecute(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("alert('hello world', args)");
	}
	//refresh browser
	public void refresh(){
		driver.navigate().refresh();
	}
	
	//when element is visible, return
	public WebElement getWhenVisible(By locator, int timeout){
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	//click when element is visible
	public void clickWhenReady(By locator,int timeout){
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	//Simple method to check if an element Exist
	//return true if exist
	public boolean existElement(By by){
		return driver.findElements(by).size() > 0;
	}
	//drop Down List
	public void dropDownElement(By by){
		Select dropdown = new Select(driver.findElement(by));
		dropdown.deselectAll();
		dropdown.selectByVisibleText("selectLabel");
		//Get the first selected value from the dropdown'
		dropdown.getFirstSelectedOption();
	}

 //takes screenshot and save to local driver
	public void takeScreenShot() throws IOException{
		//take a screenshot
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		//copy the screenshot to desired location using copyFile method
		FileUtils.copyFile(src, new File("C:/ScreenShot/ScreenShot.png"));
		
	}
	//partial screenshot and convert to file, save to local directory
	public void takePartialScreenShot(WebElement element) throws IOException{
		//Capture entire page screenshot as buffer.
		  //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
		  File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  
		  //Used selenium getSize() method to get height and width of element.
		  //Retrieve width of element.
		  int ImageWidth = element.getSize().getWidth();
		  //Retrieve height of element.
		  int ImageHeight = element.getSize().getHeight();  
		  
		  //Used selenium Point class to get x y coordinates of Image element.
		  //get location(x y coordinates) of the element.
		  Point point = element.getLocation();
		  int xcord = point.getX();
		  int ycord = point.getY();
		  
		  //Reading full image screenshot.
		  BufferedImage img = ImageIO.read(screen);
		  
		  //cut Image using height, width and x y coordinates parameters.
		  BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
		  ImageIO.write(dest, "png", screen);
		  
		  //Used FileUtils class of apache.commons.io.
		  //save Image screenshot In D: drive.
		  FileUtils.copyFile(screen, new File("C:/ScreenShot/screenshot.png"));
	}
}

