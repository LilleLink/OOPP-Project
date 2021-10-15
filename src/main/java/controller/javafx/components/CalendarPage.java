package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import model.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CalendarPage extends ViewComponent implements IObserver {

    private final TagHandler tagHandler;
    @FXML
    private Label weekLabel;
    @FXML
    private Button nextWeekButton;
    @FXML
    private Button previousWeekButton;
    @FXML
    private Button newEventButton;

    @FXML
    private Label mondayLabel;
    @FXML
    private Label tuesdayLabel;
    @FXML
    private Label wednesdayLabel;
    @FXML
    private Label thursdayLabel;
    @FXML
    private Label fridayLabel;
    @FXML
    private Label saturdayLabel;
    @FXML
    private Label sundayLabel;

    @FXML
    private FlowPane mondayFlowPane;
    @FXML
    private FlowPane tuesdayFlowPane;
    @FXML
    private FlowPane wednesdayFlowPane;
    @FXML
    private FlowPane thursdayFlowPane;
    @FXML
    private FlowPane fridayFlowPane;
    @FXML
    private FlowPane saturdayFlowPane;
    @FXML
    private FlowPane sundayFlowPane;

    @FXML
    private StackPane calendarPageStackPane;
    @FXML
    private AnchorPane calendarPageAnchorPane;

    private LocalDate weekToDisplay;

    private EventCreationCard eventCreationCard;
    private EditEventCard editEventCard;
    private List<CalendarEventCard> calendarEventCards = new ArrayList<>();

    private EventList eventList;
    private ContactList contactList;

    CalendarPage(EventList eventList, ContactList contactList, TagHandler tagHandler) {
        this.eventList = eventList;
        this.contactList = contactList;
        this.tagHandler = tagHandler;
        eventList.subscribe(this);

        eventCreationCard = new EventCreationCard(eventList, contactList, tagHandler);
        calendarPageStackPane.getChildren().add(eventCreationCard.getPane());
        calendarPageAnchorPane.toFront();

        newEventButton.setOnAction(this::newEvent);
        nextWeekButton.setOnAction(this::incrementWeek);
        previousWeekButton.setOnAction(this::decrementWeek);

        weekToDisplay = LocalDate.now();
        setLabels(weekToDisplay);
        onEvent();
    }

    private void incrementWeek(ActionEvent actionEvent) {
        weekToDisplay = weekToDisplay.plusWeeks(1);
        setLabels(weekToDisplay);
        onEvent();
    }

    private void decrementWeek(ActionEvent actionEvent) {
        weekToDisplay = weekToDisplay.minusWeeks(1);
        setLabels(weekToDisplay);
        onEvent();
    }

    private void setLabels(LocalDate date) {
        // Sets date to monday of this week.
        date = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        TemporalField weekGetter = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        weekLabel.setText("Week " + date.get(weekGetter) + " " + date.getYear());

        mondayLabel.setText("Monday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        tuesdayLabel.setText("Tuesday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        wednesdayLabel.setText("Wednesday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        thursdayLabel.setText("Thursday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        fridayLabel.setText("Friday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        saturdayLabel.setText("Saturday " + date.getDayOfMonth() + "/" + date.getMonthValue());
        date = date.plusDays(1);
        sundayLabel.setText("Sunday " + date.getDayOfMonth() + "/" + date.getMonthValue());
    }

    private void newEvent(ActionEvent actionEvent) {
        eventCreationCard.clearFields();
        eventCreationCard.getPane().toFront();
    }

    @Override
    public void onEvent() {
        clearCalendar();

        List<Event> eventsThisWeek = eventList.getEventsOfWeek(weekToDisplay);

        for (Event event : eventsThisWeek) {
            CalendarEventCard calendarEventCard = new CalendarEventCard(event);
            calendarEventCard.getPane().setOnMouseClicked(mouseEvent -> editEvent(event));
            calendarEventCards.add(calendarEventCard);
            event.subscribe(calendarEventCard);
            determineFlowPane(event, calendarEventCard);
        }

    }

    private void editEvent(Event event) {
        editEventCard = new EditEventCard(event, contactList, tagHandler);
        calendarPageStackPane.getChildren().add(editEventCard.getPane());
        editEventCard.getPane().toFront();
    }

    private void clearCalendar() {
        for (CalendarEventCard calendarEventCard : calendarEventCards) {
            calendarEventCard.unsubscribe();
        }

        mondayFlowPane.getChildren().clear();
        tuesdayFlowPane.getChildren().clear();
        wednesdayFlowPane.getChildren().clear();
        thursdayFlowPane.getChildren().clear();
        fridayFlowPane.getChildren().clear();
        saturdayFlowPane.getChildren().clear();
        sundayFlowPane.getChildren().clear();
    }

    private void determineFlowPane(Event event, CalendarEventCard calendarEventCard) {
        switch (event.getDateTime().getDayOfWeek().getValue()) {
            case 1:
                mondayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 2:
                tuesdayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 3:
                wednesdayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 4:
                thursdayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 5:
                fridayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 6:
                saturdayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            case 7:
                sundayFlowPane.getChildren().add(calendarEventCard.getPane());
                break;
            default:
                throw new IllegalArgumentException("Date of event could not be parsed");
        }
    }
}
