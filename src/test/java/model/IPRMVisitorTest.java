package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        
        user.accept(new IPRMVisitor<StringBuilder>() {
            @Override
            public void visitUser(User user, StringBuilder env) {
                env.append(user.getName());
            }

            @Override
            public void visitContact(Contact contact, StringBuilder env) { }

            @Override
            public void visitEvent(Event event, StringBuilder env) { }

            @Override
            public void visitNotes(Notes notes, StringBuilder env) { }
        }, name);

        assertEquals(name.toString(), "Bruh");
    }

    @Test
    public void testVisitEvent() {
        Collection<String> eventNames = new ArrayList<String>();

        user.accept(new IPRMVisitor<Collection<String>>() {
            @Override
            public void visitUser(User user, Collection<String> env) { }

            @Override
            public void visitContact(Contact contact, Collection<String> env) { }

            @Override
            public void visitEvent(Event event, Collection<String> env) {
                env.add(event.getName());
            }

            @Override
            public void visitNotes(Notes notes, Collection<String> env) { }
        }, eventNames);

        assertTrue(eventNames.contains("Event1") && eventNames.contains("Event2") && eventNames.contains("Event3"));
    }

}