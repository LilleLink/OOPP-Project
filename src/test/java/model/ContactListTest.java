package model;

import mocks.MockObserver;
import model.exceptions.NameNotAllowedException;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class ContactListTest {
    private ContactList contactList;
    private final MockObserver mockObserver = new MockObserver();


    @Before
    public void before() {
        if (!Objects.isNull(contactList)) {
            contactList.unSubscribe(mockObserver);
        }
        contactList = new ContactList();
        contactList.subscribe(mockObserver);
        mockObserver.resetEventCount();
    }

    @Test
    public void addContactTest() {
        assertEquals(0, contactList.getList().size());
        try {
            contactList.addContact("test");
        } catch (NameNotAllowedException e) {
            e.printStackTrace();
        }
        assertEquals(1, contactList.getList().size());
    }

    @Test
    public void emptyNameTest() {
        assertThrows(NameNotAllowedException.class, () -> contactList.addContact(""));
    }

    @Test
    public void removeContact() {
        Contact contact = new Contact("test");
        assertEquals(0, contactList.getList().size());
        contactList.addContact(contact);
        assertEquals(1, contactList.getList().size());
        contactList.removeContact(contact);
        assertEquals(0, contactList.getList().size());
    }

    @Test
    public void observerTest() {
        Contact contact = new Contact("test");
        assertEquals(0, mockObserver.getEventCount());
        contactList.addContact(contact);
        assertEquals(1, mockObserver.getEventCount());
        contactList.removeContact(contact);
        assertEquals(2, mockObserver.getEventCount());
        contactList.unSubscribe(mockObserver);
        contactList.addContact(contact);
        assertEquals(2, mockObserver.getEventCount());
    }

    @Test
    public void toStringTest() {
        try {
            contactList.addContact("name");
        } catch (NameNotAllowedException e) {
            fail();
        }
        assertEquals("ContactList{contactList=[Contact{name='name', phoneNumber='', address='', tags=[], notes=Notes{elements=[]}}]}", contactList.toString());
    }

    @Test
    public void emptyNameCacheTest() {
        Contact.ContactCache cache = new Contact.ContactCache();
        cache.name = "name";
        try {
            contactList.addContact(cache);
            Contact c = contactList.getList().get(0);
            assertNotNull(c.getDirectoryId());
            assertNotNull(c.getTags());
        } catch (Exception e) {
            fail();
        }
    }
}
