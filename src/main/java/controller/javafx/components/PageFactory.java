package controller.javafx.components;

import controller.javafx.IPageNavigator;
import model.ContactList;
import model.EventList;
import model.TagHandler;

/***
 * Factory that creates JavaFX components and returns them as ViewComponents to the caller.
 */
public class PageFactory {

    /***
     * Creates a top bar.
     * @param nav the navigator object
     * @return the top bar in the form of a ViewComponent
     */
    public static ViewComponent createTopBar(IPageNavigator nav) {
        return new TopBar(nav);
    }

    /***
     * Creates a StatisticsPage component
     * @return the StatisticsPage in the form of a ViewComponent
     */
    public static ViewComponent createStatisticsPage(EventList eventList, TagHandler tagHandler) {
        return new StatisticsPage(eventList, tagHandler);
    }

    /**
     * Creates a ContactPage component.
     *
     * @param contactList A list of the contacts to display
     * @return ContactPage as a ViewComponent
     */
    public static ViewComponent createContactPage(ContactList contactList, TagHandler tagHandler, EventList eventList) {
        return new ContactPage(contactList, tagHandler, eventList);
    }

    /***
     * Creates a CalendarPage component
     * @param eventList the list of event to be displayed in the calendar
     * @return the CalendarPage in the form of a CalendarPage
     */
    public static ViewComponent createCalendarPage(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        return new CalendarPage(eventList, contactList, tagHandler);
    }
}
