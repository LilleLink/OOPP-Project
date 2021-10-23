package model.exceptions;

/***
 * A custom exception that extends the NameNotAllowedException class.
 * Thrown in the event that a given name is already taken.
 */
public class NameNotAvailableException extends NameNotAllowedException {

    public NameNotAvailableException(String name) {
        super("The name " + '"' + name + '"' + " was not available");
    }
}
