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
    public void editTextTest() {
        String text = "Test";
        Note note = new Note();
        String newText = note.editNote(text);
        assert(newText.equals(note.viewNote()));
    }

    @Test
    public void dateOfCreationTest() {
        Note note = new Note();
        LocalDate date = LocalDate.now();
        assert(note.viewDate().equals(date));
    }

    public void notes() {
        Notes notes = new Notes();

    }
}
