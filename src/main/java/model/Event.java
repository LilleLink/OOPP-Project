package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/***
 * Represents an event occurring at a point in time, past or future, with a name/description and list of contacts/categories it is included in.
 */
public class Event implements IPRMVisitable{

    private String name;
    private Address address;
    private LocalDateTime dateTime;
    private String description;

    private final ArrayList<UUID> tagIdList = new ArrayList<>();
    private ArrayList<UUID> contactIdList = new ArrayList<>();


    /***
     * Creates an event with the given parameters.
     * @param name the name of the event
     * @param address the physical address of the event
     * @param dateTime the date and time of the event
     * @param description the description of the event
     * @param contactIdList the list containing the IDs of the contacts tagged in the event
     */
    public Event(String name, String address, LocalDateTime dateTime, String description, ArrayList<UUID> contactIdList) {
        this.name = name;
        this.address = new Address(address);
        this.dateTime = dateTime;
        this.description = description;
        this.contactIdList = contactIdList;
    }

    /***
     * Creates an event with the given parameters
     * @param name the name of the event
     * @param date the date of the event
     */
    public Event(String name, LocalDateTime date) {
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
     * @return a copy of the event with the new name
     */
    public Event setName(String name) {
        return new Event(name, this.address.getAddress(), this.dateTime, this.description, this.contactIdList);
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
     * @return a copy of the event with the new address.
     */
    public Event setAddress(String address) {
        return new Event(this.name, address, this.dateTime, this.description, this.contactIdList);
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
     * @return a copy of the event with the new date/time
     */
    public Event setDateTime(LocalDateTime dateTime) {
        return new Event(this.name, this.address.getAddress(), dateTime, this.description, this.contactIdList);
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
     * @return a copy of the event with the new description
     */
    public Event setDescription(String description) {
        return new Event(this.name, this.address.getAddress(), this.dateTime, description, this.contactIdList);
    }

    /***
     * Adds a tag to the event
     * @param tag the UUID of the tag
     * @return true if it succeeds, false if it already exists
     */
    public boolean addTag(UUID tag){
        if (tagIdList.contains(tag)) return false;
        tagIdList.add(tag);
        return true;
    }

    /***
     * Removes a tag from the event
     * @param id the id of the tag to be removed
     * @return true if operation successful, false if operation failed
     */
    public boolean removeTag(UUID id){
        return tagIdList.remove(id);
    }

    /***
     * Returns a copy of the ArrayList containing the tag UUID's
     * @return a copy of the ArrayList
     */
    public ArrayList<UUID> getTagsIdList(){
        return new ArrayList<>(tagIdList);
    }

    /***
     * Adds a contact to the event
     * @param id the id of the contact to be added
     * @return true if operation successful, false if it already exists.
     */
    public boolean addContact(UUID id){
        if (contactIdList.contains(id)){
            return false;
        }
        contactIdList.add(id);
        return true;
    }

    /***
     * Removes a contact from the event
     * @param id the ID of the contact to be removed
     * @return true if operation successful, false if not.
     */
    public boolean removeContact(UUID id){
        return contactIdList.remove(id);
    }

    /***
     * Returns a copy of the contact UUID arraylist.
     * @return a copy of the contact UUID arraylist.
     */
    public ArrayList<UUID> getContactIdList(){
        return new ArrayList<>(contactIdList);
    }

    @Override
    public <T, E> void accept(IPRMVisitor<T, E> visitor, E env) {
        visitor.visitEvent(this, env);
    }
}
