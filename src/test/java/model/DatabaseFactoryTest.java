package model;

import database.DatabaseFactory;
import database.Database;
import model.exceptions.NameNotAvailableException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseFactoryTest {

    private User user;

    @Before
    public void setUpUser() throws NameNotAvailableException {
        user = new User("Test");

        Event event = new Event("Event1", LocalDateTime.now());

        ITag tag = user.createTag("bizniz");
        ITag tag2 = user.createTag("frends");


        user.addContact("Bruh");
        Contact bruhContact = user.getContacts().get(user.getContacts().size() - 1);
        bruhContact.setAddress("Bruhs address :)");
        bruhContact.setPhoneNumber("+123456789");
        bruhContact.addNote("Hello this is bruh!");
        bruhContact.addNote("N'other note!");
        bruhContact.addTag(tag2);
        bruhContact.addTag(tag);

        user.addContact("Bruh 2");
        Contact bruh2Contact = user.getContacts().get(user.getContacts().size() - 1);
        bruh2Contact.setAddress("Bruh2s address :)");
        bruh2Contact.setPhoneNumber("+999999999");
        bruh2Contact.addNote("ANOTHER!");
        bruh2Contact.addTag(tag);
        user.removeContact(bruh2Contact);

        event.addContact(bruhContact);
        event.addContact(bruh2Contact);
        event.setName("Cool event");
        event.setDescription("This is a pretty fun event ye");
        event.setAddress("Hubben 2.1");


        event.addTag(tag);

        user.addEvent(event);

        // Shouldnt use cache like this but its the only way to test notes atm.
    }

    @Test
    public void testSave() throws IOException {
        Database db = DatabaseFactory.getDatabase();
        db.save(user);
        User user = db.load("");

        assertEquals(user.getName(), "Test");

        assertEquals(user.getEventList().size(), 1);

        user.getEventList().forEach(e -> {
            if(e.getName().equalsIgnoreCase("Event1")) {
                assertEquals(e.getAddress(), "Hubben 2.1");
            }else{
                fail();
            }
        });
    }

}