package Tests;

import org.junit.Test;

public class TestFilmFavourite extends BaseTestClass {

    @Test
    public void addFilmToFavourite() {
        filmListPage.search("40 Days");
        filmListPage.assertSearchResult("40 Days and 40 Nights");
        filmListPage.clickFavouriteButton();
        filmListPage.clickFavouriteTab();
        filmListPage.assertSearchResult("40 Days and 40 Nights");
        filmListPage.clickFavouriteButton();
    }

}
