package model;

import java.awt.*;

public class Contact {

    private String name;
    private String phoneNumber = "";

    Contact(String name){
        this.name = name;
    }

    private Contact(Contact old){
        name = old.name;
        phoneNumber = old.phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact setName(String name){
        Contact newContact = new Contact(this);
        newContact.name = name;
        return newContact;
    }

    public Contact setPhoneNumber(String number){
        Contact newContact = new Contact(this);
        newContact.phoneNumber = number;
        return newContact;
    }

}
