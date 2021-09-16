package model;

import java.awt.*;
import java.util.UUID;

public class Contact {

    private String name;
    private String phoneNumber = "";
    private final UUID id;

    Contact(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }

    private Contact(Contact old){
        name = old.name;
        phoneNumber = old.phoneNumber;
        id = old.id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UUID getId(){
        return id;
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
