package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.Contact;
import model.Event;

import java.net.URL;
import java.util.ResourceBundle;

public class EventCard extends ViewComponent implements Initializable {

    private Event event;

    @FXML private Label eventNameLabel;
    @FXML private Label eventDescriptionLabel;
    @FXML private Label eventTagLabel;
    @FXML private Label eventDateLabel;
    @FXML private Label eventAddressLabel;
    @FXML private AnchorPane contactsAnchorPane;
    @FXML private FlowPane contactsFlowPane;

    public EventCard(Event event) {
        this.event = event;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPane().setStyle("-fx-border-color: "+event.getTag().getColor()+";");

        eventNameLabel.setText(event.getName());
        eventDescriptionLabel.setText(event.getDescription());
        eventTagLabel.setText(event.getTag().getName());
        eventDateLabel.setText(event.getDateTime().toString());
        eventAddressLabel.setText(event.getAddress());

        for (Contact contact : event.getContacts()) {
            contactsFlowPane.getChildren().add(new Label(contact.getName()));
        }
    }

}
