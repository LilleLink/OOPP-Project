package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a list of {@link Note} objects with restricted operations.
 * Note objects contained in the list are sorted after age using the {@link java.time.LocalDateTime} class.
 *
 * @see java.time.LocalDateTime
 * @author Simon Johnsson
 */
public class Notes implements ICacheVisitable {

    // Elements represented as a list of note objects
    private final List<Note> elements;

    /**
     * Default constructor containing an empty list.
     */
    Notes() {
        elements = new ArrayList<>();
    }

    /**
     * Copy constructor.
     * Instantiates a new sorted list from the list contained in the given notes.
     *
     * @param oldNotes the notes to be copied
     */
    Notes(Notes oldNotes) {
        this.elements = new ArrayList<>(oldNotes.elements);
        elements.sort(Note::compareTo);
    }

    /**
     * Copy constructor for setting a selected list of note in this.
     *
     * @param notes the new list of elements
     */
    public Notes(List<Note> notes) {
        this.elements = notes;
        elements.sort(Note::compareTo);
    }

    /**
     * Instantiates an empty note with default constructor and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     */
    public void add() {
        elements.add(new Note());
    }

    /**
     * Instantiates a note with the given string and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     *
     * @param text the String of text to be contained in the note.
     */
    public void add(String text) {
        elements.add(new Note(text));
    }

    /**
     * Removes the Note from the list of elements at the specified position.
     *
     * @param index the index of the note to be removed.
     */
    public void removeAt(int index) {
        elements.remove(index);
    }

    /**
     * Gives the current number of elements in notes
     *
     * @return the number of elements
     */
    public int size() {
        return elements.size();
    }

    /**
     * Gives the text for the note at the given index.
     *
     * @param index the index of the note to view
     * @return the text of the viewed note
     */
    public String viewAt(int index) {
        return elements.get(index).viewNote();
    }

    /**
     * Gives the note at the given index.
     *
     * @param index the index of the note to receive
     * @return a note
     */
    public Note getAt(int index) {
        return elements.get(index);
    }

    /**
     * Returns the last note added.
     *
     * @return the last added note
     */
    public Note getLastAdded() {
        return elements.get(elements.size() - 1);
    }

    /**
     * Replaces the note at the given index with a new one containing the given text.
     * The list order is unaffected.
     *
     * @param index the index of the note being edited
     * @param text  the new text
     */
    public void editAt(int index, String text) {
        Note note = elements.get(index).withText(text);
        elements.set(index, note);
    }

    /**
     * Returns a copy of the list of note objects.
     *
     * @return the list of notes
     */
    public List<Note> getSortedList() {
        List<Note> list = new ArrayList<>(elements);
        list.sort(Note::compareTo);
        return list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return elements.equals(notes.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    public Notes(NotesCache cache) {
        this.elements = new ArrayList<>(cache.elements);
    }

    public static class NotesCache {
        public List<Note> elements;
    }

    private NotesCache getCache() {
        NotesCache cache = new NotesCache();
        cache.elements = new ArrayList<>(this.elements);
        return cache;
    }

    @Override
    public <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env) {
        return visitor.visit(this.getCache(), env);
    }
}
