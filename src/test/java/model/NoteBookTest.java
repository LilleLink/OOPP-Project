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
        int oSize = notes.sizeOfNotes();
        notes.addNote();
        assert(oSize == notes.sizeOfNotes() - 1);
    }

    @Test
    public void sizeDecreasedTest() {
        NoteBook notes = new NoteBook();
        notes.addNote();
        int oSize = notes.sizeOfNotes();
        notes.removeNote(0);
        assert(oSize == notes.sizeOfNotes() + 1);
    }

    @Test
    public void noteIsEditedTest() {
        NoteBook notes = new NoteBook();
        notes.addNote("Pizza");
        String oText = notes.viewNote(0);
        notes.editNote(0,"Taco");
        String nText = notes.viewNote(0);
        assertNotEquals(oText, nText);
    }

    @Test
    public void transferNotesTest() {
        NoteBook notes = new NoteBook();
        notes.addNote("Pizza");
        NoteBook tNotes = new NoteBook(notes);
        assertEquals(notes,tNotes);
    }

    @Test
    public void notesSortedTest() {
        NoteBook noteBook = new NoteBook();
        noteBook.addNote();
        for(int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1); // must wait a very small amount of time to be considered different times
                noteBook.addNote();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Note> list = noteBook.getSortedList();
        boolean sorted;
        for(int i = 0; i < noteBook.sizeOfNotes() - 1; i++) {
            Note note = list.get(i);
            Note other = list.get(i+1);
            sorted = note.compareAge(other) < 0;
            assert(sorted);
        }
    }
    @Test
    public void consistentHashTest() {
        NoteBook noteBook = new NoteBook();
        noteBook.addNote();
        int firstHash = noteBook.hashCode();
        int secondHash = noteBook.hashCode();
        assertEquals(firstHash,secondHash);
    }

    @Test
    public void copiedListEqualsTest() {
        NoteBook notes = new NoteBook();
        notes.addNote("Bamse");
        NoteBook copiedNotes = new NoteBook(notes.getSortedList());
        assert(notes.equals(copiedNotes));
    }

    @Test
    public void changedCopyNotEqualsTest() {
        NoteBook notes = new NoteBook();
        notes.addNote("Bamse");
        NoteBook copiedNotes = new NoteBook(notes);
        copiedNotes.addNote("Lille-Skutt");
        assert(!notes.equals(copiedNotes));
    }

}
