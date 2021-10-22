package model.notes;

/**
 * An object with to ability to collect and operate upon {@link Note} objects.
 * General contract is that the implementer uses an index based collection to operate on the notes.
 */
public interface IDocumentable {

    /**
     * Adds a default construction of Note.
     */
    void addNote();

    /**
     * Adds a note with the given text.
     * @param text the text contained in the note
     */
    void addNote(String text);

    /**
     * Removes the note at the given index.
     * @param index the index of the note to remove
     */
    void removeNote(int index);

    /**
     * Edits the note at the given index to contain the given text.
     * @param index the index of the note to edit
     * @param text the new text
     */
    void editNote(int index, String text);

    /**
     * Returns the content of the note at the given index.
     * @param index the index of the viewed note
     * @return the text of the note at the given index
     */
    String viewNote(int index);

    /**
     * Returns the note at the given index.
     * @param index the index of the note to be returned
     * @return a note at the given index
     */
    Note getNote(int index);

    /**
     * Returns the last added note to the collection.
     * @return the last added note
     */
    Note getLastAddedNote();

    /**
     * Returns the size of the collection of notes
     * @return an integer representing the number of notes in the collection
     */
    int sizeOfNotes();

}
