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
import java.util.ArrayList;

class EventCreationCard extends ViewComponent {

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

    private EventList eventList;
    private ContactList contactList;

    public EventCreationCard(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        this.eventList = eventList;
        this.contactList = contactList;
        this.tagHandler = tagHandler;
        saveButton.setOnMouseClicked(this::createEvent);
        lightboxAnchorPane.setOnMouseClicked(this::close);
        cardAnchorPane.setOnMouseClicked(this::consumeClick);
        addTagButton.setOnAction(this::addTag);
        initializeSpinners();
        initializeComboBox();
    }

    private void consumeClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    private void initializeComboBox() {
        resetTagComboBox();
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
        eventList.addEvent(name, localDateTime, address, description, new ArrayList<>(), tagComboBox.getValue());
        close(null);
    }

    private LocalDateTime getLocalDateTime() {
        LocalDate localDate = eventDatePicker.getValue();
        return localDate.atTime(hourSpinner.getValue(), minuteSpinner.getValue());
    }

    private void close(MouseEvent mouseEvent) {
        this.getPane().toBack();
    }

    private void addTag(ActionEvent event){
        TextInputDialog td = new TextInputDialog();
        td.showAndWait();
        try {
            tagHandler.createTag(td.getEditor().getText());
        } catch (NameNotAvailableException e) {
            throw new RuntimeException(e);
        }
        resetTagComboBox();
    }

    public void clearFields() {
        nameTextField.clear();
        addressTextField.clear();
        eventDatePicker.setValue(null);
        initializeSpinners();
        descriptionTextArea.clear();
        resetTagComboBox();
        contactSearchField.clear();
    }

    private void resetTagComboBox(){
        tagComboBox.getItems().clear();
        tagComboBox.getItems().addAll(tagHandler.getAllTags());
    }

}
