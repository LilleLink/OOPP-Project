package model;

import org.junit.Before;
import org.junit.Test;

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
    public void testSave() {
        PRMDatabaseFactory.createUserDatabase().save(user);
    }

}