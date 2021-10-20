package model;

import model.exceptions.NameNotAllowedException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {
    private Event event;
    private EventList eventList;

    @Before
    public void beforeTest() {
        event = new Event("Test event", LocalDateTime.now());
        eventList = new EventList();
    }

    @Test
    public void testCreation() {
        assertEquals("Test event", event.getName());
    }

    // To make sure it is visible and things are returned without side effects
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
        } catch (NameNotAllowedException e) {
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
        Event future = new Event("Future", LocalDateTime.of(2022, 9, 21, 10, 44));

        assertFalse(past.isInFuture());
        assertTrue(future.isInFuture());
    }

    @Test
    public void addEventTest() {
        int oldSize = eventList.getList().size();
        Event e = eventList.addEvent();
        assert (eventList.getList().contains(e));
        assert (eventList.getList().size() > oldSize);
    }

    @Test
    public void contactIsPartOfTest() {
        Contact c1 = new Contact("name");
        Collection<Event> eventsWithContact = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            eventsWithContact.add(eventList.addEvent());
            eventList.addEvent();
        }
        eventsWithContact.forEach(event -> event.addContact(c1));
        Collection<Event> collectedEvents = eventList.getContactsEvents(c1);
        assertEquals(collectedEvents, eventsWithContact);
        assert (collectedEvents.size() < 6);
    }

    @Test
    public void eventOfWeekTest() {
        LocalDateTime oldTime = LocalDateTime.now().minusDays(50);
        LocalDateTime timeNow = LocalDateTime.now();
        Collection<Event> eventsNow = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            eventsNow.add(new Event(Integer.toString(i), timeNow));
        }
        eventsNow.forEach(event -> eventList.addEvent(event));
        Event oldEvent = new Event("old", oldTime);
        eventList.addEvent(oldEvent);
        Collection<Event> eventsOfWeek = eventList.getEventsOfWeek(timeNow.toLocalDate());
        assertEquals(eventsNow, eventsOfWeek);
        assertFalse(eventsOfWeek.contains(oldEvent));
    }

    @Test
    public void searchIdentityTest() {
        Event e = eventList.addEvent();
        String name = e.getName();
        assertEquals(e.getSearchIdentity(), name.toLowerCase());
    }

    @Test
    public void setContactsTest() {
        Event e = new Event();
        Contact c1 = new Contact("Name");
        e.addContact(c1);
        List<Contact> newContacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            newContacts.add(new Contact(Integer.toString(i)));
        }
        e.setContacts(newContacts);
        List<Contact> eventContacts = e.getContacts();
        assertFalse(eventContacts.contains(c1));
        newContacts.forEach(contact -> assertTrue(eventContacts.contains(contact)));
    }

    private static class Wrapper {
        boolean isObserved = false;
    }

    @Test
    public void observerTest() {
        Wrapper w = new Wrapper();
        IObserver observer = () -> w.isObserved = true;
        eventList.subscribe(observer);
        eventList.notifyObservers();
        assert (w.isObserved);
        w.isObserved = false;
        eventList.unSubscribe(observer);
        eventList.notifyObservers();
        assert (!w.isObserved);

        event.subscribe(observer);
        event.notifyObservers();
        assert (w.isObserved);
        w.isObserved = false;
        event.unSubscribe(observer);
        event.notifyObservers();
        assert (!w.isObserved);
    }

}