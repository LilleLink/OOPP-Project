package controller.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import controller.javafx.components.ViewComponent;
import javafx.scene.layout.AnchorPane;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class RootWindow implements IPageNavigator, Initializable {

    private User user;

    @FXML private AnchorPane topBarAnchorPane;
    @FXML private AnchorPane pageAnchorPane;

    private ViewComponent mainPage;
    private ViewComponent secondaryPage;
    private ViewComponent testTopBar;

    // Class that controls the stage, what scenes are displayed etc.
    public RootWindow(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiatePages();
        topBarAnchorPane.getChildren().add(testTopBar);
        openMainPage();
    }

    private void initiatePages() {
        testTopBar = ViewComponentFactory.CreateTestTopBar(this);
        mainPage = ViewComponentFactory.CreateMainPage();
        secondaryPage = ViewComponentFactory.CreateSecondaryPage();
    }

    @Override
    public void openMainPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(mainPage);
    }

    @Override
    public void openSecondaryPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(secondaryPage);
    }

    @Override
    public void clearRootPage() {
        pageAnchorPane.getChildren().clear();
    }


}
