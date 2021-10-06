package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.Note;
import model.Notes;

/**
 * A component representing the interface that interacts with and views notes.
 * {@author Simon Johnsson}
 * Uses {@link NoteCard}
 */
public class NotesComponent extends ViewComponent{

    private final Notes notes;

    @FXML
    private TextArea inputTextArea;
    @FXML
    private VBox noteVBox;

    /**
     * Instantiates a component representing the given Notes object.
     * @param notes the notes to represent.
     */
    public NotesComponent(Notes notes) {
        super();
        this.notes = notes;
        initializeNotes();
    }

    /**
     * Creates cards for every note initially contained in Notes and adds them
     * to the VBox.
     */
    private void initializeNotes() {
        for(Note note : notes.getSortedElem()) {
            addToVBox(note);
        }
    }

    private void addToVBox(Note note) {
        noteVBox.getChildren().add(new NoteCard(note).getPane());
    }

    /**
     * Adds a note containing the current text in the text area.
     */
    @FXML
    public void addNote() {
        notes.addNote(inputTextArea.toString());
        addToVBox(notes.getNoteAt(notes.size() - 1));
    }


}
