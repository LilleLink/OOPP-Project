package controller.javafx.components;

import javafx.application.Platform;
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

class EventCard extends ViewComponent {

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

    private final TagHandler tagHandler;
    private final ContactList contactList;
    private List<Contact> participants;
    private final Event event;
    private EventHandler<ActionEvent> deleteHandler;
    private EventHandler<javafx.event.Event> closeHandler;

    EventCard(TagHandler tagHandler, ContactList contactList, Event event) {
        this.tagHandler = tagHandler;
        this.contactList = contactList;
        this.event = event;
        this.participants = event.getContacts();

        initializeComponent();
    }

    private void saveEvent(ActionEvent actionEvent) {
        event.setName(nameTextField.getText());
        event.setAddress(addressTextField.getText());
        event.setDateTime(getLocalDateTime());
        event.setDescription(descriptionTextArea.getText());
        event.setTag(tagComboBox.getValue());
        event.setContacts(participants);
        closeHandler.handle(actionEvent);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void initializeComponent() {
        saveButton.setOnAction(this::saveEvent);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);

        lightboxAnchorPane.setOnMouseClicked(this::close);
        cardAnchorPane.setOnMouseClicked(this::consumeClick);
        addTagButton.setOnAction(this::addTag);
        selectContactsButton.setOnAction(this::selectContacts);

        Platform.runLater(() -> nameTextField.requestFocus());

        setFields();
    }

    private void setFields() {
        nameTextField.setText(event.getName());
        addressTextField.setText(event.getAddress());
        eventDatePicker.setValue(event.getDateTime().toLocalDate());
        descriptionTextArea.setText(event.getDescription());
        initializeSpinners();
        initializeComboBox();
        updateParticipantFlowPane();
    }

    private void initializeSpinners() {
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,
                event.getDateTime().getHour(), 1);
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,
                event.getDateTime().getMinute(), 5);
        hourValueFactory.setWrapAround(true);
        minuteValueFactory.setWrapAround(true);
        hourSpinner.setValueFactory(hourValueFactory);
        minuteSpinner.setValueFactory(minuteValueFactory);
    }

    private void initializeComboBox() {
        resetTagComboBox();
    }

    private void resetTagComboBox() {
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(tagHandler.getAllTags());
        tagComboBox.setValue(event.getTag());
    }

    private void updateParticipantFlowPane() {
        participantsHBox.getChildren().clear();

        for (Contact c : participants) {
            participantsHBox.getChildren().add(new SmallContactCard(c).getPane());
        }
    }

    private void selectContacts(ActionEvent actionEvent) {
        ContactPickerDialog dialog = new ContactPickerDialog(contactList, event);
        participants = dialog.getPickedContacts();
        updateParticipantFlowPane();
    }

    private void addTag(ActionEvent actionEvent) {
        new CreateTagDialog(tagHandler);
        resetTagComboBox();
    }

    private void consumeClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    private void delete(ActionEvent actionEvent) {
        deleteHandler.handle(actionEvent);
        close(actionEvent);
    }

    public void setOnDelete(EventHandler<ActionEvent> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    public void setOnClose(EventHandler<javafx.event.Event> closeHandler) {
        this.closeHandler = closeHandler;
    }

    private void close(javafx.event.Event event) {
        closeHandler.handle(event);
    }


}
