package model;

import mocks.MockObserver;
import model.exceptions.NameNotAllowedException;
import model.exceptions.TagNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ContactTest {

    private Contact c1;
    String testName = "Test Name";
    private final TagHandler tagHandler = new TagHandler();

    @Before
    public void setup() {
        c1 = new Contact(testName);
    }

    @Test
    public void addressNamePhoneTest() {
        c1.setAddress("   baller street 4 ");
        c1.setName("Sir Hamilton");
        c1.setPhoneNumber("070-369-5555454266");
        assertEquals("   baller street 4 ", c1.getAddress());
        assertEquals("Sir Hamilton", c1.getName());
        assertEquals("070-369-5555454266", c1.getPhoneNumber());
    }

    @Test
    public void addAndGetTagTest() {
        try {
            ITag t1 = tagHandler.createTag("friends");
            assertEquals(0, c1.getTags().size());
            c1.addTag(t1);
            assertEquals(1, c1.getTags().size());
            assertTrue(c1.getTags().contains(t1));
        } catch (NameNotAllowedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void removeTagTwiceTest() {
        try {
            ITag t1 = tagHandler.createTag("work");
            c1.addTag(t1);
            c1.removeTag(t1);
            assertThrows(TagNotFoundException.class, () -> c1.removeTag(t1));
        } catch (TagNotFoundException | NameNotAllowedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void andAndRemoveNote() {
        c1.addNote("Pizza");
        assert c1.sizeOfNotes() == 1;
        c1.removeNote(0);
        assert c1.sizeOfNotes() == 0;
    }

    @Test
    public void editAndViewNote() {
        String text = "Pizza is good";
        c1.addNote();
        c1.editNote(0, text);
        assert c1.viewNote(0).equals(text);
    }

    @Test
    public void viewLastAddedNote() {
        c1.addNote("note1");
        c1.addNote("note2");
        assertEquals("note2", c1.getLastAddedNote().viewNote());
    }

    @Test
    public void searchIdentityTest() {
        c1.setName("This is a Name");
        assertEquals("this is a name", c1.getSearchIdentity());
    }

    @Test
    public void observableTest() {
        MockObserver observer = new MockObserver();
        c1.subscribe(observer);
        assertEquals(0, observer.getEventCount());
        c1.notifyObservers();
        assertEquals(1, observer.getEventCount());
        c1.unSubscribe(observer);
        c1.notifyObservers();
        assertEquals(1, observer.getEventCount());
    }
}
