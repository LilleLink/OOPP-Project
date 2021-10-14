package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
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
class NotesComponent extends ViewComponent{

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

    /**
     * The
     */
    private Node selected;
    /**
     * Instantiates a component representing the given Notes object.
     * @param notes the notes to represent
     */
    NotesComponent(Notes notes) {
        super();
        this.notes = notes;
        initializeNotes();
        addButton.setOnAction(this::addNote);
        removeButton.setOnAction(this::removeNote);
        editButton.setOnAction(this::editNote);
        inputTextArea.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                addNote(keyEvent);
                inputTextArea.clear();
            }
        });
    }

    /**
     * Creates {@link NoteCard} objects for every note initially contained in Notes and adds them
     * to the VBox.
     */
    private void initializeNotes() {
        for(Note note : notes.getSortedElem()) {
            addToVBox(createCard(note));
        }
    }

    /**
     * Adds the given card to the VBox.
     * @param card the card to add
     */
    private void addToVBox(NoteCard card) {
        noteVBox.getChildren().add(card.getPane());
    }

    /**
     * Sets the element at the given index to a note card created from the given note.
     * @param index the index of the element to set
     * @param card the replacing card
     */
    private void setVBoxElement(int index, NoteCard card){
        noteVBox.getChildren().set(index,card.getPane());
    }

    /**
     * Creates a card of the given note and sets the on click method for it.
     * @param note the note to represent as a card
     * @return the created card
     */
    private NoteCard createCard(Note note) {
        NoteCard card = new NoteCard(note);
        card.getPane().setOnMouseClicked(this::updateSelected);
        return card;
    }


    /**
     * Sets the selected node to the one being clicked on.
     * @param mouseEvent the currently given mouse input.
     */
    private void updateSelected(MouseEvent mouseEvent) {
        if(isValidSelected( (Node) mouseEvent.getSource()))
        selected = (Node) mouseEvent.getSource();
    }

    /**
     * Validates the selected node.
     * @return true if the selected node is valid
     */
    private boolean isValidSelected(Node node) {
        return (!Objects.isNull(node) && noteVBox.getChildren().contains(node));
    }


    /**
     * Removes a note from the notes object and the related card from the vbox.
     * Updates selected to null.
     * @param actionEvent the input event.
     */
    private void removeNote(ActionEvent actionEvent) {
            int noteIndex = noteVBox.getChildren().indexOf(selected);
            notes.removeNote(noteIndex);
            noteVBox.getChildren().remove(selected);
            this.selected = null;
    }

    /**
     * Adds a note containing the current text in the text area.
     * A new {@link NoteCard} is instanced with the string contained in the text area.
     * @param event the user input
     */
    private void addNote(Event event) {
        notes.addNote(inputTextArea.getText());
        addToVBox(createCard(notes.getLastAdded()));
    }

    /**
     * Edits a note with the current text in the text area.
     * A new {@link NoteCard} is instanced with the edited note and replaces the current card.
     * @param actionEvent the user input
     */
    private void editNote(ActionEvent actionEvent) {
            int noteIndex = noteVBox.getChildren().indexOf(selected);
            notes.editNoteAt(noteIndex,inputTextArea.getText());
            setVBoxElement(noteIndex,createCard(notes.getLastAdded()));
            selected = null;
    }




}
