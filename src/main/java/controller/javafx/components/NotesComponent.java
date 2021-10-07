package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Note;
import model.Notes;
import java.util.Objects;

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
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    private Node selected;
    /**
     * Instantiates a component representing the given Notes object.
     * @param notes the notes to represent.
     */
    public NotesComponent(Notes notes) {
        super();
        this.notes = notes;
        initializeNotes();
        addButton.setOnAction(this::addNote);
        removeButton.setOnAction(this::removeNote);
        editButton.setOnAction(this::editNote);
    }

    /**
     * Creates {@link NoteCard} objects for every note initially contained in Notes and adds them
     * to the VBox.
     */
    private void initializeNotes() {
        for(Note note : notes.getSortedElem()) {
            addToVBox(note);
        }
    }

    private void addToVBox(Note note) {
        NoteCard noteCard = new NoteCard(note);
        noteCard.getPane().setOnMouseClicked(this::updateSelected);
        noteVBox.getChildren().add(new NoteCard(note).getPane());
    }

    /**
     * Sets the selected node to the one being clicked on.
     * @param mouseEvent the currently given mouse input.
     */
    @FXML
    private void updateSelected(MouseEvent mouseEvent) {
        selected = (Node) mouseEvent.getSource();
    }

    /**
     * Removes a note from the notes object and the related card from the vbox.
     * @param actionEvent the input event.
     */
    @FXML
    private void removeNote(ActionEvent actionEvent) {
        if(!Objects.isNull(selected)) {
            int noteIndex = noteVBox.getChildren().indexOf(selected);
            notes.removeNote(noteIndex);
            noteVBox.getChildren().remove(selected);
        }
    }

    /**
     * Adds a note containing the current text in the text area.
     * A new {@link NoteCard} is instanced with the string contained in the text area.
     * @param actionEvent the user input
     */
    @FXML
    private void addNote(ActionEvent actionEvent) {
        notes.addNote(inputTextArea.getText());
        addToVBox(notes.getNoteAt(notes.size() - 1));
    }

    /**
     * Edits a note with the current text in the text area.
     * A new {@link NoteCard} is instanced with the edited note and replaces the current card.
     * @param actionEvent the user input
     */
    private void editNote(ActionEvent actionEvent) {
        if(!Objects.isNull(selected)) {
            int noteIndex = noteVBox.getChildren().indexOf(selected);
            notes.editNoteAt(noteIndex,inputTextArea.toString());
            noteVBox.getChildren().set(noteIndex,new NoteCard(notes.getNoteAt(noteIndex)).getPane());
        }
    }




}
