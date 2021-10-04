package model;

import java.util.ArrayList;
import java.util.List;

/***
 * Wrapper for a list of contacts.
 */
public class ContactList {

    private List<Contact> contactList = new ArrayList<>();

    /***
     * Creates a new contactlist wrapper object
     */
    public ContactList() {}

    /***
     * Wraps a given list of contacts.
     * @param contacts the list to be wrapped
     */
    public ContactList(List<Contact> contacts) {
        this.contactList = contacts;
    }

    /***
     * Adds a contact to the contactList.
     * @param name the name of the contact
     */
    public void addContact(String name) {
        contactList.add(new Contact(name));
    }

    /***
     * Adds a contact to the contactList
     * @param contact the contact object
     */
    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    /***
     * Removes a contact from the contactList
     * @param contact the contact to be removed
     */
    public void removeContact(Contact contact) {
        contactList.remove(contact);
    }

    /***
     * Returns the contactList
     * @return the contact list
     */
    public List<Contact> getList() {
        return this.contactList;
    }

}
