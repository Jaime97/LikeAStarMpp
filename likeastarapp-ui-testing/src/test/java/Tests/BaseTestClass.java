package Tests;

import AppiumSupport.AppiumBaseClass;
import PageObjects.*;
import org.junit.After;
import org.junit.Before;

import AppiumSupport.AppiumController;

public class BaseTestClass extends AppiumBaseClass{
    FilmListPage filmListPage;
    FilmDetailPage filmDetailPage;

    @Before
    public void setUp() throws Exception {
        AppiumController.instance.start();
        switch (AppiumController.executionOS) {
            case ANDROID:
                filmListPage = new FilmListPageAndroid(driver());
                filmDetailPage = new FilmDetailPageAndroid(driver());
                break;
            case IOS:
                filmListPage = new FilmListPageIOS(driver());
                filmDetailPage = new FilmDetailPageIOS(driver());
                break;
        }
    }

    @After
    public void tearDown() {
        AppiumController.instance.stop();
    }
}
