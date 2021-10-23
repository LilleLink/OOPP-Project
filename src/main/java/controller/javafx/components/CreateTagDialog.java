package controller.javafx.components;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;

class CreateTagDialog extends ViewComponent {

    private final Stage stage = new Stage();

    private final TagHandler tagHandler;
    @FXML
    private TextField tagName;
    @FXML
    private Button addTagButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Text errorMessageText;


    CreateTagDialog(TagHandler tagHandler) {
        super();
        this.tagHandler = tagHandler;
        errorMessageText.setVisible(false);
        errorMessageText.setFill(Color.RED);
        addTagButton.setOnAction(actionEvent -> addTag());
        cancelButton.setOnAction(actionEvent -> closeStage());
        tagName.textProperty().addListener(this::textFieldChanged);
        displayAndWait();
    }

    private void textFieldChanged(Observable observable) {
        errorMessageText.setVisible(false);
    }

    @FXML
    private void addTag() {
        try {
            Color color = colorPicker.getValue();
            String formattedColor = String.format("#%02X%02X%02X",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
            tagHandler.createTag(tagName.getText(), formattedColor);
            closeStage();
        } catch (NameNotAllowedException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    private void closeStage() {
        stage.close();
    }

    private void keyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case ESCAPE:
                closeStage();
                break;
            case ENTER:
                addTag();
                break;
            default:
                break;
        }
    }

    private void displayAndWait() {
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(this.getPane(), 300, 150);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);

        stage.setTitle("Create new Tag");
        stage.setScene(scene);
        stage.showAndWait();
    }

}