package javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Page extends AnchorPane {

    protected Page() {
        // Loads the fxml file and sets this file as the controller
        String fxmlFileName = getClass().getSimpleName()+".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

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
