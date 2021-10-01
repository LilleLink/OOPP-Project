package controller.javafx;

import controller.javafx.components.MainPage;
import controller.javafx.components.SecondaryPage;
import controller.javafx.components.TestTopBar;
import controller.javafx.components.ViewComponent;
import javafx.scene.layout.AnchorPane;

/***
 * Factory that creates JavaFX components and returns them as ViewComponents to the caller.
 */
public class ViewComponentFactory {

    /***
     * Creates a top bar for testing purposes.
     * @param nav the navigator object
     * @return the top bar in the form of a ViewComponent
     */
    public static ViewComponent CreateTestTopBar(IPageNavigator nav) {
        return new TestTopBar(nav);
    }

    /***
     * Creates a MainPage component.
     * @return the MainPage in the form of a ViewComponent
     */
    public static ViewComponent CreateMainPage() {
        return new MainPage();
    }

    /***
     * Creates a SecondaryPage component
     * @return the SecondaryPage in the form of a ViewComponent
     */
    public static ViewComponent CreateSecondaryPage () {
        return new SecondaryPage();
    }

}
