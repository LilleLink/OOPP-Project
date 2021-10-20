package model.notes;

import model.ICacheVisitable;
import model.ICacheVisitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Represents a documented note containing text and the point in time that it was created.
 *
 * @author Simon Johnsson
 */
public class Note implements Comparable<Note>, ICacheVisitable {

    // Resembles the content of the note
    private final String text;
    // The date and time the note was created
    private final LocalDateTime pointOfCreation;

    /**
     * Constructs a note containing the given text.
     * Sets the text to an empty String and the point of creation to the current time.
     *
     * @param text the string to be stored
     */
    public Note(String text) {
        this.text = text;
        this.pointOfCreation = LocalDateTime.now();
    }

    /**
     * Copies the given note to a new note.
     * @param oldNote the note to copy
     */
    public Note(Note oldNote) {
        this(oldNote.text,oldNote.pointOfCreation);
    }

    /**
     * Constructs Note containing default values as if it were empty.
     * Sets the text to an empty String and the point of creation to the current time.
     */
    public Note() {
        this("");
    }

    /**
     * Private constructor for instancing a note with the given text, date and time.
     *
     * @param text            a String
     * @param pointOfCreation a date and time
     */
    private Note(String text, LocalDateTime pointOfCreation) {
        this.text = text;
        this.pointOfCreation = pointOfCreation;
    }

    /**
     * Gives the text contained in the note.
     *
     * @return a String
     */
    public String viewNote() {
        return this.text;
    }

    /**
     * Returns an adjusted copy containing the given text and keeps this point of creation.
     * Uses mutate by copy.
     *
     * @param newText a new String
     * @return a new Note containing the given text
     */
    public Note withText(String newText) {
        return new Note(newText, pointOfCreation);
    }

    /**
     * Gives the date of creation as a String.
     *
     * @return a String
     */
    public String dateToString() {
        return viewDate().toString();
    }

    /**
     * Gives the time of creation as a String containing numerical hour and minute.
     * <p>
     * The output will be HH:MM.
     * </p>
     *
     * @return a String
     */
    public String timeToString() {
        LocalTime time = pointOfCreation.toLocalTime();
        return time.getHour() + ":" + time.getMinute();
    }

    /**
     * Compares the time and date of this note to another note.
     * The comparison is firstly based on the date. Compares time if dates are considered equal.
     *
     * @return the comparator value, negative if this is older, positive if this is newer
     */
    public int compareAge(Note other) {
        int cmp = viewDate().compareTo(other.viewDate());
        if (cmp == 0) {
            cmp = viewTime().compareTo(other.viewTime());
        }
        return cmp;
    }

    /**
     * Returns the number of characters contained in the text.
     *
     * @return an integer
     */
    public int size() {
        return text.length();
    }

    /**
     * Gives the immutable date of creation.
     *
     * @return a date consisting of year, month and day
     */
    public LocalDate viewDate() {
        return pointOfCreation.toLocalDate();
    }

    /**
     * Gives the immutable time of creation.
     *
     * @return a time consisting of hours, minutes, seconds and nanoseconds
     */
    public LocalTime viewTime() {
        return pointOfCreation.toLocalTime();
    }

    //-----------------------------------------------------------------------

    /**
     * Compares this note to another note.
     * <p>
     * The comparison is based on the date and time of creation.
     * If this note is considered older, a negative value is returned.
     * If this note is considered newer, a positive value is returned.
     *
     * </p>
     * Note: this class has a natural ordering that is inconsistent with equals
     *
     * @param o the other note to compare to, not null
     * @return the comparator value, negative if considered less, positive if considered greater
     */
    @Override // Override for JavaDoc
    public int compareTo(Note o) {
        return compareAge(o);
    }

    /**
     * Determines whether an object is equal to this note
     *
     * @param other the object to be checked
     * @return if the object equals this
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        Note otherNote = (Note) other;
        return otherNote.viewNote().equals(text);
    }

    public Note(NoteCache cache) {
        this.text = cache.text;
        this.pointOfCreation = cache.pointOfCreation;
    }

    public static class NoteCache {
        public String text;
        public LocalDateTime pointOfCreation;
    }

    private NoteCache getCache() {
        NoteCache cache = new NoteCache();
        cache.text = text;
        cache.pointOfCreation = pointOfCreation;
        return cache;
    }

    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }

    @Override
    public String toString() {
        return "Note{" +
                "text='" + text + '\'' +
                ", pointOfCreation=" + pointOfCreation +
                '}';
    }
}
