package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ITag;


class TagCard extends ViewComponent {
    @FXML
    private AnchorPane baseAnchorPane;
    @FXML
    private Label tagLabel;
    @FXML
    private Button deleteButton;
    private EventHandler<ActionEvent> deleteHandler;
    private final ITag tag;

    TagCard(ITag tag) {
        this.tag = tag;
        this.tagLabel.setText(tag.getName());
        baseAnchorPane.setStyle("-fx-border-color: #" + tag.getColor());
        this.deleteButton.setOnAction(actionEvent -> deleteHandler.handle(actionEvent));
    }

    ITag getTag() {
        return this.tag;
    }

    void setOnDelete(EventHandler<ActionEvent> eventHandler) {
        deleteHandler = eventHandler;
    }
}
