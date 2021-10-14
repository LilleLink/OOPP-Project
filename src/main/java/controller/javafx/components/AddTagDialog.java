package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Contact;
import model.TagHandler;

public class AddTagDialog extends ViewComponent {

    @FXML private Button createTagButton;

    private final Contact contact;

    AddTagDialog(Contact contact, TagHandler tagHandler){
        super();
        this.contact = contact;
        initCombobox();
        createTagButton.setOnAction(actionEvent -> {
            new CreateTagDialog(tagHandler).displayAndWait();
            initCombobox();
        });
    }

    private void initCombobox() {

    }

    void displayAndWait(){

    }

}
