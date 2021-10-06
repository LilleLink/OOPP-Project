package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ContactList;
import model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EditEventCard extends ViewComponent {

    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker eventDatePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private ComboBox<String> tagComboBox;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField contactSearchField;

    @FXML private Button saveButton;
    @FXML private Button closeButton;

    private Event event;
    private ContactList contactList;

    public EditEventCard(Event event, ContactList contactList) {
        this.event = event;
        this.contactList = contactList;

        saveButton.setOnAction(this::saveEvent);
        closeButton.setOnAction(this::close);
        setFields();
    }

    private void saveEvent(ActionEvent actionEvent) {
        event.setName(nameTextField.getText());
        event.setAddress(addressTextField.getText());
        event.setDateTime(getLocalDateTime());
        event.setDescription(descriptionTextArea.getText());
        //Tags and contacts
        close(null);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(ActionEvent actionEvent) {
        this.getPane().toBack();
    }

    private void setFields() {
        nameTextField.setText(event.getName());
        addressTextField.setText(event.getAddress());
        eventDatePicker.setValue(event.getDateTime().toLocalDate());
        initializeSpinners();
        descriptionTextArea.setText(event.getDescription());
        initializeComboBox();
        //contactSearchField
    }

    private void initializeComboBox() {
        //Set tag as options
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,
                event.getDateTime().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,
                event.getDateTime().getMinute(), 5);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

}
