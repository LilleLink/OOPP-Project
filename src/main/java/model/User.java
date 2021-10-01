package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User {
    //TODO fix javadoc, rushing to get runnable version W3
    private String name;
    private final Collection<Event> eventList = new ArrayList<>();
    private final List<Contact> contactList = new ArrayList<>();
    private final TagHandler tagHandler = new TagHandler();

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
    public void setName(String name){
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
        return eventList.add(event);
    }

    /***
     * Removes an event from the eventlist
     * @param event the event to be removed
     * @return true if the operation was successful, false if not
     */
    boolean removeEvent(Event event) {
        return eventList.remove(event);
    }

    /***
     * Returns the users list of events
     * @return the list of events
     */
    public Collection<Event> getEventList() {
        return eventList;
    }

    /***
     * Returns a list of events that the given contact is tagged in
     * @param contact the subject contact
     * @return an arraylist of the events the contact is a part of
     */
    public List<Event> getContactEvents(Contact contact) {
        List<Event> contactEvents= new ArrayList<>();
        for (Event e: eventList) {
            if (e.getContacts().contains(contact))
                contactEvents.add(e);
        }
        return contactEvents;
    }

    public void addContact(String name){
        contactList.add(new Contact(name));
    }

    public void removeContact(Contact contact){
        contactList.remove(contact);
    }

    public List<Contact> getContacts(){
        return contactList;
    }

    public ITag createTag(String name) throws NameNotAvailableException{
        return tagHandler.createTag(name);
    }

    public List<ITag> getTags(){
        return tagHandler.getTags();
    }

    public ITag getTag(String name) throws TagNotFoundException{
        return tagHandler.getTag(name);
    }

    public boolean setColor(ITag tag, String color) {
        return tagHandler.setColor(tag, color);
    }

    public void renameTag(ITag tag, String newName) throws NameNotAvailableException {
        tagHandler.rename(tag, newName);

    }
}
