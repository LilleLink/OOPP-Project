package model;

import model.exceptions.NameNotAvailableException;

public abstract class Tag {

    protected String name;

    protected String color ="CDCDCD";
    private boolean deleted;

    protected Tag(String name){
        this.name = name;
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
    void delete(){
        this.deleted = true;
        updateHandler();
    }

    protected abstract void updateHandler();

    public boolean isDeleted(){ return this.deleted; }

    /**
     * Renames a Tag to the given string. Returns false if the name was not available
     * @param name the new name
     */
    abstract void renameTo(String name) throws NameNotAvailableException;

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
