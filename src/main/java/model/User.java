package model;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User {
    //TODO fix javadoc, rushing to get runnable version W3
    private String name;
    private final ArrayList<Event> eventList = new ArrayList<>();
    private final TagFactory tagFactory = new TagFactory();

    public User(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    boolean addEvent(Event event) {
        return eventList.add(event);
    }

    boolean removeEvent(Event event) {
        return eventList.remove(event);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public List<Event> getContactEvents(Contact contact) {
        List<Event> contactEvents= new ArrayList<>();
        for (Event e: eventList) {
            if (e.getContacts().contains(contact))
                contactEvents.add(e);
        }
        return contactEvents;
    }

}
