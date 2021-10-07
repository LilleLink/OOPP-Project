package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import model.exceptions.*;

import java.util.List;


public class ContactTest {

    private Contact c1;
    String testName = "Test Name";
    private final TagHandler tagHandler = new TagHandler();

    @BeforeClass
    public static void initVariables(){

    }

    @Before
    public void setup(){
        c1 = new Contact(testName);
    }

    @Test
    public void addressNamePhoneTest(){
        c1.setAddress("   baller street 4 ");
        c1.setName("Sir Hamilton");
        c1.setPhoneNumber("070-369-5555454266");
        assertEquals("   baller street 4 ", c1.getAddress());
        assertEquals("Sir Hamilton", c1.getName());
        assertEquals("070-369-5555454266", c1.getPhoneNumber());
    }

    @Test
    public void addAndGetTagTest(){
        try{
            ITag t1 = tagHandler.createTag("friends");
            assertEquals(0, c1.getTags().size());
            c1.addTag(t1);
            assertEquals(1, c1.getTags().size());
            assertTrue(c1.getTags().contains(t1));
        } catch (model.exceptions.NameNotAvailableException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void removeTagTwiceTest(){
        try {
            ITag t1 = tagHandler.createTag("work");
            c1.addTag(t1);
            c1.removeTag(t1);
            assertThrows(TagNotFoundException.class, () -> c1.removeTag(t1));
        } catch (NameNotAvailableException | TagNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void andAndRemoveNote() {
        c1.addNote("Pizza");
        List<Note> notes = c1.getNotes();
        assert(notes.size() == 1);
        c1.removeNote(0);
        List<Note> newNotes = c1.getNotes();
        assert(newNotes.size() == 0);
    }

    @Test
    public void editAndViewNote() {
        String text = "Pizza is good";
        c1.addNote();
        c1.editNote(0,text);
        assert(c1.viewNoteAt(0).equals(text));
    }
}
