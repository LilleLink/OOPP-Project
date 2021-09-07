package javafxExample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloWorld extends Application
{

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelloWorld.fxml")));

        Scene scene = new Scene(root);
        stage.setTitle("Hello Mr. World");
        stage.setScene(scene);
        stage.show();

    }

}
