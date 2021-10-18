package controller.javafx.components;

import attachmentHandler.AttachmentHandlerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Contact;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

class SmallContactCard extends ViewComponent {

    @FXML
    private Label nameLabel;
    @FXML
    private ImageView avatarImageView;

    private Contact contact;

    SmallContactCard(Contact contact) {
        this.contact = contact;
        setLabels();
    }

    private void setLabels() {
        nameLabel.setText(contact.getName());
        try {
            avatarImageView.setImage(new Image(AttachmentHandlerFactory.getService().getMainImage(contact.getDirectoryId()).toUri().toString()));
        } catch (NoSuchFileException e) {
            avatarImageView.setImage(new Image("images/defaultContactIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
