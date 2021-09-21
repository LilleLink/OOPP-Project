package model;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User implements IPRMVisitable{
    //TODO fix javadoc, rushing to get runnable version W3
    private String name;
    private final Collection<Event> events = new ArrayList<>();
    private final TagHandler tagHandler = new TagHandler();
    private final Collection<Contact> contacts = new ArrayList<>();

    /***
     * Instantiates a user object with the specified name.
     * @param name the name of the user
     */
    public User(String name){
        this.name = name;
    }

    /***
     * Sets the name of the user
     * @param name new name of the user
     */
    void setName(String name){
        this.name = name;
    }

    /***
     * Returns the name of the user
     * @return a string containing the name of the users
     */
    public String getName() {
        return this.name;
    }

    /***
     * Adds an event to the event list
     * @param event the event to be added
     * @return true if the operation was successful, false if not
     */
    boolean addEvent(Event event) {
        return events.add(event);
    }

    /***
     * Removes an event from the eventlist
     * @param event the event to be removed
     * @return true if the operation was successful, false if not
     */
    boolean removeEvent(Event event) {
        return events.remove(event);
    }

    /***
     * Returns the users list of events
     * @return the list of events
     */
    public Collection<Event> getEventList() {
        return events;
    }

    /***
     * Returns a list of events that the given contact is tagged in
     * @param contact the subject contact
     * @return an arraylist of the events the contact is a part of
     */
    public List<Event> getContactEvents(Contact contact) {
        List<Event> contactEvents= new ArrayList<>();
        for (Event e: events) {
            if (e.getContacts().contains(contact))
                contactEvents.add(e);
        }
        return contactEvents;
    }

    @Override
    public <E, T> T accept(IPRMVisitor<E, T> visitor, E env) {
        return visitor.visitUser(this, env);
    }
}
