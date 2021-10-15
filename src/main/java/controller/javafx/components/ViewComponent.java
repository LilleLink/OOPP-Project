package controller.javafx.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/***
 * A ViewComponent element is a component that wraps an anchorPane. Classes that inherit this class will load their
 * FXML files automatically onto the pane object given that the name of the FXML file is the same as the controller class name.
 */
public abstract class ViewComponent {

    private AnchorPane pane = new AnchorPane();

    /***
     * Loads the FXML file and sets the controller class automatically.
     */
    protected ViewComponent() {
        // Loads the fxml file and sets this file as the controller
        String fxmlFileName = getClass().getSimpleName() + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/javafx/components/" + fxmlFileName));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(pane);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Anchors the component
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
    }

    /***
     * Returns the AnchorPane that the ViewComponent controls
     * @return the AnchorPane that the ViewComponent controls
     */
    public AnchorPane getPane() {
        return pane;
    }

}
