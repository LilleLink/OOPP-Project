package model;


import jdk.vm.ci.meta.Local;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Test
    public void timeTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(event.getDateTime().getSecond() < LocalDateTime.now().getSecond());
    }

    // To make sure its visible and things are returned without side-effects
    @Test
    public void editAttributes() {
        event = event.setAddress("Hubben 2.1");
        assertEquals("Hubben 2.1", event.getAddress());

        event = event.setDateTime(LocalDateTime.of(2021, 11, 17, 11, 30));
        assertEquals(30, event.getDateTime().getMinute());

        event = event.setDescription("simpens födelsedag");
        assertEquals("simpens födelsedag", event.getDescription());

        event = event.setName("test");
        assertEquals("test", event.getName());
    }

    @Test
    public void registerAndUnregisterContactID() {
        Contact contact = new Contact("simpen");
        event.addContact(contact.getId());
        assertTrue(event.getContactIdList().contains(contact.getId()));

        event.removeContact(contact.getId());
        assertFalse(event.getContactIdList().contains(contact.getId()));
    }

    @Test
    public void tagAndUntagTest() {
        TagFactory tf = new TagFactory();
        UUID myTag = tf.createTag("Kompisaj");

        event.addTag(myTag);
        assertTrue(event.getTagsIdList().contains(myTag));

        event.removeTag(myTag);
        assertFalse(event.getTagsIdList().contains(myTag));
    }

}