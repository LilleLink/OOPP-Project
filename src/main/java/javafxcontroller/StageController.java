package javafxcontroller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageController implements INavigator {

    private Stage stage;
    private Scene mainScene;
    private Scene secondaryScene;

    // Class that controls the stage, what scenes are displayed etc.
    public StageController(Stage stage) {
        this.stage = stage;
        stage.setTitle("PRM");

        initiateScenes();

        stage.setScene(new Scene(new MainScene()));
    }

    private void initiateScenes() {
        mainScene = SceneFactory.CreateMainScene();
        secondaryScene = SceneFactory.CreateSecondaryScene();
    }

    @Override
    public void toMainScene() {
        stage.setScene(mainScene);
    }

    @Override
    public void toSecondaryScene() {
        stage.setScene(secondaryScene);
    }
}
