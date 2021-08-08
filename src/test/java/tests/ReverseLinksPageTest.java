package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AuthAction;
import pages.MainPage;
import pages.ReverseLinksPage;
import utils.PropertyFilesReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReverseLinksPageTest {

    private PropertyFilesReader propertyReader;
    private ReverseLinksPage page;
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
        page = new ReverseLinksPage(driver);
        authAction = new AuthAction(driver);
    }

    @Test
    void searchTest() {
        driver.get(propertyReader.getProperty("reverse_links_page_url"));
        authAction.inputLoginRequest(propertyReader.getProperty("login"));
        authAction.inputPasswordRequest(propertyReader.getProperty("password"));
        authAction.getAuthButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//a[@id=\"btn-account\"]")));
        page.inputSearchRequest(propertyReader.getProperty("test_site"));
        page.getUrlInputButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//h1[.='Список обратных ссылок на сайт iphones.ru']")));
        assertEquals("Обратные ссылки на сайт iphones.ru", driver.getTitle());
    }

    @Test
    void showMoreTest() {
        searchTest();
        page.getShowMoreButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[2]/div[2]/i/i/div[1]/table[1]/tbody/tr[665]")));
        WebElement lastTR = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/i/i/div[1]/table[1]/tbody/tr[665]"));
        actions.moveToElement(lastTR);
        actions.perform();
    }

    @AfterAll
    void destroy() {
        driver.quit();
    }

}