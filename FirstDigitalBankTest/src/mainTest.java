import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class mainTest {
	static WebDriver driver;
	mainPagePOM mainP = new mainPagePOM(driver);
	
	
	
	@BeforeClass
	public static void initDriver() {
		
		String path = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", path + "\\Drivers\\chromedriver.exe");
		Map<String, Object> prefs = new HashMap<String, Object>();
		// Pass the argument 1 to allow and 2 to block
		prefs.put("profile.default_content_setting_values.cookies", 2);
		prefs.put("network.cookie.cookieBehavior", 2);
		prefs.put("profile.block_third_party_cookies", true);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
	}
	
	
  @Test
  public void test() throws IOException {
	  System.out.println("-----------------Test -----------------");
	  driver.get("https://www.amazon.com/");
	  
	  mainP.searchAnItem("software automation");
	  mainP.openCSVfile();
	  for (int i=0;i<4;i++) {
		  mainP.writeIntoCSVfile();
		  mainP.moveToNextResultPage();
	  }
	  mainP.csvFileValidations("Software Automation Testing Secrets Revealed", "$0.00");
  }
  
 
  
  
  @AfterClass
	public static void tearDown() {
		driver.quit();
	}
	
}
