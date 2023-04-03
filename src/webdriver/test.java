
package webdriver;



import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class test {
	WebDriver driver;
	Random rand = new Random();
	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String password, birthday, usernameBE, passwordBE;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			// System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			// System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		password = "a123456789";
		birthday = "20000101";
		usernameBE = "manhld";
		passwordBE = "a123456789a";
	}

	@Test
	public void Register_Indi() {
		// go to registration email page
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}