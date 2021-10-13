package controller.javafx.components;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import search.ISearchable;
import search.SearchEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * A component responsible for getting text input and returning results relevant to the given input.
 *
 * @param <T> the searchable type of the domain of which to search.
 * @author Simon Johnsson
 */
public class SearchBar<T extends ISearchable<String>> extends ViewComponent{

    private final SearchEngine<T> searchEngine;
    private List<T> results;
    private final int tolerance;

    @FXML private TextField textField;
    @FXML private Button searchButton;

    SearchBar(List<T> searchBase,int tolerance) {
        this.searchEngine = new SearchEngine<>(searchBase);
        this.tolerance = tolerance;
        results = new ArrayList<>();
        textField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                search(keyEvent);
            }
        });
        searchButton.setOnAction(this::search);
    }

    @FXML void search(Event event) {
        results = searchEngine.search(textField.getText(),tolerance);
    }

    List<T> getResults() {
        return results;
    }



}