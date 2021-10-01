package controller.javafx.components;

import controller.javafx.IPageNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TestTopBar extends ViewComponent {

    @FXML private Button toMainButton;
    @FXML private Button toSecondButton;

    IPageNavigator nav;

    public TestTopBar(IPageNavigator nav) {
        super();
        this.nav = nav;

        toMainButton.setOnMouseClicked(this::mainButtonClicked);
        toSecondButton.setOnMouseClicked(this::secondButtonClicked);
    }

    private void secondButtonClicked(MouseEvent mouseEvent) {
        nav.openSecondaryPage();
    }

    private void mainButtonClicked(MouseEvent mouseEvent) {
        nav.openCalendarPage();
    }

}
