package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/***
 * The user object holding user specific data.
 */
public class User implements ICacheVisitable {
    private String name;
    private EventList eventList = new EventList();
    private ContactList contactList = new ContactList();
    private TagHandler tagHandler = new TagHandler();

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
     * Returns the users list of events
     * @return the list of events
     */
    public EventList getEvents() {
        return eventList;
    }

    /***
     * Returns a list of events that the given contact is tagged in
     * @param contact the subject contact
     * @return an arraylist of the events the contact is a part of
     */
    public List<Event> getContactEvents(Contact contact) {
        List<Event> contactEvents= new ArrayList<>();
        for (Event e: eventList.getList()) {
            if (e.getContacts().contains(contact))
                contactEvents.add(e);
        }
        return contactEvents;
    }

    /***
     * Returns the wrapper object for the contact list
     * @return ContactList wrapper object
     */
    public ContactList getContacts(){
        return contactList;
    }

    /***
     * Creates a tag given the specified name
     * @param name the name of the tag
     * @return a tag with the specified name
     * @throws NameNotAvailableException if the name is already taken
     */
    public ITag createTag(String name) throws NameNotAvailableException{
        return tagHandler.createTag(name);
    }

    /***
     * Returns a list of all tags
     * @return a list of all tags
     */
    public List<ITag> getTags(){
        return tagHandler.getTags();
    }

    /***
     * Gets a tag given the specified name
     * @param name name of the tag saught after
     * @return the tag with the given name
     * @throws TagNotFoundException if the tag with the given name cannot be found
     */
    public ITag getTag(String name) throws TagNotFoundException{
        return tagHandler.getTag(name);
    }

    /***
     * Sets the color of a tag to the specified color
     * @param tag the subject tag
     * @param color the specified color
     * @return boolean depending on operation result
     */
    public boolean setColor(ITag tag, String color) {
        return tagHandler.setColor(tag, color);
    }

    /***
     * Renames a given tag to the specified name
     * @param tag the tag to be renamed
     * @param newName the new name of the tag
     * @throws NameNotAvailableException if the name is already taken
     */
    public void renameTag(ITag tag, String newName) throws NameNotAvailableException {
        tagHandler.rename(tag, newName);
    }


    /***
     * The user cache class contains fields which should be saved/loaded to persistent storage.
     */
    public static class UserCache {
        public String name;
        public List<Event> events;
        public List<Contact> contacts;
        public TagHandler tagHandler;

        public UserCache() {}
    }

    private UserCache getCache() {
        UserCache cache = new UserCache();
        cache.name = this.name;
        cache.events = new ArrayList<>(this.eventList.getList());
        cache.contacts = new ArrayList<>(this.contactList.getList());
        cache.tagHandler = this.tagHandler;
        return cache;
    }

    public User(UserCache cache) {
        this.eventList = new EventList(cache.events);
        this.contactList = new ContactList(cache.contacts);
        this.tagHandler = cache.tagHandler;
        this.name = cache.name;
    }

    /***
     * Invoke the user cache visitor case.
     */
    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }
}
