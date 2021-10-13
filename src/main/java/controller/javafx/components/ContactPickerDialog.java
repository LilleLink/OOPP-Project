package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.ContactList;

import java.util.ArrayList;
import java.util.List;

class ContactPickerDialog extends ViewComponent {

    @FXML private AnchorPane searchBarAnchorPane;
    @FXML private VBox contactCardVBox;
    @FXML private Button saveButton;

    ContactList contactList;
    List<ContactPickerContactCard> contactPickerContactCardList = new ArrayList<>();
    List<Contact> pickedContacts = new ArrayList<>();

    ContactPickerDialog(ContactList contactList) {
        this.contactList = contactList;
        initContactPickerContactCardList();
        saveButton.setOnAction(this::saveAndClose);
        displayAndWait();
    }

    public List<Contact> getPickedContacts() {
        return pickedContacts;
    }

    private void saveAndClose(ActionEvent actionEvent) {
        for (ContactPickerContactCard cpcc : contactPickerContactCardList) {
            if (cpcc.isSelected()) {
                pickedContacts.add(cpcc.getContact());
            }
        }
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void displayAndWait() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene =  new Scene(this.getPane());
        stage.setTitle("Pick contacts");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void initContactPickerContactCardList() {
        for (Contact contact : contactList.getList()) {
            ContactPickerContactCard contactPickerContactCard = new ContactPickerContactCard(contact);
            contactPickerContactCardList.add(contactPickerContactCard);
            contactCardVBox.getChildren().add(contactPickerContactCard.getPane());
        }
    }

}