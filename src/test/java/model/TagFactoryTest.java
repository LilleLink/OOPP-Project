package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TagFactoryTest {

    TagFactory factory;

    @Before
    public void preTest(){
        factory = new TagFactory();
    }

    @Test
    public void creationTest(){
        factory.createTag("First tag");
        factory.createTag("Second Tag");
        ArrayList<Tag> tags = factory.getTags();
        assertNotEquals(tags.get(0).getName(), tags.get(1).getName());
    }

    @Test
    public void noDuplicateTest(){
        Tag t1 = factory.createTag("First tag");
        Tag t2 = factory.createTag("First tag");
        assertEquals(t1, t2);
    }

    @Test
    public void renameTest(){
        Tag t1 = factory.createTag("First tag");
        try {
            t1.renameTo("new name");
        } catch (NameNotAvailableException e){
            fail();
        }
        assertEquals("new name", t1.name);
    }

    @Test
    public void renameToDuplicateNameTest(){
        Tag t1 = factory.createTag("First tag");
        Tag t2 = factory.createTag("Second tag");
        assertThrows(NameNotAvailableException.class, () -> t1.renameTo("Second tag"));
        assertNotEquals(t1.name, t2.name);
    }


}
