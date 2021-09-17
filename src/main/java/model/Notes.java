package model;

import java.util.ArrayList;

/**
 * Represents a collection of Note objects with composite operations.
 */
public class Notes {

    private final ArrayList<Note> elements;

    public Notes() {
        elements = new ArrayList<>();
    }

    public Notes(Notes oldNotes) {
        this.elements = new ArrayList<>(oldNotes.elements);
    }

    /**
     * Instantiates an empty Note and appends it to the list of elements.
     */
    public void addNote() {
        elements.add(new Note());
    }

    /**
     * Instantiates a Note with the given string and appends it to the list of elements.
     * @param text the String of text to be contained in the note.
     */
    public void addNote(String text) {
        elements.add(new Note(text));
    }

    /**
     * Removes the Note from the list of elements at the specified position.
     * @param index the index of the Note to be removed.
     */
    public void removeNote(int index) {
        elements.remove(index);
    }

    /**
     * Gives the current amount of notes
     * @return an integer
     */
    public int size() {
        return elements.size();
    }


}
