package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a list of Note objects with restricted operations.
 * Note objects contained in the list are sorted after age using the LocalDateTime class.
 * @see java.time.LocalDateTime
 */
public class Notes implements ICacheVisitable{

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
     * @param oldNotes the notes to be copied
     */
    Notes(Notes oldNotes) {
        this.elements = new ArrayList<>(oldNotes.elements);
        elements.sort(Note::compareTo);
    }

    /**
     * Private copy constructor for setting a selected list of note in this.
     * @param notes the new list of elements
     */
    private Notes(List<Note> notes) {
        this.elements = notes;
        elements.sort(Note::compareTo);
    }

    /**
     * Instantiates an empty note with default constructor and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     * The list of elements contained in the old Notes object is unaffected.
     * @return a notes object where an empty note is added last
     */
    Notes addNote() {
        List<Note> list = getSortedElem();
        list.add(new Note());
        return new Notes(list);
    }

    /**
     * Instantiates a note with the given string and appends it to the list of elements.
     * New note objects will always be newer than the object last added to the list.
     * The list of elements contained in the old Notes object is unaffected.
     * @param text the String of text to be contained in the note.
     * @return a notes object where a note of the specified text is added last
     */
    Notes addNote(String text) {
        List<Note> list = getSortedElem();
        list.add(new Note(text));
        return new Notes(list);
    }

    /**
     * Removes the Note from the list of elements at the specified position.
     * The list of elements contained in the old Notes object is unaffected.
     * @param index the index of the note to be removed.
     * @return a notes object where the note of the specified index is removed
     */
    Notes removeNote(int index) {
        List<Note> list = getSortedElem();
        list.remove(index);
        return new Notes(list);
    }

    /**
     * Gives the current number of elements in notes
     * @return the number of elements
     */
    int size() {
        return elements.size();
    }

    /**
     * Gives the text for the note at the given index.
     * @param index the index of the note to view
     * @return the text of the viewed note
     */
    String viewNoteAt(int index) {
        return elements.get(index).viewNote();
    }

    /**
     * Replaces the note at the given index with a new one containing the given text.
     * The list order is unaffected.
     * The list of elements contained in the old Notes object is unaffected.
     * @param index the index of the note being edited
     * @param text the new text
     * @return a notes object where the note of the specified index is edited
     */
    Notes editNoteAt(int index, String text) {
        Note note = elements.get(index).editNote(text);
        List<Note> list = getSortedElem();
        list.set(index, note);
        return new Notes(list);
    }

    /**
     * Returns a copy of the list of note objects.
     * @return the list of notes
     */
    List<Note> getSortedElem() {
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
