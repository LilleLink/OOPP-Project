package application;

import controller.javafx.JavaFXViewInitializer;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    /***
     * Starts the JavaFX application
     * @param stage the stage of the GUI
     * @throws IOException if the necessary files cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Instantiates model
        User user = new User("Pelle");

        //Instantiates javafx controller/view
        JavaFXViewInitializer javaFXViewInitializer = new JavaFXViewInitializer(stage, user);
    }
}
