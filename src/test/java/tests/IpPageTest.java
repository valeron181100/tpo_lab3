package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AuthAction;
import pages.IpPage;
import pages.ReverseLinksPage;
import utils.PropertyFilesReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IpPageTest {
    private PropertyFilesReader propertyReader;
    private IpPage page;
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private AuthAction authAction;
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
        page = new IpPage(driver);
    }

    @Test
    void showPortTest() throws InterruptedException {
        driver.get(propertyReader.getProperty("ip_page_url"));
        page.getShowPortButton().click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Thread.sleep(1000);
    }

    @AfterAll
    void destroy() {
        driver.quit();
    }
}