package application;

import javafx.stage.Stage;
import javafxcontroller.StageController;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //TODO Initiate model and send as parameter to StageController
        StageController stageController = new StageController(stage);
    }
}
