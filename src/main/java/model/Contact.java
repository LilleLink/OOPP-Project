package model;

public class Contact {

    private String name;
    private String phoneNumber;
    private String address;

    Contact(String name){

    }

    public void removePhoneNumber(){
        phoneNumber = "";
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setAddress(String address) {
        this.address = address;
    }
}
