package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import model.ContactList;
import model.EventList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;
import java.util.*;

class CalendarPage extends ViewComponent {

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

    private LocalDate weekToDisplay;

    private EventCard eventCard;

    private EventList eventList;
    private ContactList contactList;

    public CalendarPage(EventList eventList, ContactList contactList) {
        this.eventList = eventList;
        this.contactList = contactList;

        eventCard = ViewComponentFactory.CreateEventCard(eventList, contactList);
        calendarPageStackPane.getChildren().add(eventCard.getPane());
        calendarPageAnchorPane.toFront();

        newEventButton.setOnMouseClicked(this::newEvent);
        nextWeekButton.setOnMouseClicked(this::incrementWeek);
        previousWeekButton.setOnMouseClicked(this::decrementWeek);

        weekToDisplay = LocalDate.now();
        setLabels(weekToDisplay);
    }

    private void incrementWeek(MouseEvent mouseEvent) {
        weekToDisplay = weekToDisplay.plusWeeks(1);
        setLabels(weekToDisplay);
    }

    private void decrementWeek(MouseEvent mouseEvent) {
        weekToDisplay = weekToDisplay.minusWeeks(1);
        setLabels(weekToDisplay);
    }

    private void setLabels(LocalDate date) {
        // Sets date to monday of this week.
        date = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        TemporalField weekGetter = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        weekLabel.setText("Week "+date.get(weekGetter)+" "+date.getYear());

        mondayLabel.setText("Monday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        tuesdayLabel.setText("Tuesday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        wednesdayLabel.setText("Wednesday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        thursdayLabel.setText("Thursday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        fridayLabel.setText("Friday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        saturdayLabel.setText("Saturday "+date.getDayOfMonth()+"/"+date.getMonthValue());
        date = date.plusDays(1);
        sundayLabel.setText("Sunday "+date.getDayOfMonth()+"/"+date.getMonthValue());
    }

    private void newEvent(MouseEvent mouseEvent) {
        eventCard.clearFields();
        eventCard.getPane().toFront();
    }

}
