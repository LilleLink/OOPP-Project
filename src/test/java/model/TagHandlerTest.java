package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TagHandlerTest {

    TagHandler factory;

    @Before
    public void preTest(){
        factory = new TagHandler();
    }

    @Test
    public void creationTest(){
        try {
            factory.createTag("First tag");
            factory.createTag("Second Tag");
        } catch (NameNotAvailableException e){
            fail();
        }
        ArrayList<Tag> tags = factory.getTags();
        assertNotEquals(tags.get(0).getName(), tags.get(1).getName());
    }

    @Test
    public void noDuplicateTest(){
        try {
            Tag t1 = factory.createTag("First tag");
            Tag t2 = factory.createTag("First tag");
            assertEquals(t1, t2);
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void renameTest(){
        try {
            Tag t1 = factory.createTag("First tag");
            t1.renameTo("new name");
            assertEquals("new name", t1.name);
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void renameToDuplicateNameTest(){
        try {
            Tag t1 = factory.createTag("First tag");
            Tag t2 = factory.createTag("Second tag");
            assertThrows(NameNotAvailableException.class, () -> t1.renameTo("Second tag"));
            assertNotEquals(t1.name, t2.name);
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void makeNameAvailableTest(){
        try {
            Tag t1 = factory.createTag("First tag");
            t1.renameTo("Something else");
            Tag t2 = factory.createTag("First tag");
            assertNotEquals(t1, t2);
            t2.delete();
            Tag t3 = factory.createTag("First tag");
            assertNotEquals(t1, t2);
            assertNotEquals(t1, t3);
            assertNotEquals(t2, t3);
        } catch (NameNotAvailableException e){
            fail();
        }
    }

    @Test
    public void getTagTest(){
        try {
            factory.createTag("Existing tag");
        } catch (NameNotAvailableException e){
            fail();
        }
        try {
            factory.getTag("Existing tag");
        } catch (TagNotFoundException e){
            fail();
        }
        assertThrows(TagNotFoundException.class, () -> factory.getTag("non existing tag"));
    }

}
