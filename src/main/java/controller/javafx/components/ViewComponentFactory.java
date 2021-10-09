package controller.javafx.components;

import controller.javafx.IPageNavigator;
import model.*;

import java.util.Calendar;

/***
 * Factory that creates JavaFX components and returns them as ViewComponents to the caller.
 */
public class ViewComponentFactory {

    /***
     * Creates a top bar for testing purposes.
     * @param nav the navigator object
     * @return the top bar in the form of a ViewComponent
     */
    public static TopBar CreateTestTopBar(IPageNavigator nav) {
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

    /**
     * Creates a ContactCard component.
     * @param contact to create card from
     * @return ContactCard as a ViewComponent
     */
    public static ContactCard CreateContactCard (Contact contact) {
        return new ContactCard(contact);
    }

    public static TagCard CreateTagCard (ITag tag) {
        return new TagCard(tag);
    }

    /***
     * Creates a CalendarPage component
     * @param eventList the list of event to be displayed in the calendar
     * @return the CalendarPage in the form of a CalendarPage
     */
    public static CalendarPage CreateCalendarPage(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        return new CalendarPage(eventList, contactList, tagHandler);
    }

    /***
     * Creates an EventCreationCard component
     * @param eventList list of events, used to send new event to model
     * @param contactList list of contacts, used to browse and add participants
     * @return the EventCreationCard component.
     */
    public static EventCreationCard CreateEventCreationCard(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        return new EventCreationCard(eventList, contactList, tagHandler);
    }

    /***
     * Creates a CalendarEventCard
     * @param event the event to be displayed
     * @return a CalendarEventCard
     */
    public static CalendarEventCard CreateCalendarEventCard(Event event) {
        return new CalendarEventCard(event);
    }

    /***
     * Creates an EditEventCard
     * @param event the event to bind to the card
     * @param contactList a list of contacts to choose from
     * @return the EditEventCard
     */
    public static EditEventCard CreateEditEventCard(Event event, ContactList contactList, EventList eventList, TagHandler tagHandler) {
        return new EditEventCard(event, contactList, eventList, tagHandler);
    }

    public static ContactGrayBox CreateContactGrayBox(){ return new ContactGrayBox();}

}
