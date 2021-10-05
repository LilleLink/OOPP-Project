package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Contact;

import java.lang.invoke.LambdaConversionException;

class ContactGrayBox extends ViewComponent{

    private Contact contact;

    @FXML private AnchorPane baseAnchorPane;

    @FXML private ImageView profileImage;

    @FXML private TextField contactName;

    @FXML private Button closeButton;

    @FXML Button deleteButton;

    private EventHandler<? super Event> closeWindowHandler;

    private EventHandler<? super Event> deleteContactHandler;

    ContactGrayBox(){
        super();
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
    }

    public void setContact(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
    }

    void setOnClose(EventHandler<Event> handler){
        closeWindowHandler = handler;
    }

    void close(Event event){
        closeWindowHandler.handle(event);
    }

    void setOnDelete(EventHandler<? super Event> handler){
        deleteContactHandler = handler;

    }

    private void delete(Event event){
        deleteContactHandler.handle(event);
        close(event);
    }
}
