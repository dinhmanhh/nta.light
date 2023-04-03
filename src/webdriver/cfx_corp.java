
package webdriver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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

public class cfx_corp {
	WebDriver driver;
	Random rand = new Random();

	Select select;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String email, password, establishedDate, usernameBE, passwordBE;

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		password = "a123456789";
		establishedDate = "200001";
		usernameBE = "manhld";
	}

	@Test
	public void Register_Corp() {
		// go to registration email page
		driver.get("https://reg-cfx-uatjp.nextop.asia/registration");
		String email = "corp" + rand.nextInt(999999999) + "@maillinator.com";
		// select corporation
		driver.findElement(By.cssSelector("span.icons.corporation")).click();
		sleepInSecond(1);
		
		// input email
		driver.findElement(By.name("mailAddress")).sendKeys(email);
		System.out.println(email);
		// input birthday
		driver.findElement(By.name("dateOfBirth")).sendKeys(establishedDate);
		// input password
		driver.findElement(By.name("password")).sendKeys(password);
		// checkbox-agree
		driver.findElement(By.id("checkbox-agree")).click();
		// button register
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// verify register email success
		Assert.assertEquals(driver.findElement(By.xpath("//h3[text()='認証メール送信']")).getText(), "認証メール送信");

		// go to maillocaluatjp
		driver.get("http://maillocaluatjp.nextop.asia/");
		//email
		driver.findElement(By.cssSelector("input#rcmloginuser")).sendKeys(email);
		//password
		driver.findElement(By.cssSelector("input#rcmloginpwd")).sendKeys(password);
		// button login
		driver.findElement(By.cssSelector("button#rcmloginsubmit")).click();
		// click on email
		driver.findElement(By.xpath("//span[text()='[CFX-UAT]口座開設メールアドレス認証のお知らせ']/parent::a")).click();
		sleepInSecond(1);
		// switch to iframe
		driver.switchTo().frame(driver.findElement(By.cssSelector("div#mailpreviewframe iframe"))); 
		// get link active
		String linkActive = driver.findElement(By.xpath("//a[contains(@href,'rest/account/activeAccount')]")).getText();
		// go to link active
		driver.get(linkActive);
		// click confirm
		driver.findElement(By.id("submitBt")).click();
		// driver.get("https://reg-cfx-uatjp.nextop.asia/update-profile/update");
		sleepInSecond(2);
	
		// section 1
		// check all agreement
		driver.findElement(By.cssSelector("div.btn-checkall a.checkall-register")).click();
		sleepInSecond(1);
		
		// section 2
		// corp Full Name - 法人名
		driver.findElement(By.name("corpFullname")).sendKeys("corp Full Name");
		// corp Full name Kana - 法人名(フリガナ)
		driver.findElement(By.name("corpFullnameKana")).sendKeys(",,");

		// 郵便番号 - zipcode
		driver.findElement(By.name("postCode")).sendKeys("1000000");
		sleepInSecond(1);
		// section - 番地等
		driver.findElement(By.name("section")).sendKeys("section-section1");
		// section - 番地等
		driver.findElement(By.name("buildingName")).sendKeys("buildingName-section1");
		// tel 1 - 電話番号
		driver.findElement(By.name("corpPhoneNumber")).sendKeys("64513241545");
		sleepInSecond(1);
		
		// section 3
		// 職業 - job type
		new Select(driver.findElement(By.name("jobType"))).selectByVisibleText("金融・保険");
		// corpJobDetail - 業務内容
		driver.findElement(By.name("corpJobDetail")).sendKeys("corpJobDetail");
		// corpSettleDateMonth - 決算日 - 月
		new Select(driver.findElement(By.name("corpSettleDateMonth"))).selectByVisibleText("03");
		// corpSettleDateMonth - 決算日 - 日
		new Select(driver.findElement(By.name("corpSettleDateDay"))).selectByVisibleText("06");
		// corp Homepage - ホームページアドレス
		driver.findElement(By.name("corpHomepage")).sendKeys("https://lightfx.jp/");
		// corp Rep - 代表者(役職名)
		driver.findElement(By.name("corpRep")).sendKeys("corp Rep");
		// corp Rep Last name - 代表者氏名 - 姓
		driver.findElement(By.name("corpRepLastname")).sendKeys("corp Rep Last name");
		// corp Rep First name - 代表者氏名 - 名
		driver.findElement(By.name("corpRepFirstname")).sendKeys("corp Rep First name");
		// corp Rep last name kana - 代表者氏名(フリガナ) - セイ
		driver.findElement(By.name("corpRepLastnameKana")).sendKeys(",");
		// corp Rep First name kana - 代表者氏名(フリガナ) - メイ
		driver.findElement(By.name("corpRepFirstnameKana")).sendKeys(",");
		// 代表者生年月日(西暦)   - corp Rep Birth Day Year
		new Select(driver.findElement(By.name("corpRepBirthDayYear"))).selectByVisibleText("2001 （平成13年）");
		// 代表者生年月日(西暦)   - corp Rep Birth Day Month
		new Select(driver.findElement(By.name("corpRepBirthDayMonth"))).selectByVisibleText("02");
		// 代表者生年月日(西暦)   - corp Rep Birth Day Day
		new Select(driver.findElement(By.name("corpRepBirthDayDay"))).selectByVisibleText("02");
		// corp Rep Zipcode - 郵便番号
		driver.findElement(By.name("corpRepZipcode")).sendKeys("1000000");
		// corp Rep Section - 番地等
		driver.findElement(By.name("corpRepSection")).sendKeys("corpRepSection");
		// corp Rep building name - マンション名・部屋番号
		driver.findElement(By.name("corpRepBuildingName")).sendKeys("corpRepBuildingName");
		sleepInSecond(2);
		
		//section 4
		driver.findElement(By.xpath("//div[@id='divCeo']/label/span[text()='有り']")).click();
		sleepInSecond(2);
		// ceo full name - 氏名
		driver.findElement(By.id("ceoFullname")).sendKeys("ceoFullname");
		// ceo full name kana - 氏名（フリガナ）
		driver.findElement(By.name("ceoFullnameKana")).sendKeys("..");
		// 生年月日 - 年  - corp Ceo Birth Year
		new Select(driver.findElement(By.name("corpCeoBirthYear"))).selectByVisibleText("2001 （平成13年）");
		// 生年月日 - 月  - corp Ceo Birth Month
		new Select(driver.findElement(By.name("corpCeoBirthMonth"))).selectByVisibleText("02");
		// 生年月日- 日   - corp Ceo Birth Day
		new Select(driver.findElement(By.name("corpCeoBirthDay"))).selectByVisibleText("03");
		// corp Ceo Post Code - 郵便番号
		driver.findElement(By.name("corpCeoPostCode")).sendKeys("3000000");
		// corp Ceo Street - 番地等
		driver.findElement(By.name("corpCeoStreet")).sendKeys("corpCeoStreet");
		// corp Ceo Building Name - マンション名・部屋番号
		driver.findElement(By.name("corpCeoBuildingName")).sendKeys("corpCeoBuildingName");
		
		//実質的支配者の追加 - Benefic Owner Flg2
		driver.findElement(By.xpath("//div[@id='divBeneficOwnerFlg2']/label/span[text()=' する ']")).click();
		sleepInSecond(1);
		// benefic Owner Fullname 2 - 氏名
		driver.findElement(By.id("beneficOwnerFullname2")).sendKeys("beneficOwnerFullname2");
		// benefic Owner Fullname Kana 2 - 氏名（フリガナ）
		driver.findElement(By.name("beneficOwnerFullnameKana2")).sendKeys("...");
		// 生年月日 - 年  - benefic Owner Establish Year 2
		new Select(driver.findElement(By.name("beneficOwnerEstablishYear2"))).selectByVisibleText("2003 （平成15年）");
		// 生年月日 - 月  - benefic Owner Establish Month 2
		new Select(driver.findElement(By.name("beneficOwnerEstablishMonth2"))).selectByVisibleText("03");
		// 生年月日- 日   - benefic Owner Establish Day 2
		new Select(driver.findElement(By.name("beneficOwnerEstablishDay2"))).selectByVisibleText("04");
		// benefic Owner Zipcode 2 - 郵便番号
		driver.findElement(By.name("beneficOwnerZipcode2")).sendKeys("4000000");
		// benefic Owner Section 2 - 番地等
		driver.findElement(By.name("beneficOwnerSection2")).sendKeys("beneficOwnerSection2");
		// benefic Owner Building Name 2 - マンション名・部屋番号
		driver.findElement(By.name("beneficOwnerBuildingName2")).sendKeys("beneficOwnerBuildingName2");
		
		//実質的支配者の追加 - Benefic Owner Flg3
		driver.findElement(By.xpath("//div[@id='divBeneficOwnerFlg3']/label/span[text()='する']")).click();
		sleepInSecond(1);
		// benefic Owner Fullname 3 - 氏名
		driver.findElement(By.id("beneficOwnerFullname3")).sendKeys("beneficOwnerFullname3");
		// benefic Owner Fullname Kana 3 - 氏名（フリガナ）
		driver.findElement(By.name("beneficOwnerFullnameKana3")).sendKeys("...,");
		// 生年月日 - 年  - benefic Owner Establish Year 3
		new Select(driver.findElement(By.name("beneficOwnerEstablishYear3"))).selectByVisibleText("2004 （平成16年）");
		// 生年月日 - 月  - benefic Owner Establish Month 3
		new Select(driver.findElement(By.name("beneficOwnerEstablishMonth3"))).selectByVisibleText("04");
		// 生年月日- 日   - benefic Owner Establish Day 3
		new Select(driver.findElement(By.name("beneficOwnerEstablishDay3"))).selectByVisibleText("05");
		// benefic Owner Zipcode 3 - 郵便番号
		driver.findElement(By.name("beneficOwnerZipcode3")).sendKeys("5000000");
		// benefic Owner Section 3 - 番地等
		driver.findElement(By.name("beneficOwnerSection3")).sendKeys("beneficOwnerSection3");
		// benefic Owner Building Name 3 - マンション名・部屋番号
		driver.findElement(By.name("beneficOwnerBuildingName3")).sendKeys("beneficOwnerBuildingName3");
		
		// section 5
		driver.findElement(By.xpath("//input[@name='copyRepresentative']/following-sibling::span[text()='はい']")).click();
		sleepInSecond(1);
		// corp Pic Position - 担当者役職名
		driver.findElement(By.name("corpPicPosition")).sendKeys("corpPicPosition");
		// corp Pic Dep - 担当者所属部署
		driver.findElement(By.name("corpPicDep")).sendKeys("corpPicDep");
		// corpPicSex - 性別
		driver.findElement(By.xpath("//input[@name='corpPicSex']/following-sibling::span[text()=' 男性 ']")).click();
		// corp Pic tel - 担当者電話番号
		driver.findElement(By.name("corpPicTel")).sendKeys("65498456415");
		// corp Pic mobile - 担当者携帯電話
		driver.findElement(By.name("corpPicMobile")).sendKeys("65498450000");
		sleepInSecond(1);
		
		// section 6
		// 事業体の年商  - annual Income
		new Select(driver.findElement(By.name("annualIncome"))).selectByVisibleText("250万円～500万円未満");
		// 事業体の税抜き後年間所得  - main Income
		new Select(driver.findElement(By.name("mainIncome"))).selectByVisibleText("250万円～500万円未満");
		// 事業体の投資可能金額  - financial Assets
		new Select(driver.findElement(By.name("financialAssets"))).selectByVisibleText("250万円～500万円未満");
		// FX投資目的
		driver.findElement(By.name("purposeShortTermFlg")).click();
		sleepInSecond(1);
		// 申込の経緯  - discover Source
		new Select(driver.findElement(By.name("discoverSource"))).selectByVisibleText("他サイト");
		sleepInSecond(1);
		// discoverSourceComment
		driver.findElement(By.name("discoverSourceComment")).sendKeys("discoverSourceComment");
		// 投資経験（事業体）：ＦＸ・ＣＦＤ取引  - invest Exp Fx
		new Select(driver.findElement(By.name("investExpFx"))).selectByVisibleText("5年以上");
		// 投資経験（取引担当者）：ＦＸ・ＣＦＤ取引  - invest Exp Fx Pic
		new Select(driver.findElement(By.name("investExpFxPic"))).selectByVisibleText("5年未満");
		// 投資経験（事業体）：現物株式  - invest Exp Stock
		new Select(driver.findElement(By.name("investExpStock"))).selectByVisibleText("4年未満");
		// 投資経験（事業体）：信用株式  - invest Exp Stock Trust
		new Select(driver.findElement(By.name("investExpStockTrust"))).selectByVisibleText("3年未満");
		// 投資経験（事業体）：先物オプション  - invest Exp option
		new Select(driver.findElement(By.name("investExpOption"))).selectByVisibleText("2年未満");
		// 投資経験:現物株式  - invest Exp Future Trading
		new Select(driver.findElement(By.name("investExpFutureTrading"))).selectByVisibleText("2年未満");

		// register bank
		driver.findElement(By.xpath("//input[@name='withdrawAccountConfirm']/following-sibling::span[text()=' 今すぐ登録']")).click();
		sleepInSecond(1);
		// select bank
		driver.findElement(By.xpath("//input[@name='bank']/following-sibling::span[text()=' 三菱ＵＦＪ銀行 ']")).click();
		// search branch
		driver.findElement(By.id("isSearchBranch")).click();
		sleepInSecond(2);
		// select branch - 支店名
		driver.findElement(By.xpath("//td[text()='002']/parent::tr//button")).click();
		sleepInSecond(1);
		// input account number - 口座番号
		driver.findElement(By.name("accountNo")).sendKeys("5555555");
		// input note
		driver.findElement(By.name("note")).sendKeys("note");
		// 情報のご案内
		driver.findElement(By.xpath("//div[text()=' 情報のご案内 ']/parent::div//label/span[text()='はい']")).click();
		sleepInSecond(1);
		
		// click confirm
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		sleepInSecond(2);
		
		// click register
		driver.findElement(By.cssSelector("a.RightBtn")).click();
		sleepInSecond(2);
		
		// verify register success
		Assert.assertEquals(driver.findElement(By.cssSelector("h4.title-upload span")).getText(), "確認書類等のご提出");

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
		driver.findElement(By.id("password")).sendKeys(passwordBE());
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
		Assert.assertEquals(driver.findElement(By.cssSelector("a#divNtdFxOpenStatus-button span.ui-selectmenu-status")).getText(),"Open Completed");
		writeData(email);
		
		sleepInSecond(1);
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
	
	}

	@Test
	public void registerEmails() {
		for (int i=0; i<3; i++)
			Register_Corp();
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