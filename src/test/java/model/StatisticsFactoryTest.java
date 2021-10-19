package model;

import model.exceptions.NameNotAllowedException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class StatisticsFactoryTest {

    EventList eventList;
    TagHandler tagHandler;

    @Before
    public void initEnvironment() {
        eventList = new EventList();
        tagHandler = new TagHandler();
        ITag testtag1 = null;
        ITag testtag2 = null;
        ITag testtag3 = null;

        try {
            testtag1 = tagHandler.createTag("Test1");
            testtag2 = tagHandler.createTag("Test2");
            testtag3 = tagHandler.createTag("Test3");
        } catch (NameNotAllowedException e) {
            e.printStackTrace();
        }

        Event testevent1 = new Event();
        testevent1.setTag(testtag1);
        Event testevent2 = new Event();
        testevent2.setTag(testtag1);
        Event testevent3 = new Event();
        testevent3.setTag(testtag2);
        Event testevent4 = new Event();
        testevent4.setTag(testtag3);

        eventList.addEvent(testevent1);
        eventList.addEvent(testevent2);
        eventList.addEvent(testevent3);
        eventList.addEvent(testevent4);
    }

    @Test
    public void testStats() {
        HashMap<String, Integer> res = StatisticsFactory.getEventDelegation(eventList, tagHandler);
        assertEquals(res.get("Test1"), Integer.valueOf(2));
        assertEquals(res.get("Test2"), Integer.valueOf(1));
        assertEquals(res.get("Test3"), Integer.valueOf(1));
    }


}
