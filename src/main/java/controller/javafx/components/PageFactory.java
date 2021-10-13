package controller.javafx.components;

import controller.javafx.IPageNavigator;
import model.*;

import java.util.Calendar;

/***
 * Factory that creates JavaFX components and returns them as ViewComponents to the caller.
 */
public class PageFactory {

    /***
     * Creates a top bar for testing purposes.
     * @param nav the navigator object
     * @return the top bar in the form of a ViewComponent
     */
    public static ViewComponent CreateTestTopBar(IPageNavigator nav) {
        return new TopBar(nav);
    }

    /***
     * Creates a MainPage component.
     * @return the MainPage in the form of a ViewComponent
     */
    public static MainPage CreateMainPage() {
        return new MainPage();
    }

    /***
     * Creates a SecondaryPage component
     * @return the SecondaryPage in the form of a ViewComponent
     */
    public static SecondaryPage CreateSecondaryPage () {
        return new SecondaryPage();
    }

    /**
     * Creates a ContactPage component.
     * @param contactList A list of the contacts to display
     * @return ContactPage as a ViewComponent
     */
    public static ContactPage CreateContactPage (ContactList contactList) {
        return new ContactPage(contactList);
    }

    /***
     * Creates a CalendarPage component
     * @param eventList the list of event to be displayed in the calendar
     * @return the CalendarPage in the form of a CalendarPage
     */
    public static CalendarPage CreateCalendarPage(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        return new CalendarPage(eventList, contactList, tagHandler);
    }
}
