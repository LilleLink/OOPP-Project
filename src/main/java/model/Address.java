package model;

import java.awt.*;
import java.net.URI;

public class Address {
    //TODO fix javadoc, rushing to get runnable version W3
    private final String address;

    public Address(String address){
        this.address = cleanString(address);
    }

    public String getAddress(){
        return address;
    }

    public Address setAddress(String address){
        return new Address(address);
    }


    private String cleanString(String s){
        s = s.trim().replaceAll("\\s+", " ");
        return s;
    }


    //TODO check if this works on linux and mac
    public boolean openMap(){
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://maps.google.com/maps?q=" +
                        this.address.replace(' ', '+')));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
