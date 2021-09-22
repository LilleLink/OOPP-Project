package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class IPRMVisitorTest {

    private User user;

    @Before
    public void setUpUser() {
        user = new User("Bruh");
        user.addEvent(new Event("Event1", LocalDateTime.now()));
        user.addEvent(new Event("Event2", LocalDateTime.now()));
        user.addEvent(new Event("Event3", LocalDateTime.now()));
    }
    
    @Test
    public void testVisitUser() {
        StringBuilder name = new StringBuilder();
        
        user.accept(new ICacheVisitor<StringBuilder, Void>() {
            @Override
            public Void visitUserCache(User.UserCache user, StringBuilder env) {
                env.append(user.name);
                return null;
            }

            @Override
            public Void visitContactCache(Contact.ContactCache contact, StringBuilder env) { return null; }

            @Override
            public Void visitEventCache(Event.EventCache event, StringBuilder env) { return null; }
        }, name);

        assertEquals(name.toString(), "Bruh");
    }

    @Test
    public void testVisitEvent() {
        Collection<String> eventNames = new ArrayList<String>();

        user.accept(new ICacheVisitor<Collection<String>, Void>() {
            @Override
            public Void visitUserCache(User.UserCache user, Collection<String> env) {
                user.events.forEach(e -> e.accept(this, env));
                return null; }

            @Override
            public Void visitContactCache(Contact.ContactCache contact, Collection<String> env) {
                return null; }

            @Override
            public Void visitEventCache(Event.EventCache event, Collection<String> env) {
                env.add(event.name);
                return null;
            }
        }, eventNames);

        assertTrue(eventNames.contains("Event1") && eventNames.contains("Event2") && eventNames.contains("Event3"));
    }

}