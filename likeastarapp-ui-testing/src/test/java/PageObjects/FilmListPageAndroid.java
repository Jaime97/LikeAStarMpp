package PageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilmListPageAndroid implements FilmListPage {

    public FilmListPageAndroid(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(id = "search_bar")
    private MobileElement searchField;

    @FindBy(id = "titleTextView")
    private MobileElement firstSearchResultName;

    @FindBy(id = "favouriteButton")
    private MobileElement firstSearchFavouriteButton;

    @FindBy(xpath = "//android.widget.LinearLayout[@content-desc=\"Favourites\"]")
    private MobileElement favouriteTab;

    @FindBy(id = "filmListRecyclerView")
    private MobileElement filmList;


    public void search(String name) {
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
