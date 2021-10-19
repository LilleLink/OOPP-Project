package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.ITag;
import model.TagHandler;

import java.util.ArrayList;
import java.util.List;

public class AddTagDialog extends ViewComponent {

    private final Stage stage = new Stage();

    @FXML
    private Button createTagButton;

    @FXML
    private VBox tagContainer;

    @FXML
    private Button addTagsButton;

    @FXML
    private Button cancelButton;

    private final Contact contact;

    private final TagHandler tagHandler;

    private final ArrayList<ITag> selectedTags = new ArrayList<>();

    AddTagDialog(Contact contact, TagHandler tagHandler) {
        super();
        this.contact = contact;
        this.tagHandler = tagHandler;
        updateTagContainer();
        createTagButton.setOnAction(actionEvent -> {
            new CreateTagDialog(tagHandler);
            updateTagContainer();
        });
        cancelButton.setOnAction(actionEvent -> close());
        addTagsButton.setOnAction(actionEvent -> save());
        displayAndWait();
    }

    private void updateTagContainer() {
        tagContainer.getChildren().clear();
        List<ITag> availableTags = tagHandler.getAllTags();
        availableTags.removeAll(contact.getTags());
        CheckBox checkBox;
        for (ITag tag : availableTags) {
            checkBox = new CheckBox(tag.toString());
            checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedTags.add(tag);
                } else {
                    selectedTags.remove(tag);
                }
            });
            if (selectedTags.contains(tag)) {
                checkBox.setSelected(true);
            }
            checkBox.setStyle("-fx-background-color: " + tag.getColor() + ";");
            tagContainer.getChildren().add(checkBox);
        }
    }

    private void save() {
        contact.addAllTags(selectedTags);
        close();
    }

    private void close() {
        stage.close();
    }

    private void keyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case ESCAPE:
                close();
                break;
            case ENTER:
                save();
                break;
        }
    }

    private void displayAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 400, 250);

        stage.setTitle("Add tag to contact");
        stage.setScene(scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
        stage.showAndWait();
    }

}
