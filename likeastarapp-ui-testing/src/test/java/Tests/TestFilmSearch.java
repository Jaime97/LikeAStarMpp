package Tests;

import org.junit.Test;

public class TestFilmSearch extends BaseTestClass {

    @Test
    public void searchFilm() {
        filmListPage.search("jitney");
        filmListPage.assertSearchResult("A Jitney Elopement");
        filmListPage.navigateToSearchResultDetails();
        filmDetailPage.assertFilmName("A Jitney Elopement");
    }
}
