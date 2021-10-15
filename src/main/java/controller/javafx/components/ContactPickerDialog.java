package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.ContactList;
import model.Event;

import java.util.ArrayList;
import java.util.List;

class ContactPickerDialog extends ViewComponent {

    @FXML
    private AnchorPane searchBarAnchorPane;
    @FXML
    private VBox contactCardVBox;
    @FXML
    private Button saveButton;

    private ContactList contactList;
    private List<Contact> allContacts;
    private List<ContactPickerContactCard> contactPickerContactCardList = new ArrayList<>();
    private List<Contact> pickedContacts = new ArrayList<>();

    ContactPickerDialog(ContactList contactList, Event event) {
        this.contactList = contactList;
        allContacts = xorAddLists(contactList.getList(), event.getContacts());
        initializeComponent();
    }

    ContactPickerDialog(ContactList contactList) {
        this.contactList = contactList;
        allContacts = contactList.getList();
        initializeComponent();
    }

    private void initializeComponent() {
        initContactPickerContactCardList();
        saveButton.setOnAction(this::saveAndClose);
        displayAndWait();
    }

    /***
     * XOR-adds two lists.
     * @param a list 1
     * @param b list 2
     * @param <T> some type T
     * @return a superset of the two lists
     */
    private <T> List<T> xorAddLists(List<T> a, List<T> b) {
        List<T> result = new ArrayList<>(a);
        for (T t : b) {
            if (!a.contains(t))
                result.add(t);
        }
        return result;
    }

    public List<Contact> getPickedContacts() {
        return pickedContacts;
    }

    private void saveAndClose(ActionEvent actionEvent) {
        for (ContactPickerContactCard cpcc : contactPickerContactCardList) {
            if (cpcc.isSelected()) {
                pickedContacts.add(cpcc.getContact());
            }
        }
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void displayAndWait() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(this.getPane());
        stage.setTitle("Pick contacts");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void initContactPickerContactCardList() {
        for (Contact contact : allContacts) {
            ContactPickerContactCard contactPickerContactCard = new ContactPickerContactCard(contact);
            contactPickerContactCardList.add(contactPickerContactCard);
            contactCardVBox.getChildren().add(contactPickerContactCard.getPane());
        }
    }

}