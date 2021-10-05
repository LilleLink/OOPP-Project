package controller.javafx.components;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Address;

class AddressCard extends ViewComponent{

    private Address address = new Address("placeholder");

    private EventHandler<Event> newAddressHandler;

    @FXML private TextField addressName;

    AddressCard(){
        super();
        //TODO should be the same object as in model. Address constructor should not be public
        addressName.setText(address.getAddress());
        addressName.textProperty().addListener((observableValue, s, t1) -> updateSearch());
        addressName.setOnAction(this::newAddress);
    }

    void setAddress(Address address){
        this.address = address;
        addressName.setText(address.getAddress());
    }

    private void updateSearch(){

    }

    void setNewAddressHandler(EventHandler<Event> handler){
        this.newAddressHandler = handler;
    }

    void newAddress(Event event){
        newAddressHandler.handle(event);
        this.address = new Address(addressName.getText());
    }

}
