package model;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;


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
        assert(note.viewDate().equals(date));
    }

    @Test
    public void compareAgeOlderTest() {
        Note note = new Note();
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Note newNote = new Note();
        int diff = note.compareAge(newNote);
        assert(diff < 0);
    }
}
