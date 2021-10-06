package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Contact;

import java.awt.*;
import java.lang.invoke.LambdaConversionException;
import java.net.URI;

class ContactGrayBox extends ViewComponent{

    private Contact contact;

    @FXML private AnchorPane baseAnchorPane;

    @FXML private ImageView profileImage;

    @FXML private TextField contactName;

    @FXML private Button closeButton;

    @FXML private Button deleteButton;

    @FXML private Button saveButton;

    @FXML private Text contactChangedText;

    @FXML private TextField addressText;

    @FXML private Button openMapButton;

    private EventHandler<Event> closeWindowHandler;

    private EventHandler<Event> deleteContactHandler;

    ContactGrayBox(){
        super();
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
        contactName.textProperty().addListener(((observableValue, s, t1) -> fieldsChanged()));
        saveButton.setOnAction(this::save);
        openMapButton.setOnAction(this::openMap);
        addressText.textProperty().addListener((observable -> fieldsChanged()));
    }

    private void fieldsChanged(){
        contactChangedText.setVisible(true);
    }

    public void setContact(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
        contactChangedText.setVisible(false);
        addressText.setText(contact.getAddress());
    }

    void setOnClose(EventHandler<Event> handler){
        closeWindowHandler = handler;
    }

    void close(Event event){
        closeWindowHandler.handle(event);
    }

    void setOnDelete(EventHandler<Event> handler){
        deleteContactHandler = handler;
    }

    private void delete(Event event){
        deleteContactHandler.handle(event);
        close(event);
    }

    private boolean openMap(ActionEvent event){
        //TODO use return value of openMap
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://maps.google.com/maps?q=" +
                        addressText.getText().replace(' ', '+')));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void save(Event e){
        System.out.println("This should set the name of the contact to "+contactName.getText());
        System.out.println("This should set the address of the contact to "+addressText.getText());
        if (isAllowed()) {
            close(e);
        }
    }

    private boolean isAllowed(){
        return contactName.getText().length() >= 1;
    }
}
