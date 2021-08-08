package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Data
public class FullAnalyzePage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/main/div[2]/div[1]/div/form/div[1]/input")
    private WebElement urlInput;

    @FindBy(xpath = "/html/body/main/div[2]/div[1]/div/form/div[1]/button")
    private WebElement urlInputButton;

    @FindBy(xpath = "//textarea")
    private WebElement manyUrlsArea;

    @FindBy(xpath = "/html/body/main/div[2]/div[3]/input[2]")
    private WebElement manyUrlsCheckButton;

    @FindBy(xpath = "/html/body/main/div[2]/div[3]/input[3]")
    private WebElement manyUrlsAddFileButton;

    @FindBy(xpath = "/html/body/main/div[2]/div[3]/input[4]")
    private WebElement manyUrlsClearButton;

    @FindBy(xpath = "//ul[@class='menu_trust']/li")
    private List<WebElement> resultsNavLinks;

    @FindBy(xpath = "//iframe[1]")
    private WebElement feedbacksIFrame;

    @FindBy(xpath = "//*[@id=\"form-reviews\"]/table/tbody/tr[5]/td/input")
    private WebElement feedbackSendButton;

    public FullAnalyzePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void click(WebElement element) {
        element.click();
    }

    public void inputSearchRequest(String req) {
        urlInput.sendKeys(req);
    }

    public void inputManyUrls(List<String> urls) {
        StringBuilder builder = new StringBuilder();
        urls.forEach(p -> builder.append(p + "\n"));
        manyUrlsArea.sendKeys(builder.toString());
    }
}
