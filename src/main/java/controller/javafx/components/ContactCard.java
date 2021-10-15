package controller.javafx.components;

import attachmentHandler.AttachmentHandlerFactory;
import attachmentHandler.IAttachmentHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Contact;
import model.IObserver;
import model.ITag;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

class ContactCard extends ViewComponent implements IObserver {
    @FXML
    private AnchorPane baseAnchorPane;
    @FXML
    private ImageView contactImage;
    @FXML
    private Label nameLabel;
    @FXML
    private HBox tagHBox;
    private final Contact contact;
    private final IAttachmentHandler attachmentHandler = AttachmentHandlerFactory.getService();

    ContactCard(Contact contact) {
        super();
        this.contact = contact;
        onEvent();
    }

    public Contact getContact() {
        return contact;
    }

    public void onEvent() {
        nameLabel.setText(contact.getName());
        updateImage();
        for (ITag tag : contact.getTags()) {
            tagHBox.getChildren().add(new TagCard(tag).getPane());
        }
    }


    private void updateImage() {
        try {
            contactImage.setImage(new Image(attachmentHandler.getMainImage(contact.getDirectoryId()).toUri().toString()));
        } catch (NoSuchFileException e) {
            contactImage.setImage(new Image("Images/defaultIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}