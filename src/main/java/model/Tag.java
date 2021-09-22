package model;

import model.exceptions.NameNotAvailableException;

class Tag {

    private final String name;

    private final String color;

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



}
