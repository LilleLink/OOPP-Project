package javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.Objects;

public class StageController implements IPageNavigator {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private User user;

    @FXML private AnchorPane topBarAnchorPane;
    @FXML private AnchorPane pageAnchorPane;

    private Page mainPage;
    private Page secondaryPage;
    private Page testTopBar;

    // Class that controls the stage, what scenes are displayed etc.
    public StageController(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        initiatePages();

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("RootWindow.fxml")));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.scene = new Scene(root);
        topBarAnchorPane.getChildren().add(testTopBar);
        openMainPage();

        stage.setTitle("PRM");
        stage.setScene(scene);
        stage.show();

    }

    private void initiatePages() {
        testTopBar = PageFactory.CreateTestTopBar(this);
        mainPage = PageFactory.CreateMainPage();
        secondaryPage = PageFactory.CreateSecondaryPage();
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
