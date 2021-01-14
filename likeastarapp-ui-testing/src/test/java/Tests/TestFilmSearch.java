package Tests;

import org.junit.Test;

public class TestFilmSearch extends BaseTestClass {

    @Test
    public void searchFilm() {
        filmListPage.search("40 days");
        filmListPage.assertSearchResult("40 Days and 40 Nights");
        filmListPage.navigateToSearchResultDetails();
        filmDetailPage.assertFilmName("40 Days and 40 Nights");
    }
}
