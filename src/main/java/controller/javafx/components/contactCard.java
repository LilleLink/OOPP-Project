package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class contactCard extends ViewComponent {
    @FXML AnchorPane baseAnchorPane;
    @FXML ImageView contactImage;
    @FXML Label nameLabel;
    @FXML HBox tagHBox;

    public contactCard(){
        super();
    }
}