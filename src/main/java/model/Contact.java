package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contact {

    private String name;
    private String phoneNumber = "";
    private Address address = new Address("");
    private final List<Tag> tagList = new ArrayList<>();
    private Notes notes;

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
     *
     * @return Contact's address as string.
     */
    public String getAddress(){
        return this.address.getAddress();
    }

    /**
     * Opens the contact's address on google maps in browser.
     * @return If it worked or not.
     */
    public boolean openMap(){
        return this.address.openMap();
    }

    /**
     * Updates the contact's address.
     * @param address The address to be updated to.
     */
    void setAddress(String address){
        this.address = new Address(address);
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
