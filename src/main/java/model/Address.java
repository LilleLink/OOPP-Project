package model;

import java.awt.*;
import java.net.URI;

public class Address {
    private final String address;

    public Address(String address){
        cleanString(address);
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public Address setAddress(String address){
        cleanString(address);
        return new Address(address);
    }


    private String cleanString(String s){
        s = s.trim().replaceAll("\\s+", " ");
        return s;
    }



}
