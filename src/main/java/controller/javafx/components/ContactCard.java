package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Contact;
import model.IObserver;
import model.ITag;

class ContactCard extends ViewComponent implements IObserver {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private ImageView contactImage;
    @FXML private Label nameLabel;
    @FXML private HBox tagHBox;
    private Contact contact;

    ContactCard(Contact contact){
        super();
        nameLabel.setText(contact.getName());
        this.contact = contact;
        onEvent();
    }

    public Contact getContact(){
        return contact;
    }

    public void onEvent(){
        for (ITag tag : contact.getTags()){
            tagHBox.getChildren().add(new TagCard(tag).getPane());
        }
    }
}