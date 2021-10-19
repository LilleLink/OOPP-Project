package model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class NotesTest {

    @Test
    public void sizeIncreasedTest() {
        Notes notes = new Notes();
        int oSize = notes.size();
        notes.add();
        assert (oSize == notes.size() - 1);
    }

    @Test
    public void sizeDecreasedTest() {
        Notes notes = new Notes();
        notes.add();
        int oSize = notes.size();
        notes.removeAt(0);
        assert (oSize == notes.size() + 1);
    }

    @Test
    public void noteIsEditedTest() {
        Notes notes = new Notes();
        notes.add("Pizza");
        String oText = notes.viewAt(0);
        notes.editAt(0, "Taco");
        String nText = notes.viewAt(0);
        assertNotEquals(oText, nText);
    }

    @Test
    public void transferNotesTest() {
        Notes notes = new Notes();
        notes.add("Pizza");
        Notes tNotes = new Notes(notes);
        assertEquals(notes, tNotes);
    }

    @Test
    public void notesSortedTest() {
        Notes notes = new Notes();
        notes.add();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1); // must wait a very small amount of time to be considered different times
                notes.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Note> list = notes.getSortedList();
        boolean sorted;
        for (int i = 0; i < notes.size() - 1; i++) {
            Note note = list.get(i);
            Note other = list.get(i + 1);
            sorted = note.compareAge(other) < 0;
            assert (sorted);
        }
    }

    @Test
    public void consistentHashTest() {
        Notes notes = new Notes();
        notes.add();
        int firstHash = notes.hashCode();
        int secondHash = notes.hashCode();
        assertEquals(firstHash, secondHash);
    }

    @Test
    public void copiedListEqualsTest() {
        Notes notes = new Notes();
        notes.add("Bamse");
        Notes copiedNotes = new Notes(notes.getSortedList());
        assert (notes.equals(copiedNotes));
    }

    @Test
    public void changedCopyNotEqualsTest() {
        Notes notes = new Notes();
        notes.add("Bamse");
        Notes copiedNotes = new Notes(notes);
        copiedNotes.add("Lille-Skutt");
        assert (!notes.equals(copiedNotes));
    }

    @Test
    public void getLastAddedTest() {
        Notes notes = new Notes();
        notes.add("Hello");
        notes.add("There");
        notes.add("General");
        assertNotEquals(notes.getAt(1), notes.getLastAdded());
        assertEquals(notes.getAt(2), notes.getLastAdded());
    }

    @Test
    public void toStringTest() {
        Notes notes = new Notes();
        notes.add("A");
        notes.add("B");
        assertEquals("Notes{elements=[A, B]}", notes.toString());
    }

}
