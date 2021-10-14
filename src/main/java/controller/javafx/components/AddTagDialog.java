package controller.javafx.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Contact;
import model.ITag;
import model.TagHandler;

import java.util.ArrayList;

public class AddTagDialog extends ViewComponent {

    @FXML private Button createTagButton;

    @FXML private ComboBox<CheckBox> tagComboBox;

    private final Contact contact;

    private final TagHandler tagHandler;

    private final ArrayList<ITag> availableTags = new ArrayList<>();

    private final ArrayList<ITag> selectedTags = new ArrayList<>();

    AddTagDialog(Contact contact, TagHandler tagHandler){
        super();
        this.contact = contact;
        this.tagHandler = tagHandler;
        updateAvailableTags();
        initCombobox();
        createTagButton.setOnAction(actionEvent -> {
            new CreateTagDialog(tagHandler).displayAndWait();
            initCombobox();
        });
    }

    private void initCombobox() {
        tagComboBox.getItems().clear();
        updateAvailableTags();
        CheckBox box;
        for (ITag tag: availableTags){
            box = new CheckBox(tag.toString());
            box.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue){
                    selectedTags.add(tag);
                } else {
                    selectedTags.remove(tag);
                }
            });
            tagComboBox.getItems().add(box);
        }
    }

    private void updateAvailableTags() {
        availableTags.clear();
        availableTags.addAll(tagHandler.getAllTags());
        availableTags.removeAll(contact.getTags());
    }

    void displayAndWait(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 400, 200);

        stage.setTitle("Add tag to contact");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> contact.addAllTags(selectedTags));
        stage.showAndWait();
    }

}
