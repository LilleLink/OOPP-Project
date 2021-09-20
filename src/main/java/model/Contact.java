package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Contact {

    private String name;
    private String phoneNumber = "";

    /**
     * @param name The contact's name.
     */
    Contact(String name){
        this.name = name;
    }

    private Contact(Contact old){
        name = old.name;
        phoneNumber = old.phoneNumber;
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

}
