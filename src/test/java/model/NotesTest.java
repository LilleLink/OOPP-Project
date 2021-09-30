package model;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class NotesTest {

    @Test
    public void sizeIncreasedTest() {
        Notes notes = new Notes();
        int oSize = notes.size();
        notes.addNote();
        assert(oSize == notes.size() - 1);
    }

    @Test
    public void sizeDecreasedTest() {
        Notes notes = new Notes();
        notes.addNote();
        int oSize = notes.size();
        notes.removeNote(0);
        assert(oSize == notes.size() + 1);
    }

    @Test
    public void noteIsEditedTest() {
        Notes notes = new Notes();
        notes.addNote("Pizza");
        String oText = notes.viewNoteAt(0);
        notes.editNoteAt(0,"Taco");
        String nText = notes.viewNoteAt(0);
        assertNotEquals(oText, nText);
    }

    @Test
    public void transferNotesTest() {
        Notes notes = new Notes();
        notes.addNote("Pizza");
        Notes tNotes = new Notes(notes);
        assertEquals(notes,tNotes);
    }

    @Test
    public void notesSortedTest() {
        Notes notes = new Notes();
        notes.addNote();
        for(int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1); // must wait a very small amount of time to be considered different times
                notes.addNote();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Note> list = notes.getSortedElem();
        boolean sorted;
        for(int i = 0; i < notes.size() - 1; i++) {
            Note note = list.get(i);
            Note other = list.get(i+1);
            sorted = note.compareAge(other) < 0;
            assert(sorted);
        }
    }
}
