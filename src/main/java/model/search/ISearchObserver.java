package model.search;


/**
 * A client interested in knowing the results of a search operation.
 *
 * @author Simon Johnsson
 */
public interface ISearchObserver {
    /**
     * A response for a completed search operation.
     * It is strongly advised that the operation results are handled by the implementer.
     */
    void onSearch();
}
