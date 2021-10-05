package controller.javafx.components;

import controller.javafx.ViewComponentFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import model.ContactList;
import model.Event;
import model.EventList;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class CalendarPage extends ViewComponent implements Initializable {

    @FXML private Label weekLabel;
    @FXML private Button nextWeekButton;
    @FXML private Button previousWeekButton;
    @FXML private Button newEventButton;

    @FXML private Label mondayLabel;
    @FXML private Label tuesdayLabel;
    @FXML private Label wednesdayLabel;
    @FXML private Label thursdayLabel;
    @FXML private Label fridayLabel;
    @FXML private Label saturdayLabel;
    @FXML private Label sundayLabel;

    @FXML private FlowPane mondayFlowPane;
    @FXML private FlowPane tuesdayFlowPane;
    @FXML private FlowPane wednesdayFlowPane;
    @FXML private FlowPane thursdayFlowPane;
    @FXML private FlowPane fridayFlowPane;
    @FXML private FlowPane saturdayFlowPane;
    @FXML private FlowPane sundayFlowPane;

    @FXML private StackPane calendarPageStackPane;
    @FXML private AnchorPane calendarPageAnchorPane;

    private EventCard eventCard;

    private EventList eventList;
    private ContactList contactList;

    public CalendarPage(EventList eventList, ContactList contactList) {
        this.eventList = eventList;
        this.contactList = contactList;
        newEventButton.setOnMouseClicked(this::newEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventCard = new EventCard(eventList, contactList);
        calendarPageStackPane.getChildren().add(eventCard.getPane());
        calendarPageAnchorPane.toFront();
    }

    private void newEvent(MouseEvent mouseEvent) {
        eventCard.clearFields();
        eventCard.getPane().toFront();
    }

}
