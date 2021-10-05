package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Contact;
import model.ContactList;
import model.Event;
import model.EventList;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EventCard extends ViewComponent {

    @FXML private AnchorPane lightboxAnchorPane;
    @FXML private AnchorPane cardAnchorPane;

    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker eventDatePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField categoryTextField;
    @FXML private TextField participantsTextField;

    @FXML private Button saveButton;

    private EventList eventList;
    private ContactList contactList;

    // Not used yet because I dont know how it works yet.
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

    public EventCard(EventList eventList, ContactList contactList) {
        this.eventList = eventList;
        this.contactList = contactList;
        saveButton.setOnMouseClicked(this::createEvent);
        lightboxAnchorPane.setOnMouseClicked(this::close);
        initializeSpinners();
    }


    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,
                LocalDateTime.now().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,
                0, 5);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

    private void createEvent(MouseEvent mouseEvent) {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        LocalDateTime localDateTime = getLocalDateTime();
        String description = descriptionTextArea.getText();
        //Cannot search yet, just use comma separated values?
        //List<Contact> participants = ContactList.search(participantsTextField.getText())?
        //ITag tag = tagHandler.getTag(categoryTextField.getText())?
        eventList.addEvent(name, localDateTime, address, description, new ArrayList<>(), null);
        //System.out.println("Added new event! #Events: "+eventList.getList().size());
        close(null);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(MouseEvent mouseEvent) {
        this.getPane().toBack();
    }

    public void clearFields() {
        nameTextField.clear();
        addressTextField.clear();
        eventDatePicker.setValue(null);
        initializeSpinners();
        descriptionTextArea.clear();
        categoryTextField.clear();
        participantsTextField.clear();
    }

}
