package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
            public Optional<Void> visit(User.UserCache user, StringBuilder env) {
                env.append(user.name);
                return Optional.empty();
            }

            @Override
            public Optional<Void> visit(Contact.ContactCache contact, StringBuilder env) { return Optional.empty(); }

            @Override
            public Optional<Void> visit(Event.EventCache event, StringBuilder env) { return Optional.empty(); }
        }, name);

        assertEquals(name.toString(), "Bruh");
    }

    @Test
    public void testVisitEvent() {
        Collection<String> eventNames = new ArrayList<>();

        user.accept(new ICacheVisitor<Collection<String>, Void>() {
            @Override
            public Optional<Void> visit(User.UserCache user, Collection<String> env) {
                user.events.forEach(e -> e.accept(this, env));
                return Optional.empty(); }

            @Override
            public Optional<Void> visit(Contact.ContactCache contact, Collection<String> env) {
                return Optional.empty(); }

            @Override
            public Optional<Void> visit(Event.EventCache event, Collection<String> env) {
                env.add(event.name);
                return Optional.empty();
            }
        }, eventNames);

        assertTrue(eventNames.contains("Event1") && eventNames.contains("Event2") && eventNames.contains("Event3"));
    }

}