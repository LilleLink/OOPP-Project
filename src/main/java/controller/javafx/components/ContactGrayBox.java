package controller.javafx.components;

import application.HostServicesProvider;
import attachmentHandler.AttachmentHandlerFactory;
import attachmentHandler.IAttachmentHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.*;
import model.exceptions.TagNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;

class ContactGrayBox extends ViewComponent implements IObserver {

    private Contact contact;

    @FXML
    private AnchorPane baseAnchorPane;

    @FXML
    private AnchorPane cardAnchorPane;

    @FXML
    private AnchorPane notesAnchorPane;

    @FXML
    private VBox attachmentVBox;

    @FXML
    private ImageView contactImage;

    @FXML
    private TextField contactName;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button doneButton;

    @FXML
    private Button addAttachmentButton;

    @FXML
    private TextField addressText;

    @FXML
    private Button openMapButton;

    @FXML
    private Button addTagButton;

    @FXML
    private HBox tagHBox;

    @FXML
    private AnchorPane eventsAnchorPane;

    private NotesComponent notesComponent;

    private EventOverview eventOverview;

    private EventHandler<Event> closeWindowHandler;

    private EventHandler<Event> deleteContactHandler;

    private final IAttachmentHandler attachmentHandler = AttachmentHandlerFactory.getService();

    private final EventList eventList;

    ContactGrayBox(TagHandler tagHandler, EventList eventList) {
        super();
        this.eventList = eventList;
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
        doneButton.setOnAction(this::close);
        contactName.textProperty().addListener((observableValue, s, t1) -> contact.setName(t1));
        addressText.textProperty().addListener((observableValue, s, t1) -> contact.setAddress(t1));
        openMapButton.setOnAction(this::openMap);
        cardAnchorPane.setOnMouseClicked(MouseEvent::consume);
        addAttachmentButton.setOnAction(this::addAttachment);
        addTagButton.setOnAction(actionEvent -> {
            new AddTagDialog(contact, tagHandler);
            updateTagBox();
        });
    }

    void setContact(Contact contact) {
        this.contact = contact;
        this.notesComponent = new NotesComponent(contact);
        contactName.setText(contact.getName());
        addressText.setText(contact.getAddress());
        this.eventOverview = new EventOverview(eventList.getContactsEvents(contact));
        eventsAnchorPane.getChildren().add(eventOverview.getPane());
        notesAnchorPane.getChildren().add(notesComponent.getPane());
        contactImage.setOnMouseClicked(this::setNewContactImage);
        onEvent();
    }

    Contact getContact() {
        return contact;
    }

    /**
     * Sets a handler for closing the page
     *
     * @param handler the handler for closing
     */
    void setOnClose(EventHandler<Event> handler) {
        closeWindowHandler = handler;
    }

    private void close(Event event) {
        closeWindowHandler.handle(event);
    }

    private void updateTagBox() {
        tagHBox.getChildren().clear();
        ArrayList<TagCard> cards = new ArrayList<>();
        for (ITag tag : contact.getTags()) {
            cards.add(new TagCard(tag));
        }
        cards.forEach(tagCard -> tagCard.setOnDelete(actionEvent -> removeTag(tagCard.getTag())));
        cards.forEach(tagCard -> tagHBox.getChildren().add(tagCard.getPane()));
    }

    private void removeTag(ITag tag) {
        try {
            contact.removeTag(tag);
        } catch (TagNotFoundException ignored) {
        }
        updateTagBox();
    }

    /**
     * sets a handler for deletion of a contact
     *
     * @param handler the handler for deletion
     */
    void setOnDelete(EventHandler<Event> handler) {
        deleteContactHandler = handler;
    }

    private void delete(Event event) {
        deleteContactHandler.handle(event);
        close(event);
    }

    private void openMap(ActionEvent event) {
        HostServicesProvider.getHostServices().showDocument("https://maps.google.com/maps?q=" +
                addressText.getText().replace(' ', '+'));
    }

    private void setNewContactImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new picture for contact");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.gif", "*.jpeg", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(this.getPane().getScene().getWindow());
        if (selectedFile != null) {
            try {
                attachmentHandler.saveMainImage(contact.getDirectoryId(), selectedFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        contact.notifyObservers();
    }

    private void addAttachment(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to add as attachment");
        File selectedFile = fileChooser.showOpenDialog(this.getPane().getScene().getWindow());
        if (selectedFile != null) {
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
            for (Path attachment : attachmentHandler.getAttachments(contact.getDirectoryId())) {
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

    private void updateContactImage() {
        try {
            contactImage.setImage(new Image(attachmentHandler.getMainImage(contact.getDirectoryId()).toUri().toString()));
        } catch (NoSuchFileException e) {
            contactImage.setImage(new Image("images/defaultContactIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent() {
        updateContactImage();
        drawAttachments();
        updateTagBox();
    }
}
