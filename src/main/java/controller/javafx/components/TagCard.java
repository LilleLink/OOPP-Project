package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ITag;


class TagCard extends ViewComponent {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private Label tagLabel;
    private EventHandler<ActionEvent> deleteHandler;
    private final ITag tag;

    TagCard(ITag tag){
        this.tagLabel.setText(tag.getName());
        this.tag = tag;
        baseAnchorPane.setStyle("-fx-background-color: #" + tag.getColor());
    }

    ITag getTag(){
        return this.tag;
    }

    void setOnDelete(EventHandler<ActionEvent> eventHandler){
        deleteHandler = eventHandler;
    }
}
