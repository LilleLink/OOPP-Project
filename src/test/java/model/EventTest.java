package model;

import model.exceptions.NameNotAvailableException;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {
    private Event event;

    @Before
    public void beforeTest() {
        event = new Event("Test event", LocalDateTime.now());
    }

    @Test
    public void testCreation() {
        assertEquals("Test event", event.getName());
    }

    // To make sure its visible and things are returned without side-effects
    @Test
    public void editAttributes() {
        event.setAddress("Hubben 2.1");
        assertEquals("Hubben 2.1", event.getAddress());

        event.setDateTime(LocalDateTime.of(2021, 11, 17, 11, 30));
        assertEquals(30, event.getDateTime().getMinute());

        event.setDescription("simpens födelsedag");
        assertEquals("simpens födelsedag", event.getDescription());

        event.setName("test");
        assertEquals("test", event.getName());
    }

    @Test
    public void registerAndUnregisterContactID() {
        Contact contact = new Contact("simpen");
        event.addContact(contact);
        assertTrue(event.getContacts().contains(contact));

        event.removeContact(contact);
        assertFalse(event.getContacts().contains(contact));
    }

    @Test
    public void tagAndUntagTest() {
        TagHandler tf = new TagHandler();
        ITag myTag = null;
        try {
            myTag = tf.createTag("Kompisaj");
        } catch (NameNotAvailableException e) {
            e.printStackTrace();
        }

        event.setTag(myTag);
        assertEquals(event.getTag(), myTag);

        event.removeTag();
        assertNotEquals(event.getTag(), myTag);
    }

    @Test
    public void isInFutureTest() {
        Event past = new Event("Past", LocalDateTime.of(2020, 9, 21, 10, 44));
        Event future = new Event("Future", LocalDateTime.of(2022, 9, 21, 10,44));

        assertFalse(past.isInFuture());
        assertTrue(future.isInFuture());
    }

}