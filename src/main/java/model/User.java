package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * The user object holding user specific data.
 */
public class User implements ICacheVisitable {
    private String name;
    private EventList eventList = new EventList();
    private ContactList contactList = new ContactList();
    private TagHandler tagHandler = new TagHandler();
    private UUID uuid = UUID.randomUUID();

    /***
     * Instantiates a user object with the specified name.
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
    }

    /***
     * Sets the name of the user
     * @param name new name of the user
     */
    public void setName(String name) {
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
        List<Event> contactEvents = new ArrayList<>();
        for (Event e : eventList.getList()) {
            if (e.getContacts().contains(contact))
                contactEvents.add(e);
        }
        return contactEvents;
    }

    /***
     * Returns the wrapper object for the contact list
     * @return ContactList wrapper object
     */
    public ContactList getContacts() {
        return contactList;
    }

    /**
     * Returns the tag handler used by the user
     *
     * @return the tag handler
     */
    public TagHandler getTagHandler() {
        return tagHandler;
    }

    public UUID getId() {
        return uuid;
    }

    /***
     * The user cache class contains fields which should be saved/loaded to persistent storage.
     */
    public static class UserCache {
        public String name;
        public List<Event> events;
        public List<Contact> contacts;
        public TagHandler tagHandler;
        public UUID uuid;

        public UserCache() {
        }
    }

    private UserCache getCache() {
        UserCache cache = new UserCache();
        cache.name = this.name;
        cache.events = new ArrayList<>(this.eventList.getList());
        cache.contacts = new ArrayList<>(this.contactList.getList());
        cache.tagHandler = this.tagHandler;
        cache.uuid = this.uuid;
        return cache;
    }

    public User(UserCache cache) {
        this.eventList = new EventList(cache.events);
        this.contactList = new ContactList(cache.contacts);
        this.tagHandler = cache.tagHandler;
        this.name = cache.name;
        this.uuid = cache.uuid;
    }

    /***
     * Invoke the user cache visitor case.
     */
    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }
}
