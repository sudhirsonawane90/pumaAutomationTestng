package pumaAutomation1;

import org.testng.annotations.Test;

import net.bytebuddy.dynamic.scaffold.TypeInitializer.None;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

public class NewTest {
	WebDriver driver = new ChromeDriver();
	
	public static void verifyLinks(String LinkURL) {
		try {
			URL url = new URL(LinkURL);
			HttpsURLConnection httpURLConnect = (HttpsURLConnection)url.openConnection();
			httpURLConnect.setConnectTimeout(5000);
			httpURLConnect.connect();
			if(httpURLConnect.getResponseCode() >= 400) {
				System.out.println(LinkURL+ " - " +httpURLConnect.getResponseMessage() + " is broken.");
			}else {
				System.out.println(LinkURL+ " - " +httpURLConnect.getResponseMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
  @Test(description = "Open Puma - through Google Search", priority = 1)
  public void GoogleSearch() {
	  //Search "Puma shorts for man" in google
	  driver.findElement(By.name("q")).sendKeys("puma shorts for men");
	  driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Scroll down
	  JavascriptExecutor Js = (JavascriptExecutor) driver;
	  Js.executeScript("window.scrollBy(0,250)");
	  //Select link through search result
	  driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div[1]/a/h3")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
  }
  
  @Test(description = "Check broken images and links on the web page", priority = 2, enabled = false)
  public void BrokLinksAndImages() {
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Check broken Images on the Puma home page
	  try {
		int iBrokenImageCount = 0;
		List<WebElement> img_list = driver.findElements(By.tagName("img"));
		System.out.println("Total images on the web page is : " +img_list.size());
		for (WebElement img : img_list) {
			if (img != null) {
				if(img.getAttribute("naturalWidth").equals("0")) {
					System.out.println(img.getAttribute("outerHTML") + "is Broken");
					iBrokenImageCount++;
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Broken Links on the web page - Not verifyLinks function is already created in same class
	  List<WebElement> links = driver.findElements(By.tagName("a"));
	  System.out.println("Total Link's on the web page is - " +links.size());
	  for (int i = 0; i < links.size(); i++) {
		WebElement E1 = links.get(i);
		String url = E1.getAttribute("href");
		verifyLinks(url);
	}
  }
  
  @Test(description = "Mouse hover on 7 inch and click on Image", priority = 3)
  public void MouseHoverImageClick() {
	  //Mouse hover on 7 inch and click on Image
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  WebElement img7inch = driver.findElement(By.xpath("//img[@alt='Mens Shorts knee shorts']"));
	  Actions mouseHover = new Actions(driver);
	  mouseHover.moveToElement(img7inch).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }
  
  @Test(description = "Click on Price and Select less than 1000", priority = 4)
  public void clickOnPriceSelectLessthan1000() throws InterruptedException {
	  //Click on Price
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  WebElement price = driver.findElement(By.xpath("(//button[@class='btn refinement-title ']//div)[2]"));
	  Actions mouseHover = new Actions(driver);
	  mouseHover.moveToElement(price).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  WebElement clickPrice = driver.findElement(By.xpath("(//label[@class='custom-control mode-radio']//span)[2]"));
	  clickPrice.click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //close the price element
	  Thread.sleep(5000);
	  WebElement priceClose = driver.findElement(By.xpath("(//div[@class='refinement-show']//button)[3]"));
	  priceClose.click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
  
  @Test(description = "Select size L and close size pop up", priority = 5)
  public void selectSizeL() throws InterruptedException {
	  //Select size L
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  WebElement size = driver.findElement(By.xpath("(//button[@class='btn refinement-title ']//div)[3]"));
	  Actions sizeClick = new Actions(driver);
	  sizeClick.moveToElement(size).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  WebElement sizeL = driver.findElement(By.xpath("//span[text()[normalize-space()='L']]"));
	  sizeL.click();
	  Thread.sleep(5000);
	  //Close size pop up
	  WebElement closeSize = driver.findElement(By.xpath("//div[@id='product-search-results']/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[5]/div[1]/button[1]"));
	  closeSize.click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
  
  @Test(description = "Sort - Price Low to High and Scroll down", priority = 6)
  public void sortBy_PriceLowtoHigh() throws InterruptedException, IOException {
	  //Click on Sort
	  WebElement sort = driver.findElement(By.xpath("(//select[@name='sort-order'])[2]")); 
	  Select sortBy = new Select(sort);
	  sortBy.selectByIndex(2);
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Scrolling down little bit
	  JavascriptExecutor Js1 = (JavascriptExecutor) driver;
	  Js1.executeScript("window.scrollBy(0,200)");
	  //Taking screenshot
	  File screenshot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  FileUtils.copyFile(screenshot1, new File("F:\\Study\\Screenshots\\Puma\\Step10.png"));
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
  
  @Test(description = "Select one result & take screenshot in excel", priority = 7)
  public void seectResult() throws InterruptedException, IOException {
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  Thread.sleep(5000);
	  //Click on the searched result image 
	  driver.findElement(By.xpath("//img[@itemprop='image']")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Take current price
	  String CurrentPrice = driver.findElement(By.xpath("//span[@class='value']")).getText();
	  System.out.println("Current Price of this Item is : " +CurrentPrice);
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Take Original Price
	  String OriginalPrice = driver.findElement(By.xpath("(//span[@class='value'])[2]")).getText();
	  System.out.println("Origional price of this Item is : " +OriginalPrice);
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Take full page screenshot
	  Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
	  ImageIO.write(screenshot.getImage(),"PNG", new File("F:\\Study\\Screenshots\\Puma\\FullImageStep12.png"));
	}
  
  @Test(description = "Scroll up, Check size guide and take L size guide", priority = 8)
  public void sizeGuideLsize() throws InterruptedException, IOException {
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Scroll up
	  JavascriptExecutor Js2 = (JavascriptExecutor) driver;
	  Js2.executeScript("window.scrollBy(1000,200)");
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Click on the size guide
	  Thread.sleep(5000);
	  WebElement sizeGuide = driver.findElement(By.xpath("//*[@id=\"attributes-container-size\"]/div[1]/button"));
	  Actions guideClick = new Actions(driver);
	  guideClick.moveToElement(sizeGuide).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Pasting L size description
	  WebElement LSize = driver.findElement(By.xpath("//div[@id='attributes-container-size']/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[5]"));
	  String sizeL = LSize.getText();
	  System.out.println(sizeL);
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //clicking on Close icon of the Size guide table
	  //WebElement closeSizeGuide = driver.findElement(By.xpath("//div[@id='attributes-container-size']/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[5]"));
	  WebDriverWait closeGuide = new WebDriverWait(driver, 10);
	  closeGuide.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='icon close']")));
	  WebElement closeSizeGuide = driver.findElement(By.xpath("//div[@class='icon close']"));
	  Actions closeIcon = new Actions(driver);
	  closeIcon.moveToElement(closeSizeGuide).click().build().perform();
	  Thread.sleep(5000);
	}
  
  @Test(description = "Select size M, Add to Cart, Screenshot", priority = 9)
  public void selectsizeMaddToCart() throws InterruptedException, IOException {
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  Thread.sleep(5000);
	  //Get current URL
	  String currentURL = driver.getCurrentUrl();
	  System.out.println("Current URL of the web page is : " +currentURL);
	  //Select size M
	  WebElement selectSizeM = driver.findElement(By.linkText("M"));
	  Actions sizeM = new Actions(driver);
	  sizeM.moveToElement(selectSizeM).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Click on Add to Cart
	  WebElement addToCart = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[2]/div[1]/div[2]/div/div[6]/div/div[2]/button"));
	  Actions addCart = new Actions(driver);
	  addCart.moveToElement(addToCart).click().build().perform();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  Thread.sleep(5000);
	  WebElement viewCart = driver.findElement(By.xpath("//button[text()='Add to Cart']"));
	  Actions cartView = new Actions(driver);
	  cartView.moveToElement(viewCart).click().build().perform();
	  Thread.sleep(5000);
	//Taking screenshot of Add to cart
	  File screenshot2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  FileUtils.copyFile(screenshot2, new File("F:\\Study\\Screenshots\\Puma\\AddToCartStep19.png"));
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	//Click on View cart button
	  /*WebElement viewCartClick = driver.findElement(By.xpath("//span[text()='View Cart']"));
	  Actions cartViewclick = new Actions(driver);
	  cartViewclick.moveToElement(viewCartClick).click().build().perform();
	  Thread.sleep(3000);*/
	}
  
  @BeforeClass
  public void beforeClass() {
	  
	  System.setProperty("webdriver.chrome.driver", "F:\\Study\\chromedriver_win32 (90)\\chromedriver.exe");
	  driver.navigate().to("https://www.google.co.in/");
	  
  }

  
  
  
  @AfterClass
  public void afterClass() {
	  driver.close();
	  driver.quit();
  }

  @BeforeTest
  public void beforeTest() {
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

}
