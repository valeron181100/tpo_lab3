package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.UpdatesInformerPage;
import utils.PropertyFilesReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdatesInformerPageTest {
    private PropertyFilesReader propertyReader;
    private UpdatesInformerPage page;
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
        page = new UpdatesInformerPage(driver);
    }

    @Test
    @DisplayName("Check if getting script works correctly")
    void getScriptTest() throws InterruptedException {
        driver.get(propertyReader.getProperty("updates_informer_page_url"));
        String script = page.getInformerScript();
        assertEquals("<script type=\"text/javascript\" src=\"https://xtool.ru/updates/i/?c=1&w=250&s=4\"></script>", script);
    }

    @AfterAll
    void destroy() {
        driver.quit();
    }
}
