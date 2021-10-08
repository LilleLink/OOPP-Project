package controller.javafx.components;

import attachmentHandler.AttachmentHandlerFactory;
import attachmentHandler.IAttachmentHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Contact;
import model.IObserver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.LambdaConversionException;
import java.net.URI;
import java.nio.file.NoSuchFileException;

class ContactGrayBox extends ViewComponent implements IObserver {

    private Contact contact;

    @FXML private AnchorPane baseAnchorPane;

    @FXML private AnchorPane cardAnchorPane;

    @FXML private AnchorPane notesAnchorPane;

    @FXML private ImageView contactImage;

    @FXML private TextField contactName;

    @FXML private Button closeButton;

    @FXML private Button deleteButton;

    @FXML private Button saveButton;

    @FXML private Text contactChangedText;

    @FXML private TextField addressText;

    @FXML private Button openMapButton;

    private NotesComponent notesComponent;

    private EventHandler<Event> closeWindowHandler;

    private EventHandler<Event> deleteContactHandler;

    private final IAttachmentHandler attachmentHandler = AttachmentHandlerFactory.getService();

    ContactGrayBox(){
        super();
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
        contactName.textProperty().addListener(((observableValue, s, t1) -> fieldsChanged()));
        saveButton.setOnAction(this::save);
        openMapButton.setOnAction(this::openMap);
        addressText.textProperty().addListener((observable -> fieldsChanged()));
        cardAnchorPane.setOnMouseClicked(MouseEvent::consume);
    }

    private void fieldsChanged(){
        contactChangedText.setVisible(true);
    }

    void setContact(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
        contactChangedText.setVisible(false);
        addressText.setText(contact.getAddress());
        this.notesComponent = new NotesComponent(contact.getNotes());
        notesAnchorPane.getChildren().add(notesComponent.getPane());
        contactImage.setOnMouseClicked(this::setNewContactImage);
        updateContactImage();
    }

    Contact getContact(){
        return contact;
    }

    void setOnClose(EventHandler<Event> handler){
        closeWindowHandler = handler;
    }

    void close(Event event){
        closeWindowHandler.handle(event);
    }

    void setOnDelete(EventHandler<Event> handler){
        deleteContactHandler = handler;
    }

    private void delete(Event event){
        deleteContactHandler.handle(event);
        close(event);
    }

    private boolean openMap(ActionEvent event){
        //TODO use return value of openMap to display fail/success
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://maps.google.com/maps?q=" +
                        addressText.getText().replace(' ', '+')));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void save(Event e){
        if (isValidName(contactName.getText())) {
            contact.setName(contactName.getText());
            contact.setAddress(addressText.getText());
            close(e);
        }
    }

    private void setNewContactImage(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new picture for contact");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.gif", "*.jpeg", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(this.getPane().getScene().getWindow());
        if (selectedFile != null){
            try {
                attachmentHandler.saveMainImage(contact.getDirectoryId(), selectedFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        contact.notifyObservers();
    }

    private boolean isValidName(String name){
        return name.length() >= 1;
    }

    private void updateContactImage(){
        try {
            contactImage.setImage(new Image(attachmentHandler.getMainImage(contact.getDirectoryId()).toUri().toString()));
        } catch (NoSuchFileException e) {
            contactImage.setImage(new Image("Images/defaultIcon.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent() {
        updateContactImage();
    }
}
