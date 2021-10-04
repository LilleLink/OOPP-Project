package controller.javafx.components;

import controller.javafx.ViewComponentFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactPage extends ViewComponent {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private FlowPane cardFlowPane;
    @FXML private TextField newContactNameTextField;
    @FXML private Button newContactButton;
    List<Contact> contacts = new ArrayList<>();

    public ContactPage(List<Contact> contacts){
        super();
        this.contacts = contacts;
        this.newContactButton.setOnMouseClicked(this::newContact);
        update();
    }

    private void update(){
        cardFlowPane.getChildren().clear();
        for (Contact contact : contacts){
            cardFlowPane.getChildren().add(ViewComponentFactory.CreateContactCard(contact).getPane());
        }
    }

    private void newContact(MouseEvent mouseEvent){
        //todo implement this
        System.out.println(newContactNameTextField.getCharacters());
        newContactNameTextField.clear();
        update();
    }

}
