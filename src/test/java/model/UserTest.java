package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Test
    public void addContact(){
        user.addContact("Test Testson");
        user.addContact("Simon E");
        assertEquals(2, user.getContacts().size());
    }

    @Test
    public void removeContact(){
        user.addContact("Test Testson");
        user.addContact("Simon E");
        user.removeContact(user.getContacts().get(0));
        assertEquals(1, user.getContacts().size());
    }

    @Test
    public void createTagsTest(){
        try {
            ITag t1 = user.createTag("First");
            ITag t2 = user.createTag("second");
            List<ITag> tags = user.getTags();
            assertEquals(t1, tags.get(0));
            assertEquals(t2, tags.get(1));
            assertEquals(t1, user.getTag(t1.getName()));
        } catch (NameNotAvailableException | TagNotFoundException e){
            fail();
        }
    }

    @Test
    public void renameTagTest(){
        try{
            ITag t1 = user.createTag("First");
            user.renameTag(t1, "second");
            assertEquals(t1.getName(), "second");
        } catch (NameNotAvailableException e) {
            fail();
        }
    }

    @Test
    public void setColorTest(){
        try{
            ITag t1 = user.createTag("Tag name");
            user.setColor(t1, "09cdda");
            assertEquals("09cdda", t1.getColor());
        } catch (NameNotAvailableException e) {
            fail();
        }
    }
}
