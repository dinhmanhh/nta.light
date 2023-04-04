
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

public class cfx_register_indi {
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
		driver.get("https://reg-cfx-uatjp.nextop.asia/registration");
		String email = "indi" + rand.nextInt(999999999) + "@maillinator.com";
		// input email
		driver.findElement(By.name("mailAddress")).sendKeys(email);
		System.out.println(email);

		// input birthday
		driver.findElement(By.name("dateOfBirth")).sendKeys(birthday);
		// input password
		driver.findElement(By.name("password")).sendKeys(password);
		// checkbox-agree
		driver.findElement(By.id("checkbox-agree")).click();
		// button register
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// verify register success
		Assert.assertEquals(driver.findElement(By.xpath("//h3[text()='認証メール送信']")).getText(), "認証メール送信");

		// go to maillocaluatjp
		driver.get("http://maillocaluatjp.nextop.asia/");
		//email
		driver.findElement(By.id("rcmloginuser")).sendKeys(email);
		//password
		driver.findElement(By.id("rcmloginpwd")).sendKeys(password);
		// button login
		driver.findElement(By.id("rcmloginsubmit")).click();
		// click on email
		driver.findElement(By.xpath("//span[text()='[CFX-UAT]口座開設メールアドレス認証のお知らせ']/parent::a")).click();
		sleepInSecond(1);
		// switch to iframe
		driver.switchTo().frame(driver.findElement(By.cssSelector("div#mailpreviewframe iframe"))); 
		// get link active
		String linkActive = driver.findElement(By.xpath("//a[contains(@href,'rest/account/activeAccount')]")).getText();
		// go to link verify -> update info
		driver.get(linkActive);
		driver.findElement(By.id("submitBt")).click();
		sleepInSecond(2);
	
		// section 1
		// check all agreement
		driver.findElement(By.cssSelector("div.btn-checkall a.checkall-register")).click();
		sleepInSecond(1);
		
		// section 2
		// first Name ご氏名 - 姓
		driver.findElement(By.name("lastName")).sendKeys("indi");
		// last Name ご氏名 - 名
		driver.findElement(By.name("firstName")).sendKeys("test");
		// フリガナ - セイ - first Name Kana
		driver.findElement(By.name("lastNameKana")).sendKeys(",,");
		// フリガナ - メイ - last Name Kana
		driver.findElement(By.name("firstNameKana")).sendKeys("..");
		sleepInSecond(1);
		// 性別 - sex
		driver.findElement(By.xpath("//div[@id='divSex']/label/span[text()=' 男性 ']")).click();
		sleepInSecond(1);
		
		// section 3
		// 郵便番号 - zipcode
		driver.findElement(By.name("postCode")).sendKeys("1000000");
		sleepInSecond(1);
		// section - 番地等
		driver.findElement(By.name("section")).sendKeys("section");
		// buildingName - マンション名・部屋番号
		driver.findElement(By.name("buildingName")).sendKeys("buildingName");
		// tel 1 - 電話番号
		driver.findElement(By.name("tel1")).sendKeys("34545654542");
		// tel 2 - 電話番号
		driver.findElement(By.name("tel2")).sendKeys("65498450000");
		sleepInSecond(1);
		
		// section 4
		// 職業 - job type
		new Select(driver.findElement(By.name("jobType"))).selectByVisibleText("会社員");
		sleepInSecond(1);
		// 職業 - industry Type
		new Select(driver.findElement(By.name("industryType"))).selectByVisibleText("製造業");
		// company name
		driver.findElement(By.name("companyName")).sendKeys("company name");
		// 年収  - annual Income
		new Select(driver.findElement(By.name("annualIncome"))).selectByVisibleText("250万円～500万円未満");
		// 投資可能金額  - financial Assets
		new Select(driver.findElement(By.name("financialAssets"))).selectByVisibleText("250万円～500万円未満");
		// 主な収入源  - main Income
		new Select(driver.findElement(By.name("mainIncome"))).selectByVisibleText("給与所得");
		
		// FX投資目的
		driver.findElement(By.name("purposeShortTermFlg")).click();
		sleepInSecond(1);
		
		//section 5
		// 申込の経緯  - discover Source
		new Select(driver.findElement(By.name("discoverSource"))).selectByVisibleText("他サイト");
		// discoverSourceComment
		driver.findElement(By.name("discoverSourceComment")).sendKeys("discoverSourceComment");
		// 投資経験：FX・CFD取引  - invest Exp Fx
		new Select(driver.findElement(By.name("investExpFx"))).selectByVisibleText("5年以上");
		// 投資経験:現物株式  - invest Exp Stock
		new Select(driver.findElement(By.name("investExpStock"))).selectByVisibleText("5年未満");
		// 投資経験:信用株式  - invest Exp Stock Trust
		new Select(driver.findElement(By.name("investExpStockTrust"))).selectByVisibleText("4年未満");
		// 投資経験:先物オプション  - invest Exp Option
		new Select(driver.findElement(By.name("investExpOption"))).selectByVisibleText("3年未満");
		// 投資経験:商品先物  - invest Exp Future Trading
		new Select(driver.findElement(By.name("investExpFutureTrading"))).selectByVisibleText("2年未満");
		
		// register bank
		driver.findElement(By.xpath("//div[@id='showBankDiv']/label/span[text()=' 今すぐ登録 ']")).click();
		sleepInSecond(1);
		// select bank
		driver.findElement(By.xpath("//div[@class='bank-list']//label[@id='0033']")).click();
		// search branch
		driver.findElement(By.id("isSearchBranch")).click();
		sleepInSecond(2);
		// select branch - 支店名
		driver.findElement(By.xpath("//td[text()='002']/parent::tr//button")).click();
		sleepInSecond(1);
		// input account number - 口座番号
		driver.findElement(By.name("accountNo")).sendKeys("8888888");
		// input note
		driver.findElement(By.name("note")).sendKeys("note");
		// 情報のご案内
		driver.findElement(By.xpath("//div[text()=' 情報のご案内 ']/parent::div//label/span[text()=' いいえ ']")).click();
		sleepInSecond(1);
		
		// button confirm 
		driver.findElement(By.cssSelector("button.btn-confirm.btn-success")).click();
		sleepInSecond(2);
		
		// click register
		driver.findElement(By.cssSelector("a.RightBtn")).click();
		sleepInSecond(2);
		
		// verify register success
		Assert.assertEquals(driver.findElement(By.cssSelector("h4.title-upload span")).getText(), "必要書類のご提出");
		// logout register
		driver.findElement(By.cssSelector("a.logout-url")).click();
		sleepInSecond(2);
		// logout maillocal
		driver.get("http://maillocaluatjp.nextop.asia/");
		sleepInSecond(1);
		driver.findElement(By.cssSelector("div#topline a.button-logout")).click();
		sleepInSecond(25);
		
		// go to BE
		driver.get("https://amsbe-cfx-uatjp.nextop.asia/account/Login");
		// input user, password
		driver.findElement(By.id("username_id")).sendKeys(usernameBE);
		driver.findElement(By.id("password")).sendKeys(passwordBE);
		// click login
		driver.findElement(By.cssSelector("button#but_login")).click();
		sleepInSecond(2);
		// input email registed
		driver.findElement(By.id("account-info_condition_email")).sendKeys(email);
		// click search
		driver.findElement(By.cssSelector("a[title='Searching']")).click();
		sleepInSecond(2);
		String accountInformationID = driver.getWindowHandle();

		// click on account id
		driver.findElement(By.xpath("//tr[@class='odd first last']//a[contains(@href,'detail-account-ams')]")).click();
		switchToWindowByID(accountInformationID);
		sleepInSecond(2);
		
		// select Account open status
		driver.findElement(By.cssSelector("a#divNtdFxOpenStatus-button>span.ui-selectmenu-icon")).click();
		sleepInSecond(1);
		driver.findElement(By.xpath("//a[text()='Account Open Requesting']")).click();
		// input change reason
		driver.findElement(By.name("customer.changeReason")).sendKeys("changeto AccountOpenRequesting");
		sleepInSecond(1);
		// click save
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		sleepInSecond(2);
		// click confirm
		driver.findElement(By.xpath("//a[text()=' Confirm ']")).click();
		sleepInSecond(2);

		// select Review Completed
		driver.findElement(By.cssSelector("a#divNtdFxOpenStatus-button>span.ui-selectmenu-icon")).click();
		sleepInSecond(1);
		driver.findElement(By.xpath("//a[text()='Review Completed']")).click();
		// input change reason
		driver.findElement(By.name("customer.changeReason")).sendKeys("changeto ReviewCompleted");
		sleepInSecond(1);
		// click save
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		sleepInSecond(2);
		// click confirm
		driver.findElement(By.xpath("//a[text()=' Confirm ']")).click();
		sleepInSecond(2);
		
		// select Open Completed
		driver.findElement(By.cssSelector("a#divNtdFxOpenStatus-button>span.ui-selectmenu-icon")).click();
		sleepInSecond(1);
		driver.findElement(By.xpath("//a[text()='Open Completed']")).click();
		// input change reason
		driver.findElement(By.name("customer.changeReason")).sendKeys("changeto OpenCompleted");
		sleepInSecond(1);
		// click save
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		sleepInSecond(2);
		// click confirm
		driver.findElement(By.xpath("//a[text()=' Confirm ']")).click();
		sleepInSecond(2);
		
		// verify open completed
		Assert.assertEquals(driver.findElement(By.cssSelector("a#divNtdFxOpenStatus-button span.ui-selectmenu-status")).getText(), "Open Completed");
		writeData(email);	
		sleepInSecond(1);
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
	}
	
	// neu muon tao 1 luc 4 account them @Test vao ham duoi nay
	public void registerEmails() {
		for (int i=0; i<3; i++)
			Register_Indi();
	}
	
	public void switchToWindowByID(String pageID) {
		Set<String> allIDs = driver.getWindowHandles();
		for (String id : allIDs) {
			if (!id.equals(pageID)) {
				driver.switchTo().window(id);
				sleepInSecond(1);
			}
		}
	}

    public static void writeData(String data) {
        try {
            FileWriter writer = new FileWriter("Account.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    /*
	public static String passwordBE() {
		String password = "";
		try {
			FileReader reader = new FileReader("passwordBE.txt");

			BufferedReader bufferedReader = new BufferedReader(reader);
			while ((password = bufferedReader.readLine()) != null) {
				reader.close();
				return password;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	} 
	*/
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}