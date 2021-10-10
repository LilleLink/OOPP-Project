package controller.javafx.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ITag;

class TagCard extends ViewComponent {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private Label tagLabel;

    TagCard(ITag tag){
        this.tagLabel.setText(tag.getName());
        baseAnchorPane.setStyle("-fx-background-color: #" + tag.getColor());
    }
}
