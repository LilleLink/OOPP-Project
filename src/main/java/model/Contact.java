package model;


import java.util.ArrayList;
import java.util.List;

import model.exceptions.*;

import java.util.Optional;

public class Contact implements ICacheVisitable {

    private String name;
    private String phoneNumber = "";
    private Address address = new Address("");
    private final List<Tag> tags;
    private Notes notes;
    private boolean isDeleted;

    /**
     * @param name The contact's name.
     */
    Contact(String name) {
        this.name = name;
        this.tags = new ArrayList<>();
    }

    /**
     * @return The contact's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The contact's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return Contact's address as string.
     */
    public String getAddress() {
        return this.address.getAddress();
    }

    /**
     * Opens the contact's address on google maps in browser.
     *
     * @return If it worked or not.
     */
    public boolean openMap() {
        return this.address.openMap();
    }

    /**
     * Updates the contact's address.
     *
     * @param address The address to be updated to.
     */
    void setAddress(String address) {
        this.address = new Address(address);
    }


    /**
     * Sets the name of the contact.
     *
     * @param name The name to change to.
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param number The number to change to.
     */
    void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }

    /**
     * Marks contact as deleted which removes it from the contact list, but it is still viewable in earlier events.
     */
    void delete() {
        isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Adds a tag to the contact.
     *
     * @param tag The desired tag.
     */
    void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes a tag from the contact.
     *
     * @param tag The tag to remove.
     * @throws TagNotFoundException If the contact does not have the given tag.
     */
    void removeTag(Tag tag) throws TagNotFoundException {
        if (!tags.contains(tag)) throw new TagNotFoundException(tag.getName());
        tags.remove(tag);
    }

    /**
     * @return A list of the contact's tags.
     */
    public List<Tag> getTags() {
        return new ArrayList<>(tags);
    }

    /***
     * The contact cache class contains fields which should be saved/loaded to persistent storage.
     */
    public static class ContactCache {
        public String name;
        public String phoneNumber;
        public Address address;
        public List<Tag> tags;
        public Notes notes;

        ContactCache(Contact contact) {
            this.name = contact.name;
            this.phoneNumber = contact.phoneNumber;
            this.address = contact.address;
            this.tags = new ArrayList<>(contact.tags);
            this.notes = contact.notes;
        }

        public ContactCache() {}
    }

    public Contact(ContactCache cache) {
        this.name = cache.name;
        this.phoneNumber = cache.phoneNumber;
        this.address = cache.address;
        this.tags = cache.tags;
        this.notes = cache.notes;
    }

    /***
     * Invoke the contact cache visitor case.
     */
    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(new ContactCache(this), env);
    }
}
