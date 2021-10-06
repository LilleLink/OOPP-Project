package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ContactList;
import model.Event;

public class EditEventCard extends ViewComponent {

    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker eventDatePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private ComboBox<String> tagComboBox;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField contactSearchField;

    private Event event;
    private ContactList contactList;

    EditEventCard(Event event, ContactList contactList) {
        this.event = event;
        this.contactList = contactList;
    }

    private void setFields() {

    }

}
