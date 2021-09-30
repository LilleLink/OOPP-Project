package controller.javafx;

import controller.javafx.components.MainPage;
import controller.javafx.components.SecondaryPage;
import controller.javafx.components.TestTopBar;
import controller.javafx.components.ViewComponent;

public class ViewComponentFactory {

    public static ViewComponent CreateTestTopBar(IPageNavigator nav) {
        return new TestTopBar(nav);
    }

    public static ViewComponent CreateMainPage() {
        return new MainPage();
    }

    public static ViewComponent CreateSecondaryPage () {
        return new SecondaryPage();
    }

}
