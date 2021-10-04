package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Contact;

public class ContactGrayBox extends ViewComponent{

    private final Contact contact;

    @FXML private ImageView profileImage;

    @FXML private TextField contactName;

    @FXML private AnchorPane baseAnchorPane;

    ContactGrayBox(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
    }

    @FXML
    void exit(MouseEvent event) {
        baseAnchorPane.setVisible(false);
    }
}
