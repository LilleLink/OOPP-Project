package model;

public class TagNotFoundException extends Exception{
    TagNotFoundException(String name){
        super("No tag with the name" + name + "was found");
    }
}
