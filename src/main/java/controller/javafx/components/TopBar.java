package controller.javafx.components;

import controller.javafx.IPageNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

class TopBar extends ViewComponent {

    @FXML private Button toMainButton;
    @FXML private Button toSecondButton;
    @FXML private Button toContactButton;
    @FXML private Button toCalendarButton;

    IPageNavigator nav;

    TopBar(IPageNavigator nav) {
        super();
        this.nav = nav;

        toMainButton.setOnMouseClicked(this::mainButtonClicked);
        toSecondButton.setOnMouseClicked(this::secondButtonClicked);
        toContactButton.setOnMouseClicked(this::toContactButton);
        toCalendarButton.setOnMouseClicked(this::toCalendarButton);
    }

    private void secondButtonClicked(MouseEvent mouseEvent) {
        nav.openSecondaryPage();
    }

    private void mainButtonClicked(MouseEvent mouseEvent) {
        nav.openMainPage();
    }

    private void toContactButton(MouseEvent mouseEvent) {
        nav.openContactPage();
    }

    private void toCalendarButton(MouseEvent mouseEvent) {
        nav.openCalendarPage();
    }

}
