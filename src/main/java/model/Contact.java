package model;

public class Contact {

    private String name;
    private String phoneNumber = "";
    private String address = "";

    Contact(String name){
        this.name = name;
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
        this.phoneNumber = parsePhoneNumber(phoneNumber);
    }


    private String parsePhoneNumber(String phoneNumber){
        StringBuilder sb = new StringBuilder();
        char[] phoneNumberChars = phoneNumber.toCharArray();
        for (Character c : phoneNumberChars){
            if (Character.isDigit(c))
                sb.append(c);
            else if (c =='+' || c==' ')
                sb.append(c);
        }
        return sb.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
