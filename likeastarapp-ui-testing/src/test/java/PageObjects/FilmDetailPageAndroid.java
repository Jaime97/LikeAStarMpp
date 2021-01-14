package PageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class FilmDetailPageAndroid implements FilmDetailPage {

    public FilmDetailPageAndroid(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(id = "titleTextView")
    private MobileElement contactName;

    public void assertFilmName(String expectedName) {
        Assert.assertEquals(expectedName, contactName.getText());
    }
}
