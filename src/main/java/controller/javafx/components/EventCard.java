package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Contact;
import model.Event;
import model.EventList;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventCard extends ViewComponent implements Initializable {

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

    // Not used yet because I dont know how it works yet.
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

    public EventCard(EventList eventList) {
        this.eventList = eventList;
        saveButton.setOnMouseClicked(this::newEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSpinners();
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,
                LocalDateTime.now().getHour());
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,
                LocalDateTime.now().getMinute());
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

    private void newEvent(MouseEvent mouseEvent) {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        LocalDateTime localDateTime = getLocalDateTime();
        String description = descriptionTextArea.getText();
        //List<Contact> participants = ContactList.search(participantsTextField.getText())?
        //ITag tag = tagHandler.getTag(categoryTextField.getText())?
        eventList.addEvent(name, localDateTime, address, description, new ArrayList<>(), null);
    }

    private LocalDateTime getLocalDateTime() {
        return eventDatePicker.getValue().atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

}
