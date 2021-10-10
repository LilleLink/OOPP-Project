package model.exceptions;

public class TagNotFoundException extends Exception{
    public TagNotFoundException(String name){
        super("No tag with the name" + name + "was found");
    }
}
