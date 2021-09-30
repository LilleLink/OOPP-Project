package model;

import java.util.HashMap;
import java.util.Optional;

public class Tag implements ITag, ICacheVisitable {

    private String name;

    private String color;

    Tag(String name){
        this.name = name;
        color = "CDCDCD";
    }

    Tag(Tag other) {
        this.name = other.name;
        this.color = other.color;
    }

    Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     *
     * @return the name of a Tag
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return the color code of a Tag
     */
    public String getColor(){
        return color;
    }

    void setName(String name) {
        this.name = name;
    }

    void setColor(String color) {
        this.color = color;
    }

    public static class TagCache {
        public HashMap<String, ITag> stringTagHashMap;
    }

    private TagCache getCache() {
        TagCache cache = new TagCache();
        return cache;
    }

    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }

}
