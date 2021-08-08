package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.PropertyFilesReader;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageTest {
    private PropertyFilesReader propertyReader;
    private MainPage mainPage;
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private static final int DEFAULT_TIMEOUT = 10;

    @BeforeAll
    void init() {
        propertyReader = new PropertyFilesReader();
        propertyReader.setProp("driver.properties");

        System.setProperty("webdriver.chrome.driver", propertyReader.getProperty("chrome_driver"));
        driver = new ChromeDriver();

        propertyReader.setProp("config.properties");
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

        driver.manage().window().maximize();

        actions = new Actions(driver);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Check if input trust index works correctly")
    void analiseTest() throws InterruptedException {
        driver.get(propertyReader.getProperty("main_page_url"));
        mainPage.inputSearchRequest(propertyReader.getProperty("test_site"));
        mainPage.click(mainPage.getUrlInputButton());
        WebElement foundElement =
                wait.until(visibilityOfElementLocated(By.xpath(propertyReader.getProperty("xt_history_xpath"))));
        mainPage.getAnaliseLinks().forEach(p -> {
            actions.moveToElement(p);
            actions.perform();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.click();
        });
        wait.until(visibilityOfElementLocated(By.xpath("//h1[.='ИКС сайта']")));
        Thread.sleep(1000);
        assertEquals("Проверка Яндекс ИКС сайта Бесплатно (также проверка сайтов списком)", driver.getTitle());
    }

    @Test
    @DisplayName("Check if navigation on main page works correctly")
    void navigationTests() throws InterruptedException {
        driver.get(propertyReader.getProperty("main_page_url"));
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_xt"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_analise"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_blog"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_faq"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_recs"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_feedbacks"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_api"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_contacts"))).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(propertyReader.getProperty("navbar_ads"))).click();
        Thread.sleep(1000);
    }

    @AfterAll
    void destroy() {
        driver.quit();
    }
}
