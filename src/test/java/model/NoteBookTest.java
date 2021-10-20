package model;

import model.notes.NoteBook;
import model.notes.Note;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class NoteBookTest {

    @Test
    public void sizeIncreasedTest() {
        NoteBook notes = new NoteBook();
        int oSize = notes.size();
        notes.add();
        assert(oSize == notes.size() - 1);
    }

    @Test
    public void sizeDecreasedTest() {
        NoteBook notes = new NoteBook();
        notes.add();
        int oSize = notes.size();
        notes.removeAt(0);
        assert(oSize == notes.size() + 1);
    }

    @Test
    public void noteIsEditedTest() {
        NoteBook notes = new NoteBook();
        notes.add("Pizza");
        String oText = notes.viewAt(0);
        notes.editAt(0,"Taco");
        String nText = notes.viewAt(0);
        assertNotEquals(oText, nText);
    }

    @Test
    public void transferNotesTest() {
        NoteBook notes = new NoteBook();
        notes.add("Pizza");
        NoteBook tNotes = new NoteBook(notes);
        assertEquals(notes,tNotes);
    }

    @Test
    public void notesSortedTest() {
        NoteBook noteBook = new NoteBook();
        noteBook.add();
        for(int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1); // must wait a very small amount of time to be considered different times
                noteBook.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Note> list = noteBook.getSortedList();
        boolean sorted;
        for(int i = 0; i < noteBook.size() - 1; i++) {
            Note note = list.get(i);
            Note other = list.get(i+1);
            sorted = note.compareAge(other) < 0;
            assert(sorted);
        }
    }
    @Test
    public void consistentHashTest() {
        NoteBook noteBook = new NoteBook();
        noteBook.add();
        int firstHash = noteBook.hashCode();
        int secondHash = noteBook.hashCode();
        assertEquals(firstHash,secondHash);
    }

    @Test
    public void copiedListEqualsTest() {
        NoteBook notes = new NoteBook();
        notes.add("Bamse");
        NoteBook copiedNotes = new NoteBook(notes.getSortedList());
        assert(notes.equals(copiedNotes));
    }

    @Test
    public void changedCopyNotEqualsTest() {
        NoteBook notes = new NoteBook();
        notes.add("Bamse");
        NoteBook copiedNotes = new NoteBook(notes);
        copiedNotes.add("Lille-Skutt");
        assert(!notes.equals(copiedNotes));
    }

}
