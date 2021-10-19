package controller.javafx.components;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ContactList;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;
import vcf.IVCFParser;
import vcf.VCFParserFactory;

import java.io.File;
import java.io.IOException;

class CreateContactDialog extends ViewComponent {

    private final ContactList contacts;
    private final Stage stage = new Stage();
    private final TagHandler tagHandler;
    private final IVCFParser fileParser;
    @FXML
    private TextField contactName;
    @FXML
    private Button addContactButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Text errorMessageText;
    @FXML
    private Button fileLoad;
    @FXML
    private Button dirLoad;


    CreateContactDialog(ContactList contacts, TagHandler tagHandler) {
        super();
        this.contacts = contacts;
        this.tagHandler = tagHandler;
        fileParser = VCFParserFactory.getService(contacts, tagHandler);
        errorMessageText.setVisible(false);
        errorMessageText.setFill(Color.RED);
        addContactButton.setOnAction(this::btnAddContactClicked);
        cancelButton.setOnAction(ActionEvent -> close());
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

    private void loadContactDirectory(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a directory");
        Stage stage = new Stage();
        File contactDirectory = chooser.showDialog(stage);
        if (contactDirectory != null) {
            readContactDirectory(contactDirectory);
        }
    }

    private void readContactDirectory(File contactDirectory) {
        try {
            fileParser.addContactsFromDirectory(contactDirectory.toPath());
            cancelButton.fire();
        } catch (IOException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    private void readContactFile(File contactFile) {
        try {
            fileParser.addContact(contactFile.toPath());
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
        addContact();
    }

    private void addContact() {
        try {
            contacts.addContact(contactName.getText());
            close();
        } catch (NameNotAllowedException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
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
                addContact();
                break;
        }
    }

    public void displayAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 300, 200);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();
    }


}
