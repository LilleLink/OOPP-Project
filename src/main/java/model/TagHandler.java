package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class TagHandler {

    private final HashMap<String, Tag> stringTagHashMap = new HashMap<>();

    /**
     * Creates a new Tag if the name is available. If a tag of the given name already exists, the UUID for that tag is returned
     * @param name The name of the new Tag
     * @return The id of the new Tag
     */
    ITag createTag(String name) throws NameNotAvailableException{
        Tag tag;
        if (!nameIsAvailable(name)){
            throw new NameNotAvailableException(name);
        } else {
            tag = new Tag(name);
            stringTagHashMap.put(name, tag);
        }
        return tag;
    }

    ITag getTag(String name) throws TagNotFoundException{
        ITag tag = stringTagHashMap.get(name);
        if (tag == null) throw new TagNotFoundException(name);
        return tag;
    }

    ArrayList<ITag> getTags(){
        ArrayList<ITag> tags = new ArrayList<>();
        stringTagHashMap.forEach((k,v) -> tags.add(v));
        return tags;
    }

    /**
     * Checks if a name is already taken by another Tag
     * @param name The name to be checked
     * @return If the name was available
     */
    boolean nameIsAvailable(String name){
        return stringTagHashMap.get(name) == null;
    }


    /**
     * Renames a Tag to the given string. Returns false if the name was not available
     * @param newName the new name
     */
    void rename(String oldName, String newName) throws NameNotAvailableException{
        if (stringTagHashMap.get(newName) != null)
            throw new NameNotAvailableException(newName);
        stringTagHashMap.remove(oldName);
        stringTagHashMap.put(newName, new Tag(newName));
    }


    /**
     * Changes the color of a Tag
     * @param iTag the tag to change color of
     * @param color The new color as HEX-code
     * @return If the change succeeded
     */
    boolean setColor(String iTag, String color) throws TagNotFoundException{
        Tag tag = stringTagHashMap.get(iTag);
        if (tag==null) throw new TagNotFoundException(iTag);
        if (isValidColor(color)){
            tag.setColor(color);
            return true;
        }
        return false;
    }

    private boolean isValidColor(String color) {
        return isValidLength(color) && isHexColor(color);
    }

    private boolean isValidLength(String color){
        return color.length()==6;
    }

    private boolean isHexColor(String color){
        for (char c: color.toCharArray()){
            if (Character.digit(c, 16) == -1){
                return false;
            }
        }
        return true;
    }

}
