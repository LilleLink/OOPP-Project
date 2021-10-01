package controller.javafx.components;

import controller.javafx.ViewComponentFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Contact;
import model.ITag;

public class ContactCard extends ViewComponent{
    @FXML private AnchorPane baseAnchorPane;
    @FXML private ImageView contactImage;
    @FXML private Label nameLabel;
    @FXML private HBox tagHBox;
    private Contact contact;

    public ContactCard(Contact contact){
        super();
        nameLabel.setText(contact.getName());
        this.contact = contact;
        System.out.println(contact.getTags().size());
        drawAllTags();
    }

    public void drawAllTags(){
        for (ITag tag : contact.getTags()){
            tagHBox.getChildren().add(ViewComponentFactory.CreateTagCard(tag).getPane());
        }
    }
}