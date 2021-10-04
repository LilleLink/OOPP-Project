package controller.javafx.components;

import controller.javafx.ViewComponentFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactPage extends ViewComponent {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private FlowPane cardFlowPane;
    List<Contact> contacts = new ArrayList<>();

    public ContactPage(List<Contact> contacts){
        super();
        this.contacts = contacts;
        drawAllCards();
    }

    public void drawAllCards(){
        for (Contact contact : contacts){
            cardFlowPane.getChildren().add(ViewComponentFactory.CreateContactCard(contact).getPane());
        }
    }

}
