package controller.javafx.components;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.ContactList;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;

public class AddContactDialog  extends ViewComponent {

    private final ContactList contacts;
    @FXML private TextField contactName;
    @FXML private Button addContactButton;
    @FXML private Button cancelButton;
    @FXML private Text errorMessageText;


    AddContactDialog(ContactList contacts){
        super();
        this.contacts = contacts;
        errorMessageText.setVisible(false);
        errorMessageText.setFill(Color.RED);
        addContactButton.setOnAction(this::btnAddContactClicked);
        cancelButton.setOnAction(this::closeStage);
        contactName.textProperty().addListener(this::textFieldChanged);
    }

    private void textFieldChanged(Observable observable) {
        errorMessageText.setVisible(false);
    }

    @FXML
    private void btnAddContactClicked(ActionEvent event) {
        try {
            contacts.addContact(contactName.getText());
        } catch (NameNotAllowedException e){
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void displayAndWait(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 300, 200);

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();
    }


}
