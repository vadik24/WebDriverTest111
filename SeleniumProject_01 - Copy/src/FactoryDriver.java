import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FactoryDriver {

	//Driver Type
	private WebDriver driver = null;
	
	public WebDriver getBrowser(String browserType){
		if(driver == null){
			if(browserType == "FireFox"){
				driver = new FirefoxDriver();
			}
			else if(browserType == "InternetExplorer"){
				driver = new InternetExplorerDriver();
			}
			else if(browserType == "Chrome"){
				driver = new ChromeDriver();
			}
			else
				System.out.println("Cannot find WebDriver for" + browserType);
		}
		
		return driver;
		
	}
}
