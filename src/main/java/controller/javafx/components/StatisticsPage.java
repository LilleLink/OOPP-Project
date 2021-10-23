package controller.javafx.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import model.*;

import java.util.Map;

class StatisticsPage extends ViewComponent implements IObserver {

    @FXML
    private PieChart eventDelegationPieChart;

    private EventList eventList;
    private TagHandler tagHandler;

    StatisticsPage(EventList eventList, TagHandler tagHandler) {
        this.eventList = eventList;
        this.tagHandler = tagHandler;
        eventList.subscribe(this);
        eventDelegationPieChart.setLabelsVisible(true);
        onEvent();
    }

    private void createStatistics() {
        Map<ITag, Integer> stats = StatisticsUtils.getEventDelegation(eventList, tagHandler);
        ObservableList<PieChart.Data> pieChartData = convertStatistics(stats);
        eventDelegationPieChart.setData(pieChartData);

        if (eventList.getList().isEmpty() || tagHandler.getAllTags().isEmpty()) {
            eventDelegationPieChart.setTitle("No data to track, start tagging!");
        } else {
            eventDelegationPieChart.setTitle("Event delegation");
        }
    }

    private ObservableList<PieChart.Data> convertStatistics(Map<ITag, Integer> stats) {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();

        for (Map.Entry<ITag, Integer> entry : stats.entrySet()) {
            result.add(new PieChart.Data(entry.getKey().getName(), entry.getValue()));
        }

        return result;
    }

    @Override
    public void onEvent() {
        createStatistics();
    }
}
