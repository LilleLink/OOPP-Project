package model;

import model.search.ISearchable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/***
 * Represents an event occurring at a point in time, past or future, with a name/description and list of contacts/categories it is included in.
 */
public class Event implements ICacheVisitable, ISearchable<String>, IObservable {

    private String name;
    private String address;
    private LocalDateTime dateTime;
    private String description;

    private ITag tag;
    private List<Contact> contacts = new ArrayList<>();
    private List<IObserver> observers = new ArrayList<>();
    private final UUID directoryId;

    /***
     * Creates an event with the given parameters.
     * @param name the name of the event
     * @param address the physical address of the event
     * @param dateTime the date and time of the event
     * @param description the description of the event
     * @param contacts the list containing the IDs of the contacts tagged in the event
     * @param tag the list containing the IDs of the tags tagged on the event
     */
    Event(String name, String address, LocalDateTime dateTime, String description, List<Contact> contacts, ITag tag) {
        this.name = name;
        this.address = address;
        this.dateTime = dateTime;
        this.description = description;
        this.contacts = contacts;
        this.tag = tag;
        this.directoryId = UUID.randomUUID();
    }

    /***
     * Creates an event with the given parameters
     * @param name the name of the event
     * @param date the date of the event
     */
    Event(String name, LocalDateTime date) {
        this.name = name;
        this.dateTime = date;
        this.directoryId = UUID.randomUUID();
    }

    /***
     * Creates an empty event with standard parameters.
     */
    Event() {
        this.name = "Unnamed event";
        this.dateTime = LocalDateTime.now().withSecond(0).withNano(0).withMinute(LocalDateTime.now().getMinute() -
                LocalDateTime.now().getMinute() % 15);
        this.address = "No address";
        this.directoryId = UUID.randomUUID();
    }

    /***
     * Returns whether the event is in the future or past.
     * @return true if in the future, false if in the past.
     */
    public boolean isInFuture() {
        return dateTime.compareTo(LocalDateTime.now()) > 0;
    }

    /***
     * Returns the name of the event
     * @return name of the event
     */
    public String getName() {
        return name;
    }

    /***
     * Sets the name of the event
     * @param name the name of the event
     */
    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    /***
     * Returns the address of the event
     * @return address of the event
     */
    public String getAddress() {

        return address;
    }

    /***
     * Sets the address of the event
     * @param address the address of the event
     */
    public void setAddress(String address) {
        this.address = address;
        notifyObservers();
    }

    /***
     * Returns the date/time object of the event
     * @return the date/time object of the event.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @return The event's directoryId.
     */
    public UUID getDirectoryId() {
        return directoryId;
    }

    /***
     * Sets the date and time of the event
     * @param dateTime the date and time of the event
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        notifyObservers();
    }

    /***
     * Returns the description of the event
     * @return the description of the event
     */
    public String getDescription() {
        return description;
    }

    /***
     * Sets the description of the event
     * @param description the description of the event
     */
    public void setDescription(String description) {
        this.description = description;
        notifyObservers();
    }

    /***
     * Adds a tag to the event
     * @param tag the tag to be added
     */
    public void setTag(ITag tag) {
        this.tag = tag;
        notifyObservers();
    }

    /***
     * Removes a tag from the event
     */
    public void removeTag() {
        tag = null;
        notifyObservers();
    }

    /***
     * Returns tag
     * @return the tag
     */
    public ITag getTag() {
        return this.tag;
    }

    /***
     * Adds a contact to the event
     * @param contact the contact to be added
     */
    public void addContact(Contact contact) {
        if (!contacts.contains(contact)) {
            contacts.add(contact);
        }
        notifyObservers();
    }

    /***
     * Sets the list of participating contacts to the given contact list.
     * @param inputContactList the list of new participating contacts.
     */
    public void setContacts(List<Contact> inputContactList) {
        this.contacts = inputContactList;
        notifyObservers();
    }

    /***
     * Removes a contact from the event
     * @param contact the contact to be removed
     */
    public void removeContact(Contact contact) {
        contacts.remove(contact);
        notifyObservers();
    }

    /***
     * Returns the contact arraylist.
     * @return the contact arraylist.
     */
    public List<Contact> getContacts() {
        return this.contacts;
    }

    @Override
    public String getSearchIdentity() {
        return name.toLowerCase();
    }

    /***
     * The event cache class contains fields which should be saved/loaded to persistent storage.
     */
    public static class EventCache {
        public String name;
        public String address;
        public LocalDateTime dateTime;
        public String description;
        public ITag tag;
        public List<Contact> contacts;
        public UUID directoryId;

        public EventCache() {
        }
    }

    private EventCache getCache() {
        EventCache cache = new EventCache();
        cache.name = this.name;
        cache.address = this.address;
        cache.dateTime = this.dateTime;
        cache.description = this.description;
        cache.tag = this.tag;
        cache.contacts = new ArrayList<>(this.contacts);
        cache.directoryId = this.directoryId;
        return cache;
    }

    public Event(Event.EventCache cache) {
        this.name = cache.name;
        this.address = cache.address;
        this.dateTime = cache.dateTime;
        this.description = cache.description;
        this.tag = cache.tag;
        this.contacts = cache.contacts;
        this.directoryId = cache.directoryId;
    }

    /***
     * Invoke the event cache visitor case.
     */
    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unSubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.onEvent();
        }
    }
}
