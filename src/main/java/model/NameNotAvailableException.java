package model;

import javax.naming.NameAlreadyBoundException;

public class NameNotAvailableException extends NameAlreadyBoundException {

    NameNotAvailableException(String name){
        super("The name " + name + " was not available");
    }
}
