package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class TagHandler {

    private HashMap<String, Tag> stringTagHashMap = new HashMap<>();

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
            tag = new CommonTag(name);
            stringTagHashMap.put(name, tag);
        }
        return tag;
    }

    Tag getTag(String name) throws TagNotFoundException{
        Tag tag = stringTagHashMap.get(name);
        if (tag == null) throw new TagNotFoundException(name);
        return tag;
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
     * @param tag The tag to be deleted
     */
    private void delete(Tag tag){
        stringTagHashMap.remove(tag.name);
    }

    /**
     * Renames a Tag to the given string. Returns false if the name was not available
     * @param name the new name
     */
    private void renameTo(Tag tag, String name) throws NameNotAvailableException{
        if (stringTagHashMap.get(name) != null)
            throw new NameNotAvailableException(name);
        stringTagHashMap.remove(tag.name);
        stringTagHashMap.put(name, tag);
    }

    private class CommonTag extends Tag{

        private final TagHandler parentFactory;

        private CommonTag(String name){
            super(name);
            parentFactory = TagHandler.this;
        }

        @Override
        protected void updateHandler() {
            parentFactory.delete(this);
        }

        @Override
        void renameTo(String name) throws NameNotAvailableException {
            parentFactory.renameTo(this, name);
            this.name = name;
        }
    }
}
