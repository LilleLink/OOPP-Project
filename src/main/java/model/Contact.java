package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Contact {

    private String name;
    private String phoneNumber = "";
    private final UUID id;
    private final ArrayList<UUID> tagsIdList = new ArrayList<UUID>();

    /**
     * Creates a contact with the given name and assigns a UUID to it.
     * @param name The contact's name.
     */
    Contact(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }

    private Contact(Contact old){
        name = old.name;
        phoneNumber = old.phoneNumber;
        id = old.id;
    }

    /**
     *
     * @return The contact's name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The contact's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return The contact's UUID.
     */
    public UUID getId(){
        return id;
    }

    /**
     * Sets the name of the contact.
     * @param name The name to change to.
     * @return A copy of the contact with the updated name.
     */
    public Contact setName(String name){
        Contact newContact = new Contact(this);
        newContact.name = name;
        return newContact;
    }

    /**
     * Sets the phone number of the contact.
     * @param number The number to change to.
     * @return A copy of the contact with the updated phone number.
     */
    public Contact setPhoneNumber(String number){
        Contact newContact = new Contact(this);
        newContact.phoneNumber = number;
        return newContact;
    }

    /**
     * Adds a tag ID to the contact's tag id list.
     * @param id The id of the tag to add
     * @return If the operation was successful, will return false if contact already has tag.
     */
    public boolean addTag(UUID id){
        if (!tagsIdList.contains(id)){
            tagsIdList.add(id);
            return true;
        }
        return false;
    }

    /**
     * Removes a tag from the contact.
     * @param id The id of the tag to remove
     * @return If the operation was successful, will return false if contact did not have the given tag.
     */
    public boolean removeTag(UUID id){
        return tagsIdList.remove(id);
    }

    /**
     *
     * @return A copy of the contact's tags' ids as an ArrayList.
     */
    public ArrayList<UUID> getTagsIdList(){
        return new ArrayList<>(tagsIdList);
    }

}
