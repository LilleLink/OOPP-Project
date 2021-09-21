package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/***
 * Represents an event occurring at a point in time, past or future, with a name/description and list of contacts/categories it is included in.
 */
public class Event implements IPRMVisitable{

    private String name;
    private Address address = new Address("");
    private LocalDateTime dateTime;
    private String description;

    private Tag tag;
    private ArrayList<Contact> contacts = new ArrayList<>();

    /***
     * Creates an event with the given parameters.
     * @param name the name of the event
     * @param address the physical address of the event
     * @param dateTime the date and time of the event
     * @param description the description of the event
     * @param contacts the list containing the IDs of the contacts tagged in the event
     * @param tag the list containing the IDs of the tags tagged on the event
     */
    Event(String name, String address, LocalDateTime dateTime, String description, ArrayList<Contact> contacts, Tag tag) {
        this.name = name;
        this.address = new Address(address);
        this.dateTime = dateTime;
        this.description = description;
        this.contacts = contacts;
        this.tag = tag;
    }

    /***
     * Creates an event with the given parameters
     * @param name the name of the event
     * @param date the date of the event
     */
    Event(String name, LocalDateTime date) {
        this.name = name;
        this.dateTime = date;
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
    void setName(String name) {
        this.name = name;
    }

    /***
     * Returns the address of the event
     * @return address of the event
     */
    public String getAddress() {
        return address.getAddress();
    }

    /***
     * Sets the address of the event
     * @param address the address of the event
     */
    void setAddress(String address) {
        this.address = new Address(address);
    }

    /***
     * Returns the date/time object of the event
     * @return the date/time object of the event
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /***
     * Sets the date and time of the event
     * @param dateTime the date and time of the event
     */
    void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
    void setDescription(String description) {
        this.description = description;
    }

    /***
     * Adds a tag to the event
     * @param tag the tag to be added
     */
    void addTag(Tag tag){
        this.tag = tag;
    }

    /***
     * Removes a tag from the event
     */
    void removeTag(){
        tag = null;
    }

    /***
     * Returns tag
     * @return the tag
     */
    public Tag getTag(){
        return this.tag;
    }

    /***
     * Adds a contact to the event
     * @param contact the contact to be added
     * @return true if operation successful, false if it already exists.
     */
    public boolean addContact(Contact contact){
        if (contacts.contains(contact)){
            return false;
        }
        contacts.add(contact);
        return true;
    }

    /***
     * Removes a contact from the event
     * @param contact the contact to be removed
     * @return true if operation successful, false if not.
     */
    public boolean removeContact(Contact contact){
        return contacts.remove(contact);
    }

    /***
     * Returns the contact arraylist.
     * @return the contact arraylist.
     */
    public ArrayList<Contact> getContacts(){
        return this.contacts;
    }

    @Override
    public <E, T> T accept(IPRMVisitor<E, T> visitor, E env) {
        return visitor.visitEvent(this, env);
    }
}
