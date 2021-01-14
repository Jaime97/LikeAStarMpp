package PageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilmListPageIOS implements FilmListPage {

    public FilmListPageIOS(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(id = "searchField")
    private MobileElement searchField;

    @FindBy(id = "cellTitle")
    private MobileElement firstSearchResultName;

    @FindBy(id = "favouriteButton")
    private MobileElement firstSearchFavouriteButton;

    @FindBy(id = "Favourites")
    private MobileElement favouriteTab;

    public void search(String name) {
        searchField.click();
        searchField.sendKeys(name);
    }

    public void assertSearchResult(String expectedResult) {
        Assert.assertEquals(expectedResult, firstSearchResultName.getText());
    }

    public void navigateToSearchResultDetails() {
        firstSearchResultName.click();
    }

    @Override
    public void clickFavouriteButton() {
        firstSearchFavouriteButton.click();
    }

    @Override
    public void clickFavouriteTab() {
        favouriteTab.click();
    }

}
