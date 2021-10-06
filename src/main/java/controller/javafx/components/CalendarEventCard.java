package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Event;

public class CalendarEventCard extends ViewComponent {

    @FXML private AnchorPane calendarEventAnchorPane;
    @FXML private Label eventNameLabel;
    @FXML private Label eventTimeLabel;
    @FXML private Label eventAddressLabel;

    private Event event;

    public CalendarEventCard(Event event) {
        this.event = event;
        setLabels();
    }

    private void setLabels() {
        eventNameLabel.setText(event.getName());
        eventTimeLabel.setText(event.getDateTime().toLocalTime().toString());
        eventAddressLabel.setText(event.getAddress());
    }


}
