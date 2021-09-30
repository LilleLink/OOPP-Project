package application;

import controller.javafx.JavaFXViewInitializer;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        User user = new User("Pelle");
        JavaFXViewInitializer javaFXViewInitializer = new JavaFXViewInitializer(stage, user);
    }
}
