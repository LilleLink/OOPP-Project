package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagHandler {

    private final HashMap<String, Tag> stringTagHashMap = new HashMap<>();

    /**
     * Creates a new Tag if the name is available. If a tag of the given name already exists, the UUID for that tag is returned
     * @param name The name of the new Tag
     * @return The id of the new Tag
     */
    Tag createTag(String name) throws NameNotAvailableException{
        Tag tag;
        if (!nameIsAvailable(name)){
            throw new NameNotAvailableException(name);
        } else {
            tag = new Tag(name);
            stringTagHashMap.put(name, tag);
        }
        return new Tag(tag);
    }

    Tag getTag(String name) throws TagNotFoundException{
        Tag tag = stringTagHashMap.get(name);
        if (tag == null) throw new TagNotFoundException(name);
        return new Tag(tag);
    }

    ArrayList<Tag> getTags(){
        ArrayList<Tag> tags = new ArrayList<>();
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
     * Makes a name available again by "deleting" the tag which holds it. The tag still exists as instances in other classes
     * @param tagName The tag to be deleted
     */
    void delete(String tagName) {
        stringTagHashMap.remove(tagName);
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
     * @param color The new color as HEX-code
     * @param tag the tag to change color of
     * @return If the change succeeded
     */
    boolean setColor(String tag, String color){
        if (isHexColor(color)){
             stringTagHashMap.remove(tag);
             stringTagHashMap.put(tag,new Tag(tag,color));
            return true;
        }
        return false;
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
