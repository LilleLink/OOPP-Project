package model;

import model.exceptions.NameNotAllowedException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StatisticsUtilsTest {

    EventList eventList;
    TagHandler tagHandler;
    ITag testtag1 = null;
    ITag testtag2 = null;
    ITag testtag3 = null;

    @Before
    public void initEnvironment() {
        eventList = new EventList();
        tagHandler = new TagHandler();

        // Creates the tags for testing purposes
        try {
            testtag1 = tagHandler.createTag("Test1");
            testtag2 = tagHandler.createTag("Test2");
            testtag3 = tagHandler.createTag("Test3");
        } catch (NameNotAllowedException e) {
            e.printStackTrace();
        }

        // Creates the events for testing purposes and tags them with test tags
        Event testevent1 = new Event();
        testevent1.setTag(testtag1);
        Event testevent2 = new Event();
        testevent2.setTag(testtag1);
        Event testevent3 = new Event();
        testevent3.setTag(testtag2);
        Event testevent4 = new Event();
        testevent4.setTag(testtag3);

        // Adds the events to the eventlist.
        eventList.addEvent(testevent1);
        eventList.addEvent(testevent2);
        eventList.addEvent(testevent3);
        eventList.addEvent(testevent4);
    }

    @Test
    public void testStats() {
        Map<ITag, Integer> res = StatisticsUtils.getEventDelegation(eventList, tagHandler);
        assertEquals(res.get(testtag1), Integer.valueOf(2));
        assertEquals(res.get(testtag2), Integer.valueOf(1));
        assertEquals(res.get(testtag3), Integer.valueOf(1));
    }

}
