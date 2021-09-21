package model;

import java.util.*;

public class User implements IPRMVisitable{
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

    @Override
    public <T, E> void accept(IPRMVisitor<T, E> visitor, E env) {
        events.forEach(e -> e.accept(visitor, env));
        events.forEach(e -> e.accept(visitor, env));

        visitor.visitUser(this, env);
    }
}
