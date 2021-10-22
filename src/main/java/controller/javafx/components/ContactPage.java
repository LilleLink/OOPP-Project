package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.*;
import model.search.ISearchObserver;

import java.util.ArrayList;
import java.util.List;


class ContactPage extends ViewComponent implements IObserver, ISearchObserver {
    private final TagHandler tagHandler;
    @FXML
    private AnchorPane baseAnchorPane;
    @FXML
    private FlowPane cardFlowPane;
    @FXML
    private Button newContactButton;
    @FXML
    private AnchorPane searchBarAnchorPane;
    private final ContactGrayBox contactGrayBox;
    private final SearchBar<Contact> searchBar;
    private final ContactList contacts;
    private final List<ContactCard> contactCards = new ArrayList<>();

    ContactPage(ContactList contacts, TagHandler tagHandler, EventList eventList) {
        super();
        this.contacts = contacts;
        this.tagHandler = tagHandler;
        contacts.subscribe(this);
        this.newContactButton.setOnMouseClicked(this::newContact);
        contactGrayBox = new ContactGrayBox(tagHandler, eventList);
        contactGrayBox.setOnClose(mouseEvent -> closeGrayPane());
        AnchorPane contactGrayBoxPane = contactGrayBox.getPane();
        baseAnchorPane.getChildren().add(contactGrayBoxPane);
        contactGrayBoxPane.setVisible(false);
        int searchTolerance = 4;
        searchBar = new SearchBar<>(contacts.getList(), searchTolerance);
        searchBar.subscribe(this);
        searchBarAnchorPane.getChildren().add(searchBar.getPane());
        searchBar.getPane().layout();
        this.getPane().addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
        onEvent();
    }

    public void onEvent() {
        updateContactCards(contacts.getList());
    }

    private void openGrayPane(Contact contact) {
        contactGrayBox.setContact(contact);
        contactGrayBox.setOnDelete(mouseEvent -> removeContact(contact));
        contactGrayBox.getPane().setVisible(true);
        contact.subscribe(contactGrayBox);
    }

    public void closeGrayPane() {
        contactGrayBox.getPane().setVisible(false);
        contactGrayBox.getContact().unSubscribe(contactGrayBox);
        onEvent();
    }

    private void keyPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ESCAPE) {
            closeGrayPane();
        }
    }

    private void newContact(MouseEvent mouseEvent) {
        new CreateContactDialog(contacts, tagHandler).displayAndWait();
        onEvent();
        searchBar.updateSearchBase(contacts.getList());
    }

    private void removeContact(Contact contact) {
        contacts.removeContact(contact);
        searchBar.updateSearchBase(contacts.getList());
    }

    private void clearCards() {
        for (ContactCard contactCard : contactCards) {
            contactCard.getContact().unSubscribe(contactCard);
        }
        cardFlowPane.getChildren().clear();
        contactCards.clear();
    }

    /**
     * Constructs cards from the given contacts and adds them to the list of contact cards.
     *
     * @param contacts the contacts to be represented as cards
     */
    private void createCards(List<Contact> contacts) {
        contacts.forEach(contact -> {
            ContactCard card = new ContactCard(contact);
            contact.subscribe(card);
            contactCards.add(card);
            AnchorPane pane = card.getPane();
            cardFlowPane.getChildren().add(pane);
            pane.setOnMouseClicked((MouseEvent event) -> openGrayPane(contact));
        });
    }

    /**
     * Constructs new contact cards with the given list and replaces the ones currently displayed.
     * Current viewed contacts are unsubscribed, while the given contacts are subscribed.
     *
     * @param contacts the list to replace the current cards
     */
    private void updateContactCards(List<Contact> contacts) {
        clearCards();
        createCards(contacts);
    }

    @Override
    public void onSearch() {
        updateContactCards(searchBar.getResults());
    }
}
