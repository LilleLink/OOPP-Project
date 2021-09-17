package model;

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
    public UUID createTag(String name){
        return UUID.randomUUID();
    }

    private void delete(Tag tag){

    }

    private boolean renameTo(Tag tag, String name){
        return false;
    }

    private static class CommonTag extends Tag{

        private TagFactory parentFactory;

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
