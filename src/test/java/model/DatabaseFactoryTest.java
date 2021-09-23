package model;

import database.DatabaseFactory;
import database.Database;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class DatabaseFactoryTest {

    private User user;

    @Before
    public void setUpUser() {
        user = new User("Test");

        Event event = new Event("Event1", LocalDateTime.now());
        event.addContact(new Contact("Bruh"));
        event.setAddress("Hubben 2.1");

        user.addEvent(event);

        user.addContact(new Contact("Other bruh"));
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
                Contact c = e.getContacts().stream().findFirst().orElseThrow(IllegalStateException::new);
                assertEquals(c.getName(), "Bruh");
            }else{
                fail();
            }
        });
    }

}