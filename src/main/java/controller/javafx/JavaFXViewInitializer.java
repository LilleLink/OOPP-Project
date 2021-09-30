package controller.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class JavaFXViewInitializer {

    public JavaFXViewInitializer(Stage stage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/javafx/RootWindow.fxml"));
        loader.setControllerFactory(aClass -> new RootWindow(user));

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("PRM");
        stage.show();
    }

}
