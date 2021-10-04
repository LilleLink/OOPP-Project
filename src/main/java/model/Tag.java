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
        public String name;
        public String color;
    }

    private TagCache getCache() {
        TagCache cache = new TagCache();
        cache.name = this.name;
        cache.color = this.color;
        return cache;
    }

    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }

}
