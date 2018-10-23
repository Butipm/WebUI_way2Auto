import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.xalan.xsltc.compiler.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.*;

import com.gargoylesoftware.htmlunit.javascript.host.Screen;
import com.opencsv.CSVReader;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Test2 {
	static WebDriver driver;
	static CSVReader reader;
	static String CSV_File ,txt,FirstName,LastName,UserName,Password,Customer,Role,Email,Cellphone,browser,URLval, TestDataval, ImageP ;
	static String[] cell;
	static Boolean found,found2,expected=true;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void beforesuite() 
	{
		extent = new ExtentReports("C:\\Users\\PC\\eclipse-workspace\\Assessment\\Test_Way2Auto\\Reports\\Web.html",true);
		extent.loadConfig( new File("C:\\Users\\PC\\eclipse-workspace\\Assessment\\Test_Way2Auto\\Lib\\Config\\extent-config.xml"));
	} 
	
	@BeforeMethod
	public void beforemethod()
	{
	test = extent.startTest("Testing Web UI");
	test.assignAuthor("Itu Assessment");
	test.assignCategory("Web UI Tests");
	test.log(LogStatus.INFO, "Test Began Running");
	}
//public static void main(String[] args) throws IOException {
	@BeforeTest
	public void TestAssessment_Config() throws IOException {
		setConfigFile();
		setBrowser();
		setBrowserConfig();
		setCSVPath();
		setCSVConfig();
	}
	@Test
	public static void BrowserOpen() {
		
		driver.get(URLval);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Navigated to : "+URLval );
	}
	@Test
	public static void ValidateUserTabel() {
		
		found = driver.findElement(By.cssSelector("table.smart-table")).isDisplayed();
		Assert.assertEquals(found,expected,"User Table Exist");	
		if(found == true) {
			test.log(LogStatus.PASS, "User Table Exist" );
			}else {
				test.log(LogStatus.FAIL, "User Table Exist Not Found");
			}
	}
	

@Test
public static void setAddUser() throws IOException {
	while((cell=reader.readNext())!=null){

		for (int i=0;i<1;i++) {

			FirstName = cell[i];
			LastName =cell[i+1];
			UserName = cell[i+2];
			Password = cell[i+3];
			Customer = cell[i+4];
			Role = cell[i+5];
			Email = cell[i+6];
			Cellphone = cell[i+7];

			
			driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td/button")).click();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[1]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[1]/td[2]/input")).sendKeys(FirstName);
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[2]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(LastName);
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[3]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[3]/td[2]/input")).sendKeys(UserName);
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[4]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[4]/td[2]/input")).sendKeys(Password);

		

			if (Customer == "Company AAA"){
				driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[1]/input")).click();
			}
		
			
			else{ 
				driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[2]/input")).click();
			}

			Select Sel1=new Select(driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[6]/td[2]/select")));
			Sel1.selectByVisibleText(Role);

			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[7]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[7]/td[2]/input")).sendKeys(Email);
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[8]/td[2]/input")).clear();
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[8]/td[2]/input")).sendKeys(Cellphone);

			setCheckUserName();

			
			if (found2 == false) {
	
				driver.findElement(By.xpath("/html/body/div[3]/div[3]/button[2]")).click();
				test.log(LogStatus.PASS, "User is new and unique " );
			}else {
			
				driver.findElement(By.xpath("/html/body/div[3]/div[3]/button[1]")).click();
				test.log(LogStatus.WARNING, "User name already in use and will not be created again.NB please use a unique user name");

			}

		}
	}
}

	@AfterTest
	public void TestAssessment_close() throws IOException {	
		setquitTest();	
	}
	
	@AfterMethod
	public void aftermethod() throws IOException{
		test.log(LogStatus.PASS, "Test execution finished");
		extent.endTest(test);
	}
	@AfterSuite
	public void afterSuite() {
		extent.flush();
		extent.close();
		
	}
		
		

	
	public static void setConfigFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		
		input = new FileInputStream("C:\\Users\\PC\\eclipse-workspace\\way2automate\\Config\\Configuration.properties");

		prop.load(input);
		TestDataval = prop.getProperty("TestData");
		URLval = prop.getProperty("URL");
		

	}
	public static void setBrowser(){
		
		browser = "Firefox";
		
	}
	
	
	public static void setBrowserConfig(){
		
		
		if(browser.contains("Firefox")) {
		System.setProperty("webdriver.gecko.driver","C:\\Users\\PC\\eclipse-workspace\\Assessment\\Test_Way2Auto\\Lib\\Gecko\\geckodriver.exe");
		driver = new FirefoxDriver();
		}
		
		if(browser.contains("Chrome")) {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		driver = new ChromeDriver();
		}
	}




	public static void setCSVPath() throws IOException {

		
		
		CSV_File = TestDataval;//"C:\\Users\\PC\\eclipse-workspace\\way2automate\\Test_Data\\input\\Test_data.csv";	
	}

	public static  void setCSVConfig() throws FileNotFoundException {
		reader = new CSVReader(new FileReader(CSV_File));
	}

	public static void setCheckUserName() {
	
		txt = driver.findElement(By.cssSelector("table.smart-table")).getText();
		found2 =txt.contains(UserName);
			

	}

	
	
	public static void setquitTest(){
		driver.quit();
	}
}
