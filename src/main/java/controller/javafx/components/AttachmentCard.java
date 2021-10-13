package controller.javafx.components;

import attachmentHandler.AttachmentHandlerFactory;
import attachmentHandler.IAttachmentHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

class AttachmentCard extends ViewComponent {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private Label nameLabel;
    @FXML private Button removeButton;
    private final Path attachment;
    private final IAttachmentHandler attachmentHandler = AttachmentHandlerFactory.getService();
    private EventHandler<Event> deleteHandler;

    AttachmentCard(Path attachment){
        this.attachment = attachment;
        nameLabel.setText(attachment.getFileName().toString());
        baseAnchorPane.setOnMouseClicked(this::openAttachment);
        this.removeButton.setOnAction(this::deleteAttachment);
    }

    private void openAttachment(MouseEvent event){
        try {
            Desktop.getDesktop().open(attachment.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAttachment(Event event){
        deleteHandler.handle(event);
    }

    public void setDeleteHandler(EventHandler<Event> handler){
        deleteHandler = handler;
    }
}
