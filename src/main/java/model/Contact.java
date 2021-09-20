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
     */
    void setName(String name){
        this.name = name;
    }

    /**
     * Sets the phone number of the contact.
     * @param number The number to change to.
     */
    void setPhoneNumber(String number){
        this.phoneNumber = number;
    }

}
