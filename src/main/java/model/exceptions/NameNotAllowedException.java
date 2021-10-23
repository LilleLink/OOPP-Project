package model.exceptions;

/***
 * A custom exception thrown when a name attribute is not allowed.
 */
public class NameNotAllowedException extends Exception {
    
    public NameNotAllowedException(String message) {
        super(message);
    }
}
