package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AuthAction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import pages.FullAnalyzePage;
import pages.IpPage;
import utils.PropertyFilesReader;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FullAnalyzePageTest {
    private PropertyFilesReader propertyReader;
    private FullAnalyzePage page;
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private AuthAction authAction;
    private static final int DEFAULT_TIMEOUT = 100;

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
        authAction = new AuthAction(driver);
        page = new FullAnalyzePage(driver);
    }

    @Test
    void manyUrlsCheckTest() throws InterruptedException {
        driver.get(propertyReader.getProperty("full_analyze_page_url"));
        authAction.inputLoginRequest(propertyReader.getProperty("login"));
        authAction.inputPasswordRequest(propertyReader.getProperty("password"));
        authAction.getAuthButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//a[@id=\"btn-account\"]")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", page.getManyUrlsArea());
        Thread.sleep(2000);
        page.inputManyUrls(List.of("iphones.ru", "yandex.ru"));
        page.getManyUrlsCheckButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//table[@name='files-list']/tbody/tr/td[5][.='Сформирован']")));
        driver.findElement(By.xpath("//table[@name='files-list']/tbody/tr/td[7]/input")).click();
        Thread.sleep(3000);
    }

    @Test
    void manyUrlsWithFileCheckTest() throws InterruptedException, AWTException {
        driver.get(propertyReader.getProperty("full_analyze_page_url"));
        authAction.inputLoginRequest(propertyReader.getProperty("login"));
        authAction.inputPasswordRequest(propertyReader.getProperty("password"));
        authAction.getAuthButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//a[@id=\"btn-account\"]")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", page.getManyUrlsArea());
        Thread.sleep(2000);
        page.getManyUrlsAddFileButton().click();
        Thread.sleep(2000);
        Robot rb = new Robot();
        StringSelection str = new StringSelection("C:\\Users\\bonda\\IdeaProjects\\tpo_lab3\\src\\test\\resources\\urls.txt");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(2000);
        page.getManyUrlsCheckButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//table[@name='files-list']/tbody/tr/td[5][.='Сформирован']")));
        driver.findElement(By.xpath("//table[@name='files-list']/tbody/tr/td[7]/input")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Thread.sleep(3000);
    }

    @Test
    void clearManyUrlsAreaTest() {
        driver.get(propertyReader.getProperty("full_analyze_page_url"));
        authAction.inputLoginRequest(propertyReader.getProperty("login"));
        authAction.inputPasswordRequest(propertyReader.getProperty("password"));
        authAction.getAuthButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//a[@id=\"btn-account\"]")));
        page.inputManyUrls(List.of("iphones.ru", "yandex.ru"));
        page.getManyUrlsClearButton().click();
        assertEquals("", page.getManyUrlsArea().getText());
    }

    @Test
    void analyzeUrlTest() {
        driver.get(propertyReader.getProperty("full_analyze_page_url"));
        authAction.inputLoginRequest(propertyReader.getProperty("login"));
        authAction.inputPasswordRequest(propertyReader.getProperty("password"));
        authAction.getAuthButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//a[@id=\"btn-account\"]")));
        page.inputSearchRequest(propertyReader.getProperty("test_site"));
        page.getUrlInputButton().click();
        wait.until(visibilityOfElementLocated(By.xpath("//h1[.='Seo анализ сайта iphones.ru']")));
        assertEquals("Анализ сайта iphones.ru и отзывы", driver.getTitle());
    }


    @Test
    void analyzeResultsNavigationTest() {
        analyzeUrlTest();
        page.getResultsNavLinks().forEach(p -> {
            p.click();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

     @Test
     void sendFeedbackTest() throws InterruptedException {
        analyzeUrlTest();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2")));
        page.getFeedbacksIFrame().click();
        driver.switchTo().frame(page.getFeedbacksIFrame());
        Thread.sleep(2000);
        ((JavascriptExecutor)driver).executeScript(
                "document.getElementsByTagName('body')[0].innerHTML = '" + propertyReader.getProperty("lorem_ipsum") + "'");
        driver.switchTo().defaultContent();
        page.getFeedbackSendButton().click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Thread.sleep(2000);
     }



    @AfterAll
    void destroy() {
        driver.quit();
    }
}
