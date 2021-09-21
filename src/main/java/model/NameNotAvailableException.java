package model;

public class NameNotAvailableException extends Exception{

    NameNotAvailableException(String name){
        super("The name " + name + " was not available");
    }
}
