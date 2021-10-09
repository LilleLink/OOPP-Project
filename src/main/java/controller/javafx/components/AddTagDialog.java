package controller.javafx.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;

public class AddTagDialog extends ViewComponent {

    private final TagHandler tagHandler;
    @FXML private TextField tagName;
    @FXML private Button addTagButton;
    @FXML private Button cancelButton;


    AddTagDialog(TagHandler tagHandler){
        super();
        this.tagHandler = tagHandler;
        addTagButton.setOnAction(this::btnAddPersonClicked);
        cancelButton.setOnAction(this::closeStage);
    }

    @FXML
    void btnAddPersonClicked(ActionEvent event) {
        try{
            tagHandler.createTag(tagName.getText());
            closeStage(event);
        } catch (NameNotAllowedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void display(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 200, 100);

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();
    }

}