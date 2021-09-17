package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a documented note containing text and the point in time that it was created
 */
public class Note {

    private String text;
    private final LocalDateTime pointOfCreation;

    /**
     * Creates an instance of Note containing the given text
     * Sets the text to an empty String and the point of creation to the current time
     * @param text the string to be stored
     */
    public Note(String text) {
        this.text = text;
        this.pointOfCreation = LocalDateTime.now();
    }

    /**
     * Creates an instance of Note containing default values as if it were empty
     * Sets the text to an empty String and the point of creation to the current time
     */
    public Note() {
        this("");
    }

    /**
     * Gives the text contained in the note
     * @return a String
     */
    public String viewNote() {
        return this.text;
    }

    /**
     * Sets the text contained in the note to the given text
     * @param newText a new String
     * @return the given String
     */
    public String editNote(String newText) {
        this.text = newText;
        return newText;
    }

    /**
     * Gives the date of creation as a String
      * @return a String
     */
    public String dateToString() {
        return viewDate().toString();
    }

    /**
     * Gives the time of creation as a String
     * @return a String
     */
    public String timeToString() {
        return viewTime().toString();
    }

    /**
     * Gives the date of creation
     * @return a date consisting of year, month and day
     */
    protected LocalDate viewDate() {
        return pointOfCreation.toLocalDate();
    }

    /**
     * Gives the time of creation
     * @return a time consisting of hours, minutes, seconds and nanoseconds
     */
    protected LocalTime viewTime() {
        return pointOfCreation.toLocalTime();
    }


}
