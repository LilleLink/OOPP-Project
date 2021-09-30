package controller.javafx.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewComponent extends AnchorPane {

    protected ViewComponent() {
        // Loads the fxml file and sets this file as the controller
        String fxmlFileName = getClass().getSimpleName()+".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/javafx/components/"+fxmlFileName));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sets the anchors
        AnchorPane.setBottomAnchor(this,0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
    }

}
