package model;

import model.exceptions.NameNotAvailableException;

class Tag {

    private String name;

    private String color ="CDCDCD";

    Tag(String name){
        this.name = name;
    }

    Tag(Tag other) {
        this.name = other.name;
        this.color = other.color;
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
