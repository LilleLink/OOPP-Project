package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class EventCreationCard extends ViewComponent {

    private final TagHandler tagHandler;
    @FXML
    private AnchorPane lightboxAnchorPane;
    @FXML
    private AnchorPane cardAnchorPane;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    @FXML
    private ComboBox<ITag> tagComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button addTagButton;
    @FXML
    private Button selectContactsButton;

    @FXML
    private Button saveButton;

    private EventList eventList;
    private ContactList contactList;
    private List<Contact> participants = new ArrayList<>();

    EventCreationCard(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        this.eventList = eventList;
        this.contactList = contactList;
        this.tagHandler = tagHandler;
        saveButton.setOnMouseClicked(this::createEvent);
        lightboxAnchorPane.setOnMouseClicked(this::close);
        cardAnchorPane.setOnMouseClicked(this::consumeClick);
        addTagButton.setOnAction(this::addTag);
        selectContactsButton.setOnAction(this::selectContacts);

        initializeSpinners();
        initializeComboBox();
    }

    private void selectContacts(ActionEvent actionEvent) {
        ContactPickerDialog dialog = new ContactPickerDialog(contactList);
        participants = dialog.getPickedContacts();
    }

    private void consumeClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    private void initializeComboBox() {
        resetTagComboBox();
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,
                LocalDateTime.now().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,
                LocalDateTime.now().getMinute() - LocalDateTime.now().getMinute() % 15, 5);
        hourValueFactory.setWrapAround(true);
        minuteValueFactory.setWrapAround(true);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

    private void createEvent(MouseEvent mouseEvent) {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        LocalDateTime localDateTime = getLocalDateTime();
        String description = descriptionTextArea.getText();
        eventList.addEvent(name, localDateTime, address, description, participants, tagComboBox.getValue());
        close(null);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(MouseEvent mouseEvent) {
        this.getPane().toBack();
    }

    private void addTag(ActionEvent event) {
        new CreateTagDialog(tagHandler).displayAndWait();
        resetTagComboBox();
    }

    public void clearFields() {
        nameTextField.clear();
        addressTextField.clear();
        eventDatePicker.setValue(LocalDate.now());
        initializeSpinners();
        descriptionTextArea.clear();
        resetTagComboBox();
    }

    private void resetTagComboBox() {
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(tagHandler.getAllTags());
    }

}
