package model;

import java.util.UUID;

public abstract class Tag {

    protected String name;

    protected String color ="FCFCFC";

    protected UUID id;

    protected Tag(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }

    /**
     *
     * @return the name of a Tag
     */
    String getName(){
        return name;
    }

    /**
     *
     * @return the color code of a Tag
     */
    String getColor(){
        return color;
    }

    /**
     * Deletes a Tag
     */
    abstract void delete();

    /**
     * Renames a Tag to the given string. Returns false if the name was not available
     * @param name the new name
     * @return if the renaming succeeded
     */
    abstract boolean renameTo(String name);

    /**
     * Changes the color of a Tag
     * @param color The new color as HEX-code
     * @return If the change succeeded
     */
    boolean setColor(String color){
        if (isHexColor(color)){
            this.color = color;
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
