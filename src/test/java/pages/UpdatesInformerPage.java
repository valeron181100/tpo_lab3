package pages;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Data
public class UpdatesInformerPage {
    private WebDriver driver;

    @FindBy(xpath = "//textarea")
    private WebElement scriptArea;


    public UpdatesInformerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void click(WebElement element) {
        element.click();
    }

    public String getInformerScript() {
        return scriptArea.getText();
    }
}
