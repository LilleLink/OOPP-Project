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
            assertThrows(TagNotFoundException.class, () -> handler.getTag("First tag"));
            ITag t2 = handler.createTag("First tag");
            assertNotEquals(t1, t2);
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
    public void setColorTest(){
        try {
            ITag tag = handler.createTag("First Tag");
            handler.setColor(tag.getName(), "09cDDa");
            assertEquals("09cDDa", tag.getColor());
            handler.setColor(tag.getName(), "009999ccDDaA");
            assertNotEquals("009999ccDDaA", tag.getName());
            handler.setColor(tag.getName(), "ilegal");
            assertNotEquals("ilegal", tag.getColor());
            handler.setColor(tag.getName(), "FA6607");
            assertEquals("FA6607", tag.getColor());
        } catch (NameNotAvailableException | TagNotFoundException e){
            fail();
        }
    }

}
