package model;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    //TODO fix javadoc, rushing to get runnable version W3
    private String name;
    private final Collection<Event> events = new ArrayList<>();
    private final TagFactory tagFactory = new TagFactory();
    private final Collection<Contact> contacts = new ArrayList<>();

    public User(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }


}
