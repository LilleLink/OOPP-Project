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
     * Opens the StatisticsPage
     */
    void openStatisticsPage();

    /**
     * Opens the ContactPage
     */
    void openContactPage();

    /***
     * Opens the CalendarPage
     */
    void openCalendarPage();

    /**
     * Opens the notifications page.
     */
    void openNotificationPage();

    /***
     * Clears the root-element so that a new page can be loaded.
     */
    void clearRootPage();


}
