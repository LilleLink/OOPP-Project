package controller.javafx.components;

import controller.javafx.IPageNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TestTopBar extends ViewComponent {

    @FXML private Button toMainButton;
    @FXML private Button toSecondButton;
    @FXML private Button toContactButton;

    IPageNavigator nav;

    public TestTopBar(IPageNavigator nav) {
        super();
        this.nav = nav;

        toMainButton.setOnMouseClicked(this::mainButtonClicked);
        toSecondButton.setOnMouseClicked(this::secondButtonClicked);
        toContactButton.setOnMouseClicked(this::toContactButton);
    }

    private void secondButtonClicked(MouseEvent mouseEvent) {
        nav.openSecondaryPage();
    }

    private void mainButtonClicked(MouseEvent mouseEvent) {
        nav.openCalendarPage();
    }

    private void toContactButton(MouseEvent mouseEvent) {
        nav.openContactPage();
    }

}
