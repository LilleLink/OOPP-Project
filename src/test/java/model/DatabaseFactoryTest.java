package model;

import database.DatabaseFactory;
import database.Database;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseFactoryTest {

    private User user;

    @Before
    public void setUpUser() {
        user = new User("Test");

        Event event = new Event("Event1", LocalDateTime.now());
        event.addContact(new Contact("Bruh"));
        event.setAddress("Hubben 2.1");

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