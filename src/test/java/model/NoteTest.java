package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class NoteTest {

    @Test
    public void timeOfCreationTest() {
        Note note = new Note();
        LocalTime time = LocalTime.now();
        int diff = note.viewTime().getSecond() - time.getSecond();
        assert (diff <= 2);
    }

    @Test
    public void dateOfCreationTest() {
        Note note = new Note();
        LocalDate date = LocalDate.now();
        assert (note.viewDate().equals(date));
    }

    @Test
    public void compareAgeOlderTest() {
        Note note = new Note();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Note newNote = new Note();
        int diff = newNote.compareAge(note);
        assert (diff > 0);

    }

    @Test
    public void sizeIncreaseTest() {
        Note note = new Note();
        int oldSize = note.size();
        note = note.withText("Pizza");
        int newSize = note.size();
        assert (oldSize < newSize);
    }

    @Test
    public void timeStringNotNullTest() {
        Note note = new Note();
        String text = note.timeToString();
        assertNotEquals(null, text);
    }

    @Test
    public void dateStringNotNullTest() {
        Note note = new Note();
        String text = note.dateToString();
        assertNotEquals(null, text);
    }

    @Test
    public void noteTextEqualsTest() {
        Note firstNote = new Note("Text");
        Note secondNote = new Note("Text");
        assert (firstNote.equals(secondNote));
        assertEquals(firstNote.toString(), secondNote.toString());
    }


}
