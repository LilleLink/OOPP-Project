package model.notes;

import model.ICacheVisitable;
import model.ICacheVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a list of {@link Note} objects with restricted operations.
 * Note objects contained in the list are sorted after age using the {@link java.time.LocalDateTime} class.
 *
 * @author Simon Johnsson
 * @see java.time.LocalDateTime
 */
public class NoteBook implements ICacheVisitable, IDocumentable {

    // Elements represented as a list of note objects
    private final List<Note> elements;

    /**
     * Default constructor containing an empty list.
     */
    public NoteBook() {
        elements = new ArrayList<>();
    }

    /**
     * Copy constructor.
     * Instantiates a new sorted list from the list contained in the given notes.
     *
     * @param oldNoteBook the notes to be copied
     */
    public NoteBook(NoteBook oldNoteBook) {
        this.elements = new ArrayList<>(oldNoteBook.elements);
        elements.sort(Note::compareTo);
    }

    /**
     * Copy constructor for setting a selected list of note in this.
     *
     * @param notes the new list of elements
     */
    public NoteBook(List<Note> notes) {
        this.elements = notes;
        elements.sort(Note::compareTo);
    }

    /**
     * Instantiates an empty note with default constructor and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     */
    public void addNote() {
        elements.add(new Note());
    }

    /**
     * Instantiates a note with the given string and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     *
     * @param text the String of text to be contained in the note.
     */
    public void addNote(String text) {
        elements.add(new Note(text));
    }

    /**
     * Removes the Note from the list of elements at the specified position.
     *
     * @param index the index of the note to be removed.
     */
    public void removeNote(int index) {
        elements.remove(index);
    }

    /**
     * Gives the current number of elements in notes
     *
     * @return the number of elements
     */
    public int sizeOfNotes() {
        return elements.size();
    }

    /**
     * Gives the text for the note at the given index.
     *
     * @param index the index of the note to view
     * @return the text of the viewed note
     */
    public String viewNote(int index) {
        return elements.get(index).viewNote();
    }

    /**
     * Gives the note at the given index.
     *
     * @param index the index of the note to receive
     * @return a note
     */
    public Note getNote(int index) {
        return new Note(elements.get(index));
    }

    /**
     * Returns the last note added.
     *
     * @return the last added note
     */
    public Note getLastAddedNote() {
        return new Note(elements.get(elements.size() - 1));
    }

    /**
     * Replaces the note at the given index with a new one containing the given text.
     * The list order is unaffected.
     *
     * @param index the index of the note being edited
     * @param text  the new text
     */
    public void editNote(int index, String text) {
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
        NoteBook noteBook = (NoteBook) o;
        return elements.equals(noteBook.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    public NoteBook(NotesCache cache) {
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

    @Override
    public String toString() {
        return "Notes{" +
                "elements=" + elements +
                '}';
    }
}
