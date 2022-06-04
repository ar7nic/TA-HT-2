import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");

    }

    @Test
    public void checkThatUrlContainsSearchWord() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("iPhone 13");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        assertTrue(driver.getCurrentUrl().contains("query=iPhone"));
    }

    @Test
    public void checkMoreElements() {
        driver.findElement(By.xpath("//li[@class='parent js_sidebar-item']/a[@href='https://avic.ua/televizory-i-aksessuary']")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[@href='https://avic.ua/televizoryi']")).click();


        driver.findElement(By.xpath("//a[@class='btn-see-more js_show_more']")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='prod-cart height']"),24));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='prod-cart height']"));
        int count = elements.size();
        assertEquals(count,24);

    }
    @Test
    public void producerFilter() {
        driver.findElement(By.xpath("//li[@class='parent js_sidebar-item']/a[@href='https://avic.ua/televizory-i-aksessuary']")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[@href='https://avic.ua/televizoryi']")).click();
        driver.findElement(By.xpath("//label[@for='fltr-proizvoditel-samsung']")).click();
        List<WebElement> filterPage = driver.findElements(By.className("prod-cart__descr"));
        for (WebElement prodName: filterPage) {
            assertTrue(prodName.getText().contains("Samsung"));
            System.out.println(prodName);
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
