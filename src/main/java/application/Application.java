package application;

import javafx.stage.Stage;
import javafx.StageController;
import model.User;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //TODO Initiate model and send as parameter to StageController
        User user = new User("Pelle");
        StageController stageController = new StageController(stage, user);
    }
}
