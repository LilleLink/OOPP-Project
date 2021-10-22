package controller.javafx.components;

import attachmenthandler.AttachmentHandlerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Contact;
import model.ITag;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

class ContactPickerContactCard extends ViewComponent {

    private Contact contact;

    @FXML
    private ImageView avatarImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label tagNameLabel;
    @FXML
    private CheckBox selectedCheckBox;

    ContactPickerContactCard(Contact contact) {
        this.contact = contact;
        initCard();
    }

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    public Contact getContact() {
        return contact;
    }

    private void initCard() {
        try {
            this.avatarImageView.setImage(new Image(AttachmentHandlerFactory.getService().getMainImage(
                    contact.getDirectoryId()).toUri().toString()));
        } catch (NoSuchFileException e) {
            this.avatarImageView.setImage(new Image("images/defaultContactIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLabels();
    }

    private void setLabels() {
        nameLabel.setText(contact.getName());
        StringBuilder sb = new StringBuilder();
        if (!contact.getTags().isEmpty()) {
            for (ITag tag : contact.getTags()) {
                sb.append(tag.getName()).append(", ");
            }
            tagNameLabel.setText(sb.toString());
        } else {
            tagNameLabel.setText("No category");
        }

    }

}
