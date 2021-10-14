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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.ContactList;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;
import vcf.VCFParser;

import java.io.File;
import java.io.IOException;

class CreateContactDialog extends ViewComponent {

    private final ContactList contacts;
    @FXML private TextField contactName;
    @FXML private Button addContactButton;
    @FXML private Button cancelButton;
    @FXML private Text errorMessageText;
    @FXML private Button fileLoad;
    @FXML private Button dirLoad;


    CreateContactDialog(ContactList contacts){
        super();
        this.contacts = contacts;
        errorMessageText.setVisible(false);
        errorMessageText.setFill(Color.RED);
        addContactButton.setOnAction(this::btnAddContactClicked);
        cancelButton.setOnAction(this::closeStage);
        contactName.textProperty().addListener(this::textFieldChanged);
        fileLoad.setOnAction(this::loadContactFile);
        dirLoad.setOnAction(this::loadContactDirectory);
    }

    private void loadContactFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select a contact file (*.vcf)");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Contact file", "*.vcf"));
        Stage stage = new Stage();
        File contactFile = chooser.showOpenDialog(stage);
        if (contactFile != null) {
            readContactFile(contactFile);
        }
    }

    private void loadContactDirectory(ActionEvent actionEvent){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a directory");
        Stage stage = new Stage();
        File contactDirectory = chooser.showDialog(stage);
        if (contactDirectory != null) {
            readContactDirectory(contactDirectory);
        }
    }

    private void readContactDirectory(File contactDirectory) {
        try{
            new VCFParser(contacts).addContactsFromDirectory(contactDirectory.toPath());
            cancelButton.fire();
        } catch (IOException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    private void readContactFile(File contactFile) {
        try{
            new VCFParser(contacts).addContact(contactFile.toPath());
            cancelButton.fire();
        } catch (IOException | NameNotAllowedException e) {
            errorMessageText.setVisible(true);
            errorMessageText.setText(e.getMessage());
        }
    }

    private void textFieldChanged(Observable observable) {
        errorMessageText.setVisible(false);
    }

    @FXML
    private void btnAddContactClicked(ActionEvent event) {
        try {
            contacts.addContact(contactName.getText());
            closeStage(event);
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
