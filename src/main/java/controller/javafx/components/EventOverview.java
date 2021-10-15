package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import model.Event;

import java.util.ArrayList;
import java.util.List;

class EventOverview extends ViewComponent {

    @FXML
    private FlowPane eventsFlowPane;

    private List<Event> eventList = new ArrayList<>();

    EventOverview(List<Event> eventList) {
        this.eventList = eventList;
        initialize();
    }

    private void initialize() {
        for (Event e : eventList)
            eventsFlowPane.getChildren().add(new CalendarEventCard(e).getPane());
    }
}
