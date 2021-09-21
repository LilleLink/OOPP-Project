package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

public class TagFactoryTest {
    /*
    TagFactory factory;

    @Before
    public void preTest(){
        factory = new TagFactory();
    }

    @Test
    public void creationTest(){
        ArrayList<UUID> ids = new ArrayList<>();
        UUID id1 = factory.createTag("First tag");
        UUID id2 = factory.createTag("Second Tag");
        ids.add(id1);
        ids.add(id2);
        ArrayList<Tag> tags = factory.getTag(ids);
        assertNotEquals(tags.get(0).getName(), tags.get(1).getName());
    }

    @Test
    public void noDuplicateTest(){
        ArrayList<UUID> ids = new ArrayList<>();
        UUID id1 = factory.createTag("First tag");
        UUID id2 = factory.createTag("First tag");
        ids.add(id1);
        ids.add(id2);
        ArrayList<Tag> tags = factory.getTag(ids);
        assertEquals(tags.get(0).getName(), tags.get(1).getName());
    }

    @Test
    public void renameTest(){
        UUID id1 = factory.createTag("First tag");
        assert factory.renameTo(id1, "new name");
        assertEquals("new name", factory.getName(id1));
    }

    @Test
    public void renameToDuplicateNameTest(){
        UUID id1 = factory.createTag("First tag");
        UUID id2 = factory.createTag("Second tag");
        assertFalse( factory.renameTo(id1, "Second tag"));
        assertNotEquals(factory.getName(id1), factory.getName(id2));
    }

     */
}
