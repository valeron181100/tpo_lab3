package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Data
public class ReverseLinksPage {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div[1]/form/input")
    private WebElement urlInput;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div[1]/form/button")
    private WebElement urlInputButton;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div[2]/i/i/input[2]")
    private WebElement showMoreButton;

    @FindBy(xpath = "/html/body/nav/div/div[2]/ul/li/a")
    private List<WebElement> navbarLinks;

    public ReverseLinksPage(WebDriver driver) {
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
