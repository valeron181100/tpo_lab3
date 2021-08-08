package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainPage {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/main/div[2]/div[3]/div/form/div/input")
    private WebElement urlInput;

    @FindBy(xpath = "/html/body/main/div[2]/div[3]/div/form/div/button")
    private WebElement urlInputButton;

    @FindBy(xpath = "//ul[@class='menu_a']/li")
    private List<WebElement> analiseLinks;

    @FindBy(xpath = "/html/body/nav/div/div[2]/ul/li/a")
    private List<WebElement> navbarLinks;


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void click(WebElement element) {
        element.click();
    }

    public void inputSearchRequest(String req) {
        urlInput.sendKeys(req);
    }
}
