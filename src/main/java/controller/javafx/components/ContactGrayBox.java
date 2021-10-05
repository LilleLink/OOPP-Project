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

import java.lang.invoke.LambdaConversionException;

class ContactGrayBox extends ViewComponent{

    private Contact contact;

    @FXML private AnchorPane baseAnchorPane;

    @FXML private ImageView profileImage;

    @FXML private TextField contactName;

    @FXML private Button closeButton;

    @FXML private Button deleteButton;

    @FXML private Button saveButton;

    @FXML private Text contactChangedText;

    @FXML private AnchorPane addressPane;

    private AddressCard addressCard;

    private EventHandler<Event> closeWindowHandler;

    private EventHandler<Event> deleteContactHandler;

    ContactGrayBox(){
        super();
        baseAnchorPane.setOnMouseClicked(this::close);
        closeButton.setOnAction(this::close);
        deleteButton.setOnAction(this::delete);
        contactName.textProperty().addListener(((observableValue, s, t1) -> fieldsChanged()));
        saveButton.setOnAction(this::save);
        initAddressField();
    }

    private void initAddressField(){
        this.addressCard = new AddressCard();
        addressCard.setNewAddressHandler(MouseEvent -> fieldsChanged());
        AnchorPane addressCardPane = addressCard.getPane();
        this.addressPane.getChildren().add(addressCardPane);
        AnchorPane.setTopAnchor(addressCardPane, 0d);
        AnchorPane.setLeftAnchor(addressCardPane, 0d);
        AnchorPane.setRightAnchor(addressCardPane, 0d);
        AnchorPane.setBottomAnchor(addressCardPane, 0d);
    }

    private void fieldsChanged(){
        contactChangedText.setVisible(true);
    }

    public void setContact(Contact contact){
        this.contact = contact;
        contactName.setText(contact.getName());
        contactChangedText.setVisible(false);
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

    private void save(Event e){
        System.out.println("This should set the name of the contact to "+contactName.getText());
        close(e);
    }
}
