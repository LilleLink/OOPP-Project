package model.exceptions;

public class NameNotAvailableException extends NameNotAllowedException {

    public NameNotAvailableException(String name) {
        super("The name " + '"' + name + '"' + " was not available");
    }
}
