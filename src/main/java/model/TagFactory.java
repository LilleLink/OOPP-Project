package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TagFactory {

    private HashMap<String, UUID> stringUUIDHashMap = new HashMap<>();
    private HashMap<UUID, Tag> UUIDTagHashMap = new HashMap<>();

    /**
     * Creates a new Tag if the name is available. If a tag of the given name already exists, the UUID for that tag is returned
     * @param name The name of the new Tag
     * @return The id of the new Tag
     */
    UUID createTag(String name){
        UUID id;
        if (nameIsAvailable(name)){
            Tag tag = new CommonTag(name, this);
            id = tag.id;
            stringUUIDHashMap.put(name, id);
            UUIDTagHashMap.put(id, tag);
        } else {
            id = stringUUIDHashMap.get(name);
        }
        return id;
    }

    ArrayList<Tag> getTag(ArrayList<UUID> ids){
        Tag tag;
        ArrayList<Tag> tags = new ArrayList<>();
        for (UUID id: ids){
            tag = UUIDTagHashMap.get(id);
            if (tag != null)
                tags.add(tag);
        }
        return tags;
    }

    private boolean nameIsAvailable(String text){
        return stringUUIDHashMap.get(text) == null;
    }

    private void delete(Tag tag){
        stringUUIDHashMap.remove(tag.name);
        UUIDTagHashMap.remove(tag.id);
    }

    /**
     * Renames a Tag to the given string. Returns false if the name was not available
     * @param name the new name
     * @return if the renaming succeeded
     */
    boolean renameTo(UUID id, String name){
        Tag tag = UUIDTagHashMap.get(id);
        if (tag == null) return false;
        return renameTo(tag, name);
    }

    /**
     *
     * @param id UUID of the Tag to be checked
     * @return The name of the tag
     */
    String getName(UUID id){
        Tag tag = UUIDTagHashMap.get(id);
        return tag==null? "":  tag.name;
    }

    private boolean renameTo(Tag tag, String name){
        if (stringUUIDHashMap.get(name) != null) return false;
        stringUUIDHashMap.remove(tag.name);
        stringUUIDHashMap.put(name, tag.id);
        tag.name = name;
        return true;
    }

    private static class CommonTag extends Tag{

        private final TagFactory parentFactory;

        private CommonTag(String name, TagFactory parent){
            super(name);
            parentFactory = parent;
        }

        @Override
        void delete() {
            parentFactory.delete(this);
        }

        @Override
        boolean renameTo(String name) {
            return parentFactory.renameTo(this, name);
        }
    }
}
