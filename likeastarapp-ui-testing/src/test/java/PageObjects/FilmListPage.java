package PageObjects;

public interface FilmListPage {

    void search(String name);

    void assertSearchResult(String expectedResult);

    void navigateToSearchResultDetails();

    void clickFavouriteButton();

    void clickFavouriteTab();

}
