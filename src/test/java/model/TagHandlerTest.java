package model;

import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class TagHandlerTest {

    TagHandler handler;

    @Before
    public void preTest(){
        handler = new TagHandler();
    }

    @Test
    public void creationTest(){
        try {
            handler.createTag("First tag");
            handler.createTag("Second Tag");
        } catch (NameNotAvailableException e){
            fail();
        }
        List<ITag> tags = handler.getTags();
        assertNotEquals(tags.get(0).getName(), tags.get(1).getName());
    }

    @Test
    public void noDuplicateTest(){
        try {
            handler.createTag("First tag");
            assertThrows(NameNotAvailableException.class, () -> handler.createTag("First tag"));
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void renameTest(){
        try {
            handler.createTag("First tag");
            handler.rename("First tag","new name");
            assertEquals("new name", handler.getTag("new name").getName());
        } catch (NameNotAvailableException | TagNotFoundException e){
            fail();
        }
    }

    @Test
    public void renameToDuplicateNameTest(){
        try {
            handler.createTag("First tag");
            handler.createTag("Second tag");
            assertThrows(NameNotAvailableException.class, () -> handler.rename("First tag", "Second tag"));
            assertNotEquals(handler.getTag("First tag").getName(), handler.getTag("Second tag").getName());
        } catch (NameNotAvailableException | TagNotFoundException e){
            fail();
        }
    }

    @Test
    public void makeNameAvailableTest(){
        try {
            handler.createTag("First tag");
            handler.rename("First tag","Something else");

            ITag t1 = handler.getTag("Something else");
            ITag t2 = handler.createTag("First tag");
            assertNotEquals(t1, t2);
            handler.delete(t2.getName());
            ITag t3 = handler.createTag("First tag");
            assertNotEquals(t1, t2);
            assertNotEquals(t1, t3);
            assertNotEquals(t2, t3);
        } catch (NameNotAvailableException | TagNotFoundException e){
            fail();
        }
    }

    @Test
    public void getTagTest(){
        try {
            handler.createTag("Existing tag");
        } catch (NameNotAvailableException e){
            fail();
        }
        try {
            handler.getTag("Existing tag");
        } catch (TagNotFoundException e){
            fail();
        }
        assertThrows(TagNotFoundException.class, () -> handler.getTag("non existing tag"));
    }

    @Test
    public void changeColorTest(){
        try{
            Tag t1 = factory.createTag("First Tag");
            t1.setColor("09CDda");
            assertEquals("09CDda", t1.getColor());
            t1.setColor("0099ccddddaa");
            assertNotEquals("0099ccddddaa", t1.getColor());
            t1.setColor("illgal");
            assertNotEquals("illgal", t1.getColor());
            assertEquals("09CDda", t1.getColor());
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void deleteTest(){
        try {
            Tag t1 = factory.createTag("Tag");
            assertFalse(t1.isDeleted());
            t1.delete();
            assertTrue(t1.isDeleted());
        } catch (NameNotAvailableException e) {
            e.printStackTrace();
        }
    }

}
