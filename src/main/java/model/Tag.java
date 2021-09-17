package model;

import java.util.UUID;

public abstract class Tag {

    protected String name;

    protected String color;

    protected UUID id;

    protected Tag(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }

}
