package model;

import model.exceptions.NameNotAllowedException;
import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;

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
        user.getEvents().addEvent(e);
        assertTrue(user.getEvents().getList().contains(e));
    }

    @Test
    public void removeEvent() {
        Event e = new Event("Hej", LocalDateTime.now());
        user.getEvents().addEvent(e);
        assertTrue(user.getEvents().getList().contains(e));
        user.getEvents().removeEvent(e);
        assertFalse(user.getEvents().getList().contains(e));
    }

    @Test
    public void testContactEvents() {
        Event e = new Event("Hej", LocalDateTime.now());
        Contact contact = new Contact("Test");
        user.getEvents().addEvent(e);
        e.addContact(contact);

        assertTrue(user.getContactEvents(contact).contains(e));
    }

    @Test
    public void addContact(){
        try {
            user.getContacts().addContact("Test Testson");
            user.getContacts().addContact("Simon E");
            assertEquals(2, user.getContacts().getList().size());
        } catch (NameNotAllowedException e){
            fail();
        }
    }

    @Test
    public void addContact2() {
        user.getContacts().addContact(new Contact("Simon"));
        user.getContacts().addContact(new Contact("Test testsson"));
        assertEquals(2, user.getContacts().getList().size());
    }

    @Test
    public void removeContact(){
        try {
            user.getContacts().addContact("Test Testson");
            user.getContacts().addContact("Simon E");
            user.getContacts().removeContact(user.getContacts().getList().get(0));
            assertEquals(1, user.getContacts().getList().size());
        } catch (NameNotAllowedException e){
            fail();
        }
    }

}
