package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Event;
import model.EventList;
import model.IObserver;
import model.notifications.Notifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A component representing an overview of event notifications.
 *
 * Composed of a {@link Notifier<Event>} and an {@link EventList} feeding content to it.
 *
 * Implements the {@link Runnable} and delegates the {@code run} method to
 * its {@link Notifier<Event>} object.
 *
 * @author Simon Johnsson
 */
public class EventNotificationsPage extends ViewComponent implements IObserver {

    private final Notifier<Event> eventNotifier;
    private final EventList eventList;
    private List<Event> notifications;


    @FXML private Button clearButton;
    @FXML private Label notificationsLabel;
    @FXML private VBox eventVBox;


    /**
     * Constructs an overview of the given event list and notifies of the contained events.
     * @param events the events to receive notifications about
     */
   public EventNotificationsPage(EventList events) {
        this.eventNotifier = new Notifier<>(events.getList());
        this.eventList = events;
        this.eventNotifier.subscribe(this);
        this.eventList.subscribe(this);
        this.notifications = eventNotifier.getActive();
        Thread thread = new Thread(eventNotifier, "Notifications");
        thread.setDaemon(true);
        thread.start();
        initComponents();
        populateVBox();
    }

    @Override
    public void onEvent() {
       List<Event> newEventList = new ArrayList<>(eventList.getList());
       newEventList.removeAll(new HashSet<>(eventNotifier.getActive()));
       this.eventNotifier.setContent(newEventList);
       this.notifications = eventNotifier.getActive();
       updateLabel();
       clearVBox();
       populateVBox();
    }

    private void updateLabel() {
        notificationsLabel.setText("You have (" + eventNotifier.size() + ") new notifications");
    }

    private void clearVBox() {
        eventVBox.getChildren().clear();
    }

    private void clearNotifications(ActionEvent e) {
        eventNotifier.clear();
        clearVBox();
        updateLabel();
    }

    private void populateVBox() {
        for(Event n : notifications) {
            eventVBox.getChildren().add(createCalEventCard(n).getPane());
        }
    }

    private CalendarEventCard createCalEventCard(Event e) {
        return new CalendarEventCard(e);
    }

    private void initComponents() {
        clearButton.setOnAction(this::clearNotifications);
        updateLabel();
    }
}
