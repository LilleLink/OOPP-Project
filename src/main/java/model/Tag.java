package model;

import java.util.Optional;

public class Tag implements ITag, ICacheVisitable {

    private String name;

    private String color;

    /**
     * Creates a Tag with the name {name}, and the color {color}
     *
     * @param name  the name of the Tag
     * @param color the color of the Tag
     */
    Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    void setName(String name) {
        this.name = name;
    }

    void setColor(String color) {
        this.color = color;
    }

    public Tag(TagCache cache) {
        this.name = cache.name;
        this.color = cache.color;
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

    @Override
    public String toString() {
        return name;
    }
}
