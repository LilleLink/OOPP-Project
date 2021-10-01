package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class TagHandler {

    private final HashMap<String, Tag> stringTagHashMap = new HashMap<>();

    /**
     * Creates a new ITag if the name is available. If a tag of the given name already exists, that tag is returned
     * @param name The name of the new ITag
     * @return the new ITag
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

    /**
     * Returns the tag with a certain name
     * @param name name of the
     * @return the tag with the wanted name
     * @throws TagNotFoundException if no tag with the name {@code name} exists
     */
    ITag getTag(String name) throws TagNotFoundException{
        ITag tag = stringTagHashMap.get(name);
        if (tag == null) throw new TagNotFoundException(name);
        return tag;
    }

    /**
     * Get all tags created by the handler
     * @return an ArrayList of ITags
     */
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
    void rename(ITag iTag, String newName) throws NameNotAvailableException {
        if (stringTagHashMap.get(newName) != null)
            throw new NameNotAvailableException(newName);
        Tag tag = stringTagHashMap.get(iTag.getName());
        if (tag == null) throw new RuntimeException(iTag.getName());
        stringTagHashMap.remove(tag.getName());
        tag.setName(newName);
        stringTagHashMap.put(newName,tag);
    }


    /**
     * Changes the color of a Tag
     * @param iTag the tag to change color of
     * @param color The new color as HEX-code
     * @return If the change succeeded
     */
    boolean setColor(ITag iTag, String color){
        Tag tag = stringTagHashMap.get(iTag.getName());
        if (tag==null) throw new RuntimeException(iTag.getName());
        if (isValidColor(color)){
            tag.setColor(color);
            return true;
        }
        return false;
    }

    /**
     * checks if the color is a valid HEX-color
     * @param color the color to be checked
     * @return if the color is a valid HEX-color
     */
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
