package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void beforeTests() {
        user =  new User("TestUser");
    }

    @Test
    public void testName() {
        user.setName("Ny");
        assertEquals("Ny", user.getName());
    }

    @Test
    public void addEvent() {
        Event e = new Event("Hej", LocalDateTime.now());
        user.addEvent(e);
        assertTrue(user.getEventList().contains(e));
    }

    @Test
    public void removeEvent() {
        Event e = new Event("Hej", LocalDateTime.now());
        user.addEvent(e);
        assertTrue(user.getEventList().contains(e));
        user.removeEvent(e);
        assertFalse(user.getEventList().contains(e));
    }

    @Test
    public void testContactEvents() {
        Event e = new Event("Hej", LocalDateTime.now());
        Contact contact = new Contact("Test");
        user.addEvent(e);
        e.addContact(contact);

        assertTrue(user.getContactEvents(contact).contains(e));
    }

}
