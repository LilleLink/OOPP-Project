package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Note;

/**
 * A card representing a note.
 * Contains the point of creation and the text contained in the note.
 * {@author Simon Johnsson}
 * Used by {@link NotesComponent}
 */
public class NoteCard extends ViewComponent {

    @FXML
    private TextArea noteTextArea;
    @FXML
    private Label dateLabel;

    public NoteCard(Note note) {
        super();
        noteTextArea.setText(note.viewNote());
        dateLabel.setText("Created: " + note.dateToString() + " " + note.timeToString());
    }

}
