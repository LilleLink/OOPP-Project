package javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TestTopBar extends Page {

    @FXML private Button toMainButton = new Button();
    @FXML private Button toSecondButton = new Button();

    IPageNavigator nav;

    public TestTopBar(IPageNavigator nav) {
        super();
        this.nav = nav;

        toMainButton.setOnMouseClicked(this::mainButtonClicked);
        toSecondButton.setOnMouseClicked(this::secondButtonClicked);
        System.out.println(this.getHeight()+" | "+this.getWidth());
        System.out.println(this.getLayoutX()+" | "+this.getLayoutY());
    }

    private void secondButtonClicked(MouseEvent mouseEvent) {
        nav.openSecondaryPage();
    }

    private void mainButtonClicked(MouseEvent mouseEvent) {
        nav.openMainPage();
    }

}
