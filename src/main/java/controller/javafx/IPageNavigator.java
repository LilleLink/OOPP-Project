package controller.javafx;

/***
 * An interface that defines what methods a navigator-class should have
 */
public interface IPageNavigator {
    /***
     * Opens the MainPage
     */
    void openMainPage();

    /***
     * Opens the SecondaryPage
     */
    void openSecondaryPage();

    /***
     * Clears the root-element so that a new page can be loaded.
     */
    void clearRootPage();
}
