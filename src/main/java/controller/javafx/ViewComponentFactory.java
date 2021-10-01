package controller.javafx;

import controller.javafx.components.*;
import javafx.scene.layout.AnchorPane;
import model.Event;

import javax.swing.text.View;
import java.util.Collection;

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

    /***
     * Creates a CalendarPage component
     * @param eventList the list of event to be displayed in the calendar
     * @return the CalendarPage in the form of a CalendarPage
     */
    public static ViewComponent CreateCalendarPage(Collection<Event> eventList) {
        return new CalendarPage(eventList);
    }

}
