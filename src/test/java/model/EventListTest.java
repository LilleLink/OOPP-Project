package model;

import mocks.MockObserver;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class EventListTest {
    private EventList eventList;
    private final MockObserver mockObserver = new MockObserver();


    @Before
    public void before() {
        if (!Objects.isNull(eventList)) {
            eventList.unSubscribe(mockObserver);
        }
        eventList = new EventList();
        eventList.subscribe(mockObserver);
        mockObserver.resetEventCount();
    }

    @Test
    public void addEventTest() {
        assertEquals(0, eventList.getList().size());
        eventList.addEvent(new Event("test", LocalDateTime.now()));
        assertEquals(1, eventList.getList().size());
    }

    @Test
    public void removeEvent() {
        Event event = new Event("test", LocalDateTime.now());
        assertEquals(0, eventList.getList().size());
        eventList.addEvent(event);
        assertEquals(1, eventList.getList().size());
        eventList.removeEvent(event);
        assertEquals(0, eventList.getList().size());
    }

    @Test
    public void observerTest() {
        Event event = new Event("test", LocalDateTime.now());
        assertEquals(0, mockObserver.getEventCount());
        eventList.addEvent(event);
        assertEquals(1, mockObserver.getEventCount());
        eventList.removeEvent(event);
        assertEquals(2, mockObserver.getEventCount());
    }
}
