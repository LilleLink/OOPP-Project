package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
import model.exceptions.NameNotAvailableException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EditEventCard extends ViewComponent {

    private final TagHandler tagHandler;
    @FXML private AnchorPane lightboxAnchorPane;
    @FXML private AnchorPane cardAnchorPane;

    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private DatePicker eventDatePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private ComboBox<ITag> tagComboBox;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField contactSearchField;
    @FXML private Button addTagButton;

    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private Button removeButton;

    private Event event;
    private ContactList contactList;
    private EventList eventList;

    public EditEventCard(Event event, ContactList contactList, EventList eventList, TagHandler tagHandler) {
        this.event = event;
        this.contactList = contactList;
        this.tagHandler = tagHandler;
        this.eventList = eventList;

        saveButton.setOnAction(this::saveEvent);
        closeButton.setOnMouseClicked(this::close);
        removeButton.setOnMouseClicked(this::removeEvent);

        lightboxAnchorPane.setOnMouseClicked(this::close);
        cardAnchorPane.setOnMouseClicked(this::consumeClick);
        addTagButton.setOnAction(this::addTag);

        setFields();
    }

    private void removeEvent(MouseEvent mouseEvent) {
        eventList.removeEvent(this.event);
        close(null);
    }

    private void consumeClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    private void saveEvent(ActionEvent actionEvent) {
        event.setName(nameTextField.getText());
        event.setAddress(addressTextField.getText());
        event.setDateTime(getLocalDateTime());
        event.setDescription(descriptionTextArea.getText());
        event.setTag(tagComboBox.getValue());
        //Contacts
        close(null);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(MouseEvent mouseEvent) {
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
        resetTagComboBox();
    }

    private void addTag(ActionEvent event){
        new AddTagDialog(tagHandler).display();
        resetTagComboBox();
    }

    private void resetTagComboBox() {
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(tagHandler.getAllTags());
        tagComboBox.setValue(event.getTag());
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,
                event.getDateTime().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,
                event.getDateTime().getMinute()-event.getDateTime().getMinute()%15, 5);
        hourValueFactory.setWrapAround(true);
        minuteValueFactory.setWrapAround(true);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

}
