package controller.javafx.components;

import controller.javafx.IPageNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

class TopBar extends ViewComponent {

    @FXML
    private Button toMainButton;
    @FXML
    private Button toStatisticsButton;
    @FXML
    private Button toContactButton;
    @FXML
    private Button toCalendarButton;

    IPageNavigator nav;

    TopBar(IPageNavigator nav) {
        super();
        this.nav = nav;

        toMainButton.setOnAction(this::mainButtonClicked);
        toStatisticsButton.setOnAction(this::statisticsButtonClicked);
        toContactButton.setOnAction(this::toContactButton);
        toCalendarButton.setOnAction(this::toCalendarButton);
    }

    private void statisticsButtonClicked(ActionEvent actionEvent) {
        nav.openStatisticsPage();
    }

    private void mainButtonClicked(ActionEvent actionEvent) {
        nav.openMainPage();
    }

    private void toContactButton(ActionEvent actionEvent) {
        nav.openContactPage();
    }

    private void toCalendarButton(ActionEvent actionEvent) {
        nav.openCalendarPage();
    }

}
