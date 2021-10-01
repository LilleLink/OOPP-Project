package controller.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.IOException;

public class JavaFXViewInitializer {

    /***
     * Initiates the JavaFX environment using the given stage from the launch method, and the user fron the instantiated model.
     * Loads the root window and sets the RootWindow class to the controller.
     * Displays the scene.
     * @param stage the stage to initialize the scene upon
     * @param user the instantiated "model" data-object
     * @throws IOException throws an IOException if path is faulty.
     */
    public JavaFXViewInitializer(Stage stage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/javafx/RootWindow.fxml"));
        loader.setControllerFactory(aClass -> new RootWindow(user));

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("PRM");
        stage.show();
    }

}
