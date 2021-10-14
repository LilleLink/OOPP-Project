package controller.javafx.components;

import attachmentHandler.AttachmentHandlerFactory;
import attachmentHandler.IAttachmentHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Contact;
import model.IObserver;
import model.ITag;
import model.TagHandler;
import model.exceptions.TagNotFoundException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.LambdaConversionException;
import java.net.URI;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.FileHandler;

class ContactGrayBox extends ViewComponent implements IObserver {

    private final TagHandler tagHandler;
    private Contact contact;

    @FXML private AnchorPane baseAnchorPane;

    @FXML private AnchorPane cardAnchorPane;

    @FXML private AnchorPane notesAnchorPane;

    @FXML private VBox attachmentVBox;

    @FXML private ImageView contactImage;

    @FXML private TextField contactName;

    @FXML private Button closeButton;

    @FXML private Button deleteButton;

    @FXML private Button doneButton;

    @FXML private Button addAttachmentButton;

    @FXML private TextField addressText;

    @FXML private Button openMapButton;

    @FXML private Button addTagButton;

    @FXML private HBox tagHBox;

    private NotesComponent notesComponent;

    private EventHandler<Event> closeWindowHandler;

    private EventHandler<Event> deleteContactHandler;

    private final IAttachmentHandler attachmentHandler = AttachmentHandlerFactory.getService();

    ContactGrayBox(TagHandler tagHandler){
        super();
        this.tagHandler = tagHandler;
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
        doneButton.setOnAction(this::close);
        contactName.textProperty().addListener((observableValue, s, t1) -> contact.setName(t1));
        addressText.textProperty().addListener(((observableValue, s, t1) -> contact.setAddress(t1)));
        openMapButton.setOnAction(this::openMap);
        cardAnchorPane.setOnMouseClicked(MouseEvent::consume);
        addAttachmentButton.setOnAction(this::addAttachment);
        addTagButton.setOnAction(actionEvent -> {
            new AddTagDialog(contact, tagHandler).displayAndWait();
            updateTagBox();
        });
    }

    void setContact(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
        addressText.setText(contact.getAddress());
        this.notesComponent = new NotesComponent(contact.getNotes());
        notesAnchorPane.getChildren().add(notesComponent.getPane());
        contactImage.setOnMouseClicked(this::setNewContactImage);
        updateContactImage();
        drawAttachments();
        updateTagBox();
    }

    Contact getContact(){
        return contact;
    }

    /**
     * Sets a handler for closing the page
     * @param handler the handler for closing
     */
    void setOnClose(EventHandler<Event> handler){
        closeWindowHandler = handler;
    }

    private void close(Event event){
        closeWindowHandler.handle(event);
    }

    private void updateTagBox(){
        tagHBox.getChildren().clear();
        ArrayList<TagCard> cards = new ArrayList<>();
        for (ITag tag: contact.getTags()){
            cards.add(new TagCard(tag));
        }
        cards.forEach(tagCard -> tagCard.setOnDelete(actionEvent -> removeTag(tagCard.getTag())));
    }

    private void removeTag(ITag tag){
        try {
            contact.removeTag(tag);
        } catch (TagNotFoundException ignored){
        }
        updateTagBox();
    }

    /**
     * sets a handler for deletion of a contact
     * @param handler the handler for deletion
     */
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

    private void addAttachment(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to add as attachment");
        File selectedFile =fileChooser.showOpenDialog(this.getPane().getScene().getWindow());
        if (selectedFile != null){
            try {
                attachmentHandler.addAttachment(contact.getDirectoryId(), selectedFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        contact.notifyObservers();
    }



    private void drawAttachments() {
        attachmentVBox.getChildren().clear();
        try {
            for (Path attachment : attachmentHandler.getAttachments(contact.getDirectoryId())){
                AttachmentCard attachmentCard = new AttachmentCard(attachment);
                attachmentCard.setDeleteHandler(mouseEvent ->
                {
                    try {
                        attachmentHandler.removeAttachment(contact.getDirectoryId(), attachment);
                        contact.notifyObservers();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                attachmentVBox.getChildren().add(attachmentCard.getPane());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        drawAttachments();
    }
}
