package model.exceptions;

/***
 * A custom exception thrown when trying to access a tag that does not exist.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException(String name) {
        super("No tag with the name " + name + " was found");
    }
}
