package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.ContactList;
import model.Contact;
import model.IObserver;

import java.util.ArrayList;
import java.util.List;


class ContactPage extends ViewComponent implements IObserver {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private FlowPane cardFlowPane;
    @FXML private TextField newContactNameTextField;
    @FXML private Button newContactButton;
    private ContactGrayBox contactGrayBox;
    private ContactList contacts;
    private List<ContactCard> contactCards = new ArrayList<>();

    public ContactPage(ContactList contacts){
        super();
        this.contacts = contacts;
        contacts.subscribe(this);
        this.newContactButton.setOnMouseClicked(this::newContact);
        contactGrayBox = new ContactGrayBox();
        contactGrayBox.setOnClose( mouseEvent -> closeGrayPane());
        AnchorPane contactGrayBoxPane = contactGrayBox.getPane();
        baseAnchorPane.getChildren().add(contactGrayBoxPane);
        contactGrayBoxPane.setVisible(false);
        onEvent();
    }

    public void onEvent(){
        for (ContactCard contactCard : contactCards){
            contactCard.getContact().unSubscribe(contactCard);
        }
        cardFlowPane.getChildren().clear();
        contactCards.clear();
        for (Contact contact : contacts.getList()){
            ContactCard card = new ContactCard(contact);
            contact.subscribe(card);
            AnchorPane pane = card.getPane();
            cardFlowPane.getChildren().add(pane);
            pane.setOnMouseClicked( (MouseEvent event) -> openGrayPane(contact) );
        }
    }

    private void openGrayPane(Contact contact){
        contactGrayBox.setContact(contact);
        contactGrayBox.setOnDelete(mouseEvent -> removeContact(contact));
        contactGrayBox.getPane().setVisible(true);
        contact.subscribe(contactGrayBox);
    }

    public void closeGrayPane(){
        contactGrayBox.getPane().setVisible(false);
        contactGrayBox.getContact().unSubscribe(contactGrayBox);
    }

    private void newContact(MouseEvent mouseEvent){
        String name = newContactNameTextField.getText();
        if (name.length() > 0) {
            contacts.addContact(name);
            newContactNameTextField.clear();
        }
    }

    private void removeContact(Contact contact){
        contacts.removeContact(contact);
    }

}
