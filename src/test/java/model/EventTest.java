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


}
