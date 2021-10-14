package controller.javafx.components;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;

class CreateTagDialog extends ViewComponent {

    private final TagHandler tagHandler;
    @FXML private TextField tagName;
    @FXML private Button addTagButton;
    @FXML private Button cancelButton;
    @FXML private ColorPicker colorPicker;
    @FXML private Text errorMessageText;


    CreateTagDialog(TagHandler tagHandler){
        super();
        this.tagHandler = tagHandler;
        errorMessageText.setVisible(false);
        errorMessageText.setFill(Color.RED);
        addTagButton.setOnAction(this::btnAddTagClicked);
        cancelButton.setOnAction(this::closeStage);
        tagName.textProperty().addListener(this::textFieldChanged);
    }

    private void textFieldChanged(Observable observable) {
        errorMessageText.setVisible(false);
    }

    @FXML
    private void btnAddTagClicked(ActionEvent event) {
        try{
            tagHandler.createTag(tagName.getText(), Integer.toHexString(colorPicker.getValue().hashCode()));
            closeStage(event);
        } catch (NameNotAllowedException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void displayAndWait(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 300, 200);

        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();
    }

}