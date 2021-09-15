package model;

import org.junit.Before;
import org.junit.*;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {

    Event event;
    LocalDate localDate = LocalDate.of(2021, 10, 15);
    String description = "Hej detta är ett event lol";
    String name = "Test Event";
    String address = "Exempelgatan 4";
    List<Contact> contactList = new ArrayList<>();

    @Before
    public void beforeTest() {
        event = new Event(name, address, localDate, description, contactList);
    }

    @Test
    public void eventIsImmutable() {
        event.setName("Test Event 1");
        assertSame(name, event.getName());

        event.setAddress("Test Address 1");
        assertSame(address, event.getAddress());

        event.setDescription("Test Description 1");
        assertSame(description, event.getDescription());

        event.setDate(LocalDate.now());
        assertSame(localDate, event.getDate());
    }

    @Test
    public void registerContact() {
        Contact contact = new Contact("Billy");
        event.addContact(contact);
        assertTrue(event.getRegisteredContacts().contains(contact));
    }

    @Test
    public void unregisterContact() {
        Contact contact = new Contact("Billy");
        event.addContact(contact);
        assertTrue(event.getRegisteredContacts().contains(contact));
        event.removeContact(contact);
        assertFalse(event.getRegisteredContacts().contains(contact));
    }

    @Test
    public void registerContacts() {
        List<Contact> contactList = new ArrayList<>();
        Contact contact1 = new Contact("1");
        Contact contact2 = new Contact("2");
        Contact contact3 = new Contact("3");
        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);

        event.addContacts(contactList);

        assertTrue(event.getRegisteredContacts().contains(contact1));
        assertTrue(event.getRegisteredContacts().contains(contact2));
        assertTrue(event.getRegisteredContacts().contains(contact3));
    }

    @Test
    public void unregisterContacts() {
        List<Contact> contactList = new ArrayList<>();
        Contact contact1 = new Contact("1");
        Contact contact2 = new Contact("2");
        Contact contact3 = new Contact("3");
        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);

        event.addContacts(contactList);

        assertTrue(event.getRegisteredContacts().contains(contact1));
        assertTrue(event.getRegisteredContacts().contains(contact2));
        assertTrue(event.getRegisteredContacts().contains(contact3));

        event.removeContacts(contactList);

        assertFalse(event.getRegisteredContacts().contains(contact1));
        assertFalse(event.getRegisteredContacts().contains(contact2));
        assertFalse(event.getRegisteredContacts().contains(contact3));
    }


}
