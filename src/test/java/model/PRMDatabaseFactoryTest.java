package model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class PRMDatabaseFactoryTest {

    private User user;

    @Before
    public void setUpUser() {
        user = new User("Test");

        Event event = new Event("Event1", LocalDateTime.now());
        event.addContact(new Contact("Bruh"));

        user.addEvent(event);

        user.addContact(new Contact("Other bruh"));
    }

    @Test
    public void testSave() throws IOException {
        PRMDatabaseFactory.createPRMDatabase().save(user);
    }

}