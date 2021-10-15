package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class EditEventCard extends ViewComponent {

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
    private HBox participantsHBox;

    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;

    private final Event event;
    private final ContactList contactList;
    private List<Contact> participants;
    private EventHandler<ActionEvent> deleteHandler;

    EditEventCard(Event event, ContactList contactList, TagHandler tagHandler) {
        this.event = event;
        this.contactList = contactList;
        this.tagHandler = tagHandler;
        this.participants = event.getContacts();

        saveButton.setOnAction(this::saveEvent);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);

        lightboxAnchorPane.setOnMouseClicked(this::close);
        cardAnchorPane.setOnMouseClicked(this::consumeClick);
        addTagButton.setOnAction(this::addTag);
        selectContactsButton.setOnAction(this::selectContacts);

        setFields();

    }

    private void selectContacts(ActionEvent actionEvent) {
        ContactPickerDialog dialog = new ContactPickerDialog(contactList, event);
        participants = dialog.getPickedContacts();
        updateParticipantFlowPane();
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
        event.setContacts(participants);
        close();
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(ActionEvent e) {
        close();
    }

    private void close(MouseEvent e) {
        close();
    }

    private void delete(ActionEvent e) {
        deleteHandler.handle(e);
        close();
    }

    public void setOnDelete(EventHandler<ActionEvent> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    private void close() {
        this.getPane().toBack();
    }

    private void setFields() {
        nameTextField.setText(event.getName());
        addressTextField.setText(event.getAddress());
        eventDatePicker.setValue(event.getDateTime().toLocalDate());
        initializeSpinners();
        descriptionTextArea.setText(event.getDescription());
        initializeComboBox();
        updateParticipantFlowPane();
    }

    private void updateParticipantFlowPane() {
        participantsHBox.getChildren().clear();

        for (Contact c : participants) {
            participantsHBox.getChildren().add(new SmallContactCard(c).getPane());
        }
    }

    private void initializeComboBox() {
        resetTagComboBox();
    }

    private void addTag(ActionEvent event) {
        new CreateTagDialog(tagHandler);
        resetTagComboBox();
    }

    private void resetTagComboBox() {
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(tagHandler.getAllTags());
        tagComboBox.setValue(event.getTag());
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,
                event.getDateTime().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60,
                event.getDateTime().getMinute(), 5);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

}
