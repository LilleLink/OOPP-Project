package controller.javafx.components;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import search.ISearchable;
import search.SearchEngine;

import java.util.List;

/**
 * A component responsible for getting text input and returning results relevant to the given input.
 * Client decides when to query for the results. Searches can be done without handling the results.
 * @param <T> the searchable type of the domain to search.
 * @author Simon Johnsson
 */
public class SearchBar<T extends ISearchable<String>> extends ViewComponent{

    private SearchEngine<T> searchEngine;
    private List<T> results;
    private final int tolerance;

    @FXML private TextField textField;
    @FXML private Button searchButton;

    /**
     * Constructs a search bar with the given search base and tolerance.
     * @param searchBase the information to iterate
     * @param tolerance the maximum allowed edit distance from the search query to the result
     */
    SearchBar(List<T> searchBase,int tolerance) {
        this.searchEngine = new SearchEngine<>(searchBase);
        this.tolerance = tolerance;
        results = searchBase;
        textField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                search(keyEvent);
            }
        });
        searchButton.setOnAction(this::search);
    }

    /**
     * Performs a search operation with the input text and updates the result list.
     * Searches are case-insensitive and returns a result depending on the input query and tolerance.
     * Tolerance is the maximum allowed edit distance for the query and result.
     * @param event the event triggering the search
     */
    @FXML void search(Event event) {
        results = searchEngine.search(textField.getText(),tolerance);
    }

    /**
     * Returns the results from the previous search operation.
     * @return a list of the searchable type
     */
    List<T> getResults() {
        return results;
    }

    /**
     * Constructs a new search engine with the given search base and sets it to the current engine
      * @param searchBase the new search base
     */
    void updateSearchBase(List<T> searchBase) {
        this.searchEngine = new SearchEngine<>(searchBase);
    }



}